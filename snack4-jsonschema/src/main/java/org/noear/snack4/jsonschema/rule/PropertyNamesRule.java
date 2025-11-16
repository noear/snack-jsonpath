/*
 * Copyright 2005-2025 noear.org and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noear.snack4.jsonschema.rule;

import org.noear.snack4.ONode;
import org.noear.snack4.jsonschema.JsonSchemaException;
import org.noear.snack4.jsonschema.PathTracker;
import org.noear.snack4.jsonschema.SchemaKeyword;
import org.noear.snack4.jsonschema.SchemaType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * propertyNames 约束验证规则
 *
 * @author noear
 * @since 4.0
 */
public class PropertyNamesRule implements ValidationRule {
    // 内部编译好的针对属性名的规则集
    private final List<ValidationRule> compiledNameRules = new ArrayList<>();
    private final ONode propertyNamesSchema;
    private final String expectedType;

    private final boolean hasStringConstraints;
    private final boolean hasNumericConstraints;

    public PropertyNamesRule(ONode schemaNode) {
        this.propertyNamesSchema = schemaNode.get(SchemaKeyword.PROPERTY_NAMES);
        this.expectedType = getExpectedType();

        // 预计算约束存在性
        this.hasStringConstraints = propertyNamesSchema.hasKey(SchemaKeyword.MIN_LENGTH) ||
                propertyNamesSchema.hasKey(SchemaKeyword.MAX_LENGTH) ||
                propertyNamesSchema.hasKey(SchemaKeyword.PATTERN) ||
                propertyNamesSchema.hasKey(SchemaKeyword.FORMAT);

        this.hasNumericConstraints = propertyNamesSchema.hasKey(SchemaKeyword.MINIMUM) ||
                propertyNamesSchema.hasKey(SchemaKeyword.MAXIMUM) ||
                propertyNamesSchema.hasKey(SchemaKeyword.EXCLUSIVE_MINIMUM) ||
                propertyNamesSchema.hasKey(SchemaKeyword.EXCLUSIVE_MAXIMUM);

        // 1. 类型规则
        if (propertyNamesSchema.hasKey(SchemaKeyword.TYPE)) {
            compiledNameRules.add(new TypeRule(propertyNamesSchema.get(SchemaKeyword.TYPE)));
        }

        // 2. 字符串约束规则
        if (hasStringConstraints) {
            compiledNameRules.add(new StringConstraintRule(propertyNamesSchema));
        }

        // 3. 数值约束规则
        if (hasNumericConstraints) {
            compiledNameRules.add(new NumericConstraintRule(propertyNamesSchema));
        }

        // 4. 枚举规则
        if (propertyNamesSchema.hasKey(SchemaKeyword.ENUM)) {
            compiledNameRules.add(new EnumRule(propertyNamesSchema.get(SchemaKeyword.ENUM)));
        }
    }

    @Override
    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        if (!data.isObject()) {
            return;
        }

        if (compiledNameRules.isEmpty()) {
            // 没有约束，直接通过
            return;
        }

        // 迭代对象的所有键名
        for (String propName : data.getObject().keySet()) {
            // 将属性名称包装成一个 ONode
            ONode nameNode = createNameNode(propName);

            // 当前路径（仅用于错误提示，不用于递归进入）
            String currentPath = path.currentPath();

            try {
                for (ValidationRule rule : compiledNameRules) {
                    rule.validate(nameNode, path);
                }
            } catch (JsonSchemaException e) {
                throw new JsonSchemaException(
                        "Property name '" + propName + "' failed validation. " + e.getMessage(),
                        currentPath,
                        propName
                );
            }
        }
    }

    /**
     * 获取 propertyNames 中期望的类型（单字符串类型）
     */
    private String getExpectedType() {
        if (propertyNamesSchema.hasKey(SchemaKeyword.TYPE)) {
            ONode typeNode = propertyNamesSchema.get(SchemaKeyword.TYPE);
            if (typeNode.isString()) {
                return typeNode.getString();
            }
        }
        return null;
    }

    private ONode createNameNode(String propName) {
        if (SchemaType.INTEGER.equals(expectedType) || SchemaType.NUMBER.equals(expectedType)) {
            try {
                // 尝试解析为 Double
                double value = Double.parseDouble(propName);

                // 如果期望是 integer，但值是小数，我们不能将其视为有效的数字键。
                if (SchemaType.INTEGER.equals(expectedType) && value != Math.floor(value)) {
                    // 此时，返回字符串节点。后续的 TypeRule (期望 integer) 会失败。
                    return ONode.ofBean(propName);
                }

                // 成功解析为数字 (或整数)。
                // 优化：对于整数值，尽量使用 Long 类型以避免精度问题，且与 TypeRule 兼容性更好。
                if (value == Math.floor(value)) {
                    return ONode.ofBean((long) value);
                } else {
                    return ONode.ofBean(value);
                }
            } catch (NumberFormatException e) {
                // 键名不是有效的数字 (e.g., "abc")，保持为字符串节点。
                // TypeRule 会捕获类型不匹配。
                return ONode.ofBean(propName);
            }
        } else {
            // 期望 string, object, array, null 或无约束，一律创建为字符串节点
            return ONode.ofBean(propName);
        }
    }

    @Override
    public String toString() {
        return "PropertyNamesRule{" +
                "rules=" + compiledNameRules.stream().map(ValidationRule::toString).collect(Collectors.joining(", ")) +
                '}';
    }
}
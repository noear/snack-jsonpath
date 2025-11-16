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

    public PropertyNamesRule(ONode schemaNode) {
        propertyNamesSchema = schemaNode.get(SchemaKeyword.PROPERTY_NAMES);

        // 1. 类型规则（通常是 type: string）
        if (propertyNamesSchema.hasKey(SchemaKeyword.TYPE)) {
            compiledNameRules.add(new TypeRule(propertyNamesSchema.get(SchemaKeyword.TYPE)));
        }

        // 2. 字符串约束规则（minLength, maxLength, pattern, format）
        if (propertyNamesSchema.hasKey(SchemaKeyword.MIN_LENGTH) ||
                propertyNamesSchema.hasKey(SchemaKeyword.MAX_LENGTH) ||
                propertyNamesSchema.hasKey(SchemaKeyword.PATTERN) ||
                propertyNamesSchema.hasKey(SchemaKeyword.FORMAT)) {

            // 依赖于 StringConstraintRule 已经支持 format 的前提
            compiledNameRules.add(new StringConstraintRule(propertyNamesSchema));
        }

        // 3. 数值约束规则（minimum, maximum, exclusiveMinimum, exclusiveMaximum）
        if (propertyNamesSchema.hasKey(SchemaKeyword.MINIMUM) ||
                propertyNamesSchema.hasKey(SchemaKeyword.MAXIMUM) ||
                propertyNamesSchema.hasKey(SchemaKeyword.EXCLUSIVE_MINIMUM) ||
                propertyNamesSchema.hasKey(SchemaKeyword.EXCLUSIVE_MAXIMUM)) {

            compiledNameRules.add(new NumericConstraintRule(propertyNamesSchema));
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

        // 检查 propertyNames 的类型约束
        String expectedType = getExpectedType();

        // 迭代对象的所有键名
        for (String propName : data.getObject().keySet()) {
            // 将属性名称包装成一个 String ONode，以便可以对其应用 ValidationRule
            ONode nameNode = createNameNode(propName, expectedType);

            // 当前路径（仅用于错误提示，不用于递归进入）
            String currentPath = path.currentPath();

            try {
                // 对所有编译好的属性名规则进行验证
                for (ValidationRule rule : compiledNameRules) {
                    // 对键名进行验证
                    rule.validate(nameNode, path);
                }
            } catch (JsonSchemaException e) {
                // 如果任何键名验证失败，抛出新的异常，提供详细的上下文
                // 关键是提供是哪个属性名失败了
                throw new JsonSchemaException(
                        "Property name '" + propName + "' failed validation. " + e.getMessage(),
                        currentPath,
                        propName
                );
            }
        }
    }

    /**
     * 获取 propertyNames 中期望的类型
     */
    private String getExpectedType() {
        if (propertyNamesSchema.hasKey(SchemaKeyword.TYPE)) {
            ONode typeNode = propertyNamesSchema.get(SchemaKeyword.TYPE);
            if (typeNode.isString()) {
                return typeNode.getString();
            }
        }

        return null; // 没有明确的类型约束
    }

    /**
     * 根据属性名字符串创建对应的 ONode
     * 如果属性名是数字字符串，创建为数字节点，否则创建为字符串节点
     */
    private ONode createNameNode(String propName, String expectedType) {
        // 如果有明确的类型约束，按照约束创建节点
        if (SchemaType.INTEGER.equals(expectedType)) {
            // 期望 integer 类型，尝试转换为数字
            if (isIntegerString(propName)) {
                try {
                    long value = Long.parseLong(propName);
                    return ONode.ofBean(value);
                } catch (NumberFormatException e) {
                    // 转换失败，保持字符串
                    return ONode.ofBean(propName);
                }
            } else {
                // 不是整数字符串，保持字符串让类型验证失败
                return ONode.ofBean(propName);
            }
        } else if (SchemaType.NUMBER.equals(expectedType)) {
            // 期望 number 类型，尝试转换为数字
            if (isNumericString(propName)) {
                try {
                    double value = Double.parseDouble(propName);
                    return ONode.ofBean(value);
                } catch (NumberFormatException e) {
                    // 转换失败，保持字符串
                    return ONode.ofBean(propName);
                }
            } else {
                // 不是数字字符串，保持字符串让类型验证失败
                return ONode.ofBean(propName);
            }
        } else {
            // 期望 string 类型或其他类型，或者没有类型约束，保持为字符串
            return ONode.ofBean(propName);
        }
    }

    private boolean isIntegerString(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // 允许负号开头
        int start = 0;
        if (str.charAt(0) == '-') {
            if (str.length() == 1) return false; // 只有负号不行
            start = 1;
        }

        for (int i = start; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumericString(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return "PropertyNamesRule{" +
                "rules=" + compiledNameRules.stream().map(ValidationRule::toString).collect(Collectors.joining(", ")) +
                '}';
    }
}
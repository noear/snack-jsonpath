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
import org.noear.snack4.jsonschema.JsonSchema;
import org.noear.snack4.jsonschema.JsonSchemaException;
import org.noear.snack4.jsonschema.PathTracker;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 额外属性验证规则 (已支持 patternProperties)
 *
 * @author noear
 * @since 4.0
 */
public class AdditionalPropertiesRule implements ValidationRule {
    private final boolean allowAdditional;
    private final JsonSchema additionalSchemaValidator;
    private final Set<String> definedProperties = new HashSet<>();
    private final Map<String, Pattern> compiledPatterns;

    public AdditionalPropertiesRule(ONode schemaNode) {
        ONode additionalPropsNode = schemaNode.get("additionalProperties");
        ONode patternPropertiesNode = schemaNode.getOrNull("patternProperties");

        if (additionalPropsNode.isBoolean()) {
            this.allowAdditional = additionalPropsNode.getBoolean();
            this.additionalSchemaValidator = null;
        } else if (additionalPropsNode.isObject()) {
            this.allowAdditional = true;
            this.additionalSchemaValidator = new JsonSchema(additionalPropsNode);
        } else {
            // 默认 (关键字不存在)
            this.allowAdditional = true;
            this.additionalSchemaValidator = null;
        }

        // 预先收集所有已定义的属性
        if (schemaNode.hasKey("properties")) {
            definedProperties.addAll(schemaNode.get("properties").getObject().keySet());
        }

        // 编译 patternProperties 中的正则表达式
        if (patternPropertiesNode != null && patternPropertiesNode.isObject()) {
            compiledPatterns = new HashMap<>();
            for (String patternStr : patternPropertiesNode.getObject().keySet()) {
                compiledPatterns.put(patternStr, Pattern.compile(patternStr));
            }
        } else {
            compiledPatterns = Collections.emptyMap();
        }
    }

    /**
     * 检查属性是否通过 patternProperties 定义
     */
    private boolean isPatternDefined(String propertyName) {
        for (Pattern pattern : compiledPatterns.values()) {
            if (pattern.matcher(propertyName).matches()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        if (!data.isObject()) {
            return;
        }

        String currentPath = path.currentPath();

        for (String key : data.getObject().keySet()) {
            // 检查是否为 'properties' 或 'patternProperties' 定义
            if (!definedProperties.contains(key) && !isPatternDefined(key)) {
                // 这是一个 "额外" 属性
                if (!allowAdditional) {
                    throw new JsonSchemaException("Additional property '" + key + "' is not allowed", currentPath, key);
                }

                if (additionalSchemaValidator != null) {
                    // 允许，但必须验证
                    path.enterProperty(key);
                    try {
                        additionalSchemaValidator.validate(data.get(key)); // 内部会处理路径
                    } catch (JsonSchemaException e) {
                        throw new JsonSchemaException("Additional property '" + key + "' failed validation: " + e.getMessage(), currentPath, key, e);
                    }
                    path.exit();
                }
            }
        }
    }
}
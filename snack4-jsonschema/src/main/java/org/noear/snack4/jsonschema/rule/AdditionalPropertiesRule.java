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

import java.util.HashSet;
import java.util.Set;

/**
 * 额外属性验证规则
 *
 * @author noear
 * @since 4.0
 */
public class AdditionalPropertiesRule implements ValidationRule {
    private final ONode schemaNode;
    private final boolean allowAdditional;
    private final JsonSchema additionalSchemaValidator;
    private final Set<String> definedProperties = new HashSet<>();

    public AdditionalPropertiesRule(ONode schemaNode, ONode rootSchema) {
        this.schemaNode = schemaNode;
        ONode additionalPropsNode = schemaNode.get("additionalProperties");

        if (additionalPropsNode.isBoolean()) {
            this.allowAdditional = additionalPropsNode.getBoolean();
            this.additionalSchemaValidator = null;
        } else if (additionalPropsNode.isObject()) {
            // 允许，但需要符合子-schema
            this.allowAdditional = true;
            // 关键：为子-schema 创建一个 JsonSchema 实例
            // 我们需要 rootSchema 来正确解析子-schema 中可能存在的 $ref
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
        // patternProperties 也应被视为“已定义” (此处暂未实现)
    }

    @Override
    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        if (!data.isObject()) {
            return;
        }

        for (String key : data.getObject().keySet()) {
            if (!definedProperties.contains(key)) {
                // 这是一个 "额外" 属性
                if (!allowAdditional) {
                    throw new JsonSchemaException("Additional property '" + key + "' is not allowed at " + path.currentPath());
                }

                if (additionalSchemaValidator != null) {
                    // 允许，但必须验证
                    path.enterProperty(key);
                    try {
                        additionalSchemaValidator.validate(data.get(key), path);
                    } catch (JsonSchemaException e) {
                        // 包装异常，以澄清它是 additionalProperties 失败
                        throw new JsonSchemaException("Additional property '" + key + "' failed validation: " + e.getMessage(), e);
                    }
                    path.exit();
                }
            }
        }
    }
}
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数组约束验证规则 (已支持 uniqueItems)
 *
 * @author noear
 * @since 4.0
 */
public class ArrayConstraintRule implements ValidationRule {
    private final Integer minItems;
    private final Integer maxItems;
    private final boolean uniqueItems;

    public ArrayConstraintRule(ONode schemaNode) {
        this.minItems = schemaNode.hasKey("minItems") ? schemaNode.get("minItems").getInt() : null;
        this.maxItems = schemaNode.hasKey("maxItems") ? schemaNode.get("maxItems").getInt() : null;
        this.uniqueItems = schemaNode.hasKey("uniqueItems") && schemaNode.get("uniqueItems").getBoolean();
    }

    @Override
    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        if (!data.isArray()) {
            return;
        }

        List<ONode> items = data.getArray();
        int size = items.size();
        String currentPath = path.currentPath();

        // minItems 和 maxItems 校验
        if (minItems != null && size < minItems) {
            throw new JsonSchemaException("Array length " + size + " < minItems(" + minItems + ")", currentPath, data.toJson());
        }
        if (maxItems != null && size > maxItems) {
            throw new JsonSchemaException("Array length " + size + " > maxItems(" + maxItems + ")", currentPath, data.toJson());
        }

        // uniqueItems 校验
        if (uniqueItems) {
            Set<String> set = new HashSet<>(size);
            for (ONode item : items) {
                // 将 ONode 转换为 JSON 字符串进行比较，保证复杂类型的正确比较
                if (!set.add(item.toJson())) {
                    throw new JsonSchemaException("Array contains duplicate items", currentPath, data.toJson());
                }
            }
        }
    }
}
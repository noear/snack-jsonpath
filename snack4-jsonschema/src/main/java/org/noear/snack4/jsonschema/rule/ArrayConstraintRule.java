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

/**
 * 数组约束验证规则
 *
 * @author noear
 * @since 4.0
 */
public class ArrayConstraintRule implements ValidationRule {
    private final Integer minItems;
    private final Integer maxItems;

    public ArrayConstraintRule(ONode schemaNode) {
        this.minItems = schemaNode.hasKey("minItems") ? schemaNode.get("minItems").getInt() : null;
        this.maxItems = schemaNode.hasKey("maxItems") ? schemaNode.get("maxItems").getInt() : null;
    }

    @Override
    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        if (!data.isArray()) {
            return;
        }

        int size = data.getArray().size();
        String currentPath = path.currentPath();

        if (minItems != null && size < minItems) {
            throw new JsonSchemaException("Array length " + size + " < minItems(" + minItems + ") at " + currentPath);
        }
        if (maxItems != null && size > maxItems) {
            throw new JsonSchemaException("Array length " + size + " > maxItems(" + maxItems + ") at " + currentPath);
        }
    }
}
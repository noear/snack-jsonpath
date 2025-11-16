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

import java.util.List;
import java.util.stream.Collectors;

/**
 * 必需字段验证规则
 *
 * @author noear
 * @since 4.0
 */
public class RequiredRule implements ValidationRule {
    private final List<String> requiredFields;

    public RequiredRule(ONode requiredNode) {
        this.requiredFields = requiredNode.getArray()
                .stream()
                .map(ONode::getString)
                .collect(Collectors.toList());
    }

    @Override
    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        if (!data.isObject()) {
            return;
        }

        String currentPath = path.currentPath();

        for (String field : requiredFields) {
            if (!data.getObject().containsKey(field)) {
                throw new JsonSchemaException("Missing required field: " + field + " at " + currentPath);
            }
        }
    }
}
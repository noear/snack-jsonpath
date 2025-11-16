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
import java.util.Set;

/**
 * 枚举验证规则实现
 *
 * @author noear
 * @since 4.0
 */
public class EnumRule implements ValidationRule {
    private final Set<ONode> allowedValues;

    public EnumRule(ONode enumNode) {
        this.allowedValues = new HashSet<>();
        if (enumNode.isArray()) {
            for (ONode value : enumNode.getArray()) {
                allowedValues.add(value);
            }
        }
    }

    @Override
    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        if (!allowedValues.contains(data)) {
            throw new JsonSchemaException("Value '" + data.toJson() + "' not in enum list " + allowedValues + " at " + path.currentPath());
        }
    }

    @Override
    public String toString() {
        return "EnumRule{" +
                "allowedValues=" + allowedValues +
                '}';
    }
}
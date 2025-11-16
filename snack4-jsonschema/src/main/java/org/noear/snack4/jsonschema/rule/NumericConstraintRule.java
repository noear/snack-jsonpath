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
 * 数值约束验证规则
 *
 * @author noear
 * @since 4.0
 */
public class NumericConstraintRule implements ValidationRule {
    // 优化点 11: 完整支持数值约束
    // 原始代码只支持 minimum/maximum。
    // 增加了 exclusiveMinimum 和 exclusiveMaximum 的支持。
    private final Double minimum;
    private final Double maximum;
    private final Double exclusiveMinimum;
    private final Double exclusiveMaximum;


    public NumericConstraintRule(ONode schemaNode) {
        this.minimum = schemaNode.hasKey("minimum") ? schemaNode.get("minimum").getDouble() : null;
        this.maximum = schemaNode.hasKey("maximum") ? schemaNode.get("maximum").getDouble() : null;
        this.exclusiveMinimum = schemaNode.hasKey("exclusiveMinimum") ? schemaNode.get("exclusiveMinimum").getDouble() : null;
        this.exclusiveMaximum = schemaNode.hasKey("exclusiveMaximum") ? schemaNode.get("exclusiveMaximum").getDouble() : null;
    }

    @Override
    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        if (!data.isNumber()) {
            return;
        }

        double value = data.getDouble();
        String currentPath = path.currentPath();

        if (minimum != null && value < minimum) {
            throw new JsonSchemaException("Value " + value + " < minimum(" + minimum + ") at " + currentPath);
        }
        if (maximum != null && value > maximum) {
            throw new JsonSchemaException("Value " + value + " > maximum(" + maximum + ") at " + currentPath);
        }
        if (exclusiveMinimum != null && value <= exclusiveMinimum) {
            throw new JsonSchemaException("Value " + value + " <= exclusiveMinimum(" + exclusiveMinimum + ") at " + currentPath);
        }
        if (exclusiveMaximum != null && value >= exclusiveMaximum) {
            throw new JsonSchemaException("Value " + value + " >= exclusiveMaximum(" + exclusiveMaximum + ") at " + currentPath);
        }
    }
}
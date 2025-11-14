package org.noear.snack4.jsonschema.rule;

import org.noear.snack4.ONode;
import org.noear.snack4.jsonschema.JsonSchemaException;

/**
 * 数组约束验证规则
 */
public class ArrayConstraintRule implements ValidationRule {
    private final Integer minItems;
    private final Integer maxItems;

    public ArrayConstraintRule(ONode schemaNode) {
        this.minItems = schemaNode.hasKey("minItems") ? schemaNode.get("minItems").getInt() : null;
        this.maxItems = schemaNode.hasKey("maxItems") ? schemaNode.get("maxItems").getInt() : null;
    }

    @Override
    public void validate(ONode data) throws JsonSchemaException {
        if (!data.isArray()) {
            return; // 只验证数组类型
        }

        int size = data.getArray().size();

        if (minItems != null && size < minItems) {
            throw new JsonSchemaException("Array length " + size + " < minItems(" + minItems + ")");
        }

        if (maxItems != null && size > maxItems) {
            throw new JsonSchemaException("Array length " + size + " > maxItems(" + maxItems + ")");
        }
    }
}
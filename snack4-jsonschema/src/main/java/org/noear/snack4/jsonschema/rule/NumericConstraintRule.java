package org.noear.snack4.jsonschema.rule;

import org.noear.snack4.ONode;
import org.noear.snack4.jsonschema.JsonSchemaException;

/**
 * 数值约束验证规则
 */
public class NumericConstraintRule implements ValidationRule {
    private final Double minimum;
    private final Double maximum;

    public NumericConstraintRule(ONode schemaNode) {
        this.minimum = schemaNode.hasKey("minimum") ? schemaNode.get("minimum").getDouble() : null;
        this.maximum = schemaNode.hasKey("maximum") ? schemaNode.get("maximum").getDouble() : null;
    }

    @Override
    public void validate(ONode data) throws JsonSchemaException {
        if (!data.isNumber()) {
            return; // 只验证数字类型
        }

        double value = data.getDouble();

        if (minimum != null && value < minimum) {
            throw new JsonSchemaException("Value " + value + " < minimum(" + minimum + ")");
        }

        if (maximum != null && value > maximum) {
            throw new JsonSchemaException("Value " + value + " > maximum(" + maximum + ")");
        }
    }
}
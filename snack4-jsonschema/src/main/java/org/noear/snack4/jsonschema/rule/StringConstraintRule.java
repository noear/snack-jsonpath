package org.noear.snack4.jsonschema.rule;

import org.noear.snack4.ONode;
import org.noear.snack4.jsonschema.JsonSchemaException;

/**
 * 字符串约束验证规则
 */
public class StringConstraintRule implements ValidationRule {
    private final Integer minLength;
    private final Integer maxLength;
    private final String pattern;

    public StringConstraintRule(ONode schemaNode) {
        this.minLength = schemaNode.hasKey("minLength") ? schemaNode.get("minLength").getInt() : null;
        this.maxLength = schemaNode.hasKey("maxLength") ? schemaNode.get("maxLength").getInt() : null;
        this.pattern = schemaNode.hasKey("pattern") ? schemaNode.get("pattern").getString() : null;
    }

    @Override
    public void validate(ONode data) throws JsonSchemaException {
        if (!data.isString()) {
            return; // 只验证字符串类型
        }

        String value = data.getString();

        if (minLength != null && value.length() < minLength) {
            throw new JsonSchemaException("String length " + value.length() + " < minLength(" + minLength + ")");
        }

        if (maxLength != null && value.length() > maxLength) {
            throw new JsonSchemaException("String length " + value.length() + " > maxLength(" + maxLength + ")");
        }

        if (pattern != null && !value.matches(pattern)) {
            throw new JsonSchemaException("String does not match pattern: " + pattern);
        }
    }
}
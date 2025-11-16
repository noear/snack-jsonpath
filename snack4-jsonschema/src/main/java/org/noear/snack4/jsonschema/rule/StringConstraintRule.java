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

import java.util.regex.Pattern;

/**
 * 字符串约束验证规则
 *
 * @author noear
 * @since 4.0
 */
public class StringConstraintRule implements ValidationRule {
    private final Integer minLength;
    private final Integer maxLength;
    private final String patternString;

    private final Pattern compiledPattern;

    public StringConstraintRule(ONode schemaNode) {
        this.minLength = schemaNode.hasKey("minLength") ? schemaNode.get("minLength").getInt() : null;
        this.maxLength = schemaNode.hasKey("maxLength") ? schemaNode.get("maxLength").getInt() : null;
        this.patternString = schemaNode.hasKey("pattern") ? schemaNode.get("pattern").getString() : null;

        // 预编译
        if (this.patternString != null) {
            this.compiledPattern = Pattern.compile(this.patternString);
        } else {
            this.compiledPattern = null;
        }
    }

    @Override
    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        if (!data.isString()) {
            return;
        }

        String value = data.getString();
        String currentPath = path.currentPath();

        if (minLength != null && value.length() < minLength) {
            throw new JsonSchemaException("String length " + value.length() + " < minLength(" + minLength + ") at " + currentPath);
        }

        if (maxLength != null && value.length() > maxLength) {
            throw new JsonSchemaException("String length " + value.length() + " > maxLength(" + maxLength + ") at " + currentPath);
        }

        // 使用预编译的 Pattern
        if (compiledPattern != null && !compiledPattern.matcher(value).matches()) {
            throw new JsonSchemaException("String does not match pattern: " + patternString + " at " + currentPath);
        }
    }
}
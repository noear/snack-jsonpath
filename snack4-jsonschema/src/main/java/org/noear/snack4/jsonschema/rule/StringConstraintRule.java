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
import org.noear.snack4.jsonschema.SchemaFormat;
import org.noear.snack4.jsonschema.SchemaKeyword;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

/**
 * 字符串约束验证规则
 *
 * @author noear
 * @since 4.0
 */
public class StringConstraintRule implements ValidationRule {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
            // 注意：这是一个简化的邮件正则表达式，更严格的需要更复杂的模式
    );

    // RFC 3339/ISO 8601 的简化格式化程序
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ISO_TIME;

    private final Integer minLength;
    private final Integer maxLength;
    private final String patternString;
    private final String format;

    private final Pattern compiledPattern;

    public StringConstraintRule(ONode schemaNode) {
        this.minLength = schemaNode.hasKey(SchemaKeyword.MIN_LENGTH) ? schemaNode.get(SchemaKeyword.MIN_LENGTH).getInt() : null;
        this.maxLength = schemaNode.hasKey(SchemaKeyword.MAX_LENGTH) ? schemaNode.get(SchemaKeyword.MAX_LENGTH).getInt() : null;
        this.patternString = schemaNode.hasKey(SchemaKeyword.PATTERN) ? schemaNode.get(SchemaKeyword.PATTERN).getString() : null;
        this.format = schemaNode.hasKey(SchemaKeyword.FORMAT) ? schemaNode.get(SchemaKeyword.FORMAT).getString() : null;

        // 预编译 Pattern
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

        // 1. 长度校验
        if (minLength != null && value.length() < minLength) {
            throw new JsonSchemaException("String length " + value.length() + " < minLength(" + minLength + ") at " + currentPath);
        }
        if (maxLength != null && value.length() > maxLength) {
            throw new JsonSchemaException("String length " + value.length() + " > maxLength(" + maxLength + ") at " + currentPath);
        }

        // 2. 正则表达式校验
        if (compiledPattern != null && !compiledPattern.matcher(value).matches()) {
            throw new JsonSchemaException("String does not match pattern: " + patternString + " at " + currentPath);
        }

        // 3. Format 校验
        if (format != null) {
            if (format.equals(SchemaFormat.URI)) {
                if (!isValidUri(value)) {
                    throw new JsonSchemaException("String is not a valid URI format: " + value + " at " + currentPath);
                }
            } else if (format.equals(SchemaFormat.DATE_TIME)) {
                if (!isValidDateTime(value)) {
                    throw new JsonSchemaException("String is not a valid date-time format (RFC 3339): " + value + " at " + currentPath);
                }
            } else if (format.equals(SchemaFormat.DATE)) {
                if (!isValidDate(value)) {
                    throw new JsonSchemaException("String is not a valid date format (RFC 3339): " + value + " at " + currentPath);
                }
            } else if (format.equals(SchemaFormat.TIME)) {
                if (!isValidTime(value)) {
                    throw new JsonSchemaException("String is not a valid time format (RFC 3339): " + value + " at " + currentPath);
                }
            } else if (format.equals(SchemaFormat.EMAIL)) {
                if (!isValidEmail(value)) {
                    throw new JsonSchemaException("String is not a valid email format: " + value + " at " + currentPath);
                }
            }
            // 忽略未实现的 format，例如 "uuid", "hostname" 等
        }
    }

    // --- Format 辅助方法 ---

    private boolean isValidUri(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            // 仅检查语法是否符合 RFC 3986 规范
            new URI(value);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private boolean isValidDateTime(String value) {
        try {
            // ISO_OFFSET_DATE_TIME 严格遵守 RFC 3339 的带时区偏移的日期时间格式
            DATE_TIME_FORMATTER.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidDate(String value) {
        try {
            // ISO_DATE 严格遵守 YYYY-MM-DD 格式
            DATE_FORMATTER.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidTime(String value) {
        try {
            // ISO_TIME 严格遵守 HH:MM:SS[.fffffffff] 格式 (可能包含时区 Z 或偏移)
            TIME_FORMATTER.parse(value);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidEmail(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        // 使用预编译的简化 Pattern
        return EMAIL_PATTERN.matcher(value).matches();
    }
}
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
package org.noear.snack4.jsonschema;

/**
 * 架构工具
 *
 * @author noear
 * @since 4.0
 */
public interface SchemaKeywords {
    String KEYWORD_TYPE = "type";
    String KEYWORD_TITLE = "title";
    String KEYWORD_DESCRIPTION = "description";
    String KEYWORD_FORMAT = "format";
    String KEYWORD_REQUIRED = "required";
    String KEYWORD_ENUM = "enum";

    String KEYWORD_PROPERTIES = "properties";
    String KEYWORD_ADDITIONAL_PROPERTIES = "additionalProperties";
    String KEYWORD_PATTERN_PROPERTIES = "patternProperties";


    String KEYWORD_MIN_LENGTH = "minLength";
    String KEYWORD_MAX_LENGTH = "maxLength";
    String KEYWORD_PATTERN = "pattern";


    String KEYWORD_MINIMUM = "minimum";
    String KEYWORD_MAXIMUM = "maximum";
    String KEYWORD_EXCLUSIVE_MAXIMUM = "exclusiveMaximum";
    String KEYWORD_EXCLUSIVE_MINIMUM = "exclusiveMinimum";

    String KEYWORD_ITEMS = "items";
    String KEYWORD_MIN_ITEMS = "minItems";
    String KEYWORD_MAX_ITEMS = "maxItems";

    String KEYWORD_ALLOF = "allOf";
    String KEYWORD_ANYOF = "anyOf";
    String KEYWORD_ONEOF = "oneOf";

    String KEYWORD_REF = "$ref";
    String KEYWORD_SCHEMA = "$schema";
    String KEYWORD_DEFS = "$defs";
    String KEYWORD_DEFINITIONS = "definitions";


    String FORMAT_URI = "uri";
    String FORMAT_DATE_TIME = "date-time";
    String FORMAT_DATE = "date";
    String FORMAT_TIME = "time";

    String TYPE_OBJECT = "object";
    String TYPE_ARRAY = "array";
    String TYPE_STRING = "string";
    String TYPE_NUMBER = "number";
    String TYPE_INTEGER = "integer";
    String TYPE_BOOLEAN = "boolean";
    String TYPE_NULL = "null";
}
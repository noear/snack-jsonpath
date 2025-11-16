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
 * 架构关键字
 *
 * @author noear
 * @since 4.0
 */
public interface SchemaKeyword {
    String TYPE = "type";
    String TITLE = "title";
    String DESCRIPTION = "description";
    String FORMAT = "format";
    String REQUIRED = "required";
    String ENUM = "enum";

    String PROPERTIES = "properties";
    String ADDITIONAL_PROPERTIES = "additionalProperties";
    String PATTERN_PROPERTIES = "patternProperties";

    String MIN_LENGTH = "minLength";
    String MAX_LENGTH = "maxLength";
    String PATTERN = "pattern";

    String MINIMUM = "minimum";
    String MAXIMUM = "maximum";
    String EXCLUSIVE_MAXIMUM = "exclusiveMaximum";
    String EXCLUSIVE_MINIMUM = "exclusiveMinimum";

    String ITEMS = "items";
    String MIN_ITEMS = "minItems";
    String MAX_ITEMS = "maxItems";

    String ALLOF = "allOf";
    String ANYOF = "anyOf";
    String ONEOF = "oneOf";

    String REF = "$ref";
    String SCHEMA = "$schema";
    String DEFS = "$defs";
    String ANCHOR = "$anchor";
    String COMMENT = "$comment";
    String DEFINITIONS = "definitions";
    String DEFAULT = "default";
    String EXAMPLES = "examples";

    String PROPERTY_NAMES = "propertyNames";

    String READ_ONLY = "readOnly";
    String WRITE_ONLY = "writeOnly";
}
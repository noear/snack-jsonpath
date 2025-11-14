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
package org.noear.snack4.jsonschema.codec;

import org.noear.eggg.*;
import org.noear.snack4.ONode;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 架构工具
 *
 * @author noear
 * @since 4.0
 */
public class SchemaUtil {
    public static final String NAME_TYPE = "type";
    public static final String NAME_FORMAT = "format";
    public static final String NAME_PROPERTIES = "properties";
    public static final String NAME_DESCRIPTION = "description";
    public static final String NAME_REQUIRED = "required";
    public static final String NAME_ITEMS = "items";
    public static final String NAME_ENUM = "enum";

    public static final String FORMAT_URI = "uri";
    public static final String FORMAT_DATE_TIME = "date-time";
    public static final String FORMAT_DATE = "date";
    public static final String FORMAT_TIME = "time";

    public static final String TYPE_OBJECT = "object";
    public static final String TYPE_ARRAY = "array";
    public static final String TYPE_STRING = "string";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_INTEGER = "integer";
    public static final String TYPE_BOOLEAN = "boolean";
    public static final String TYPE_NULL = "null";

    public static String getSchemaTypeName(ONode node) {
        switch (node.type()) {
            case Undefined:
                return "undefined";
            case Null:
                return "null";
            case Boolean:
                return "boolean";
            case Number:
                if (node.getValue() instanceof Float || node.getValue() instanceof Double || node.getValue() instanceof BigDecimal) {
                    return "number";
                } else {
                    return "integer";
                }
            case String:
                return "string";
            case Date:
                return "date";
            case Array:
                return "array";
            case Object:
                return "object";
            default:
                return "unknown";
        }
    }
}
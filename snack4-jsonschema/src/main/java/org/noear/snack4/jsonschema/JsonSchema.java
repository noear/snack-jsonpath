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

import org.noear.snack4.ONode;
import org.noear.snack4.jsonschema.codec.JsonSchemaGenerator;
import org.noear.snack4.jsonschema.rule.EnumRule;
import org.noear.snack4.jsonschema.rule.TypeRule;
import org.noear.snack4.jsonschema.rule.ValidationRule;
import org.noear.snack4.jsonschema.codec.SchemaUtil;

import java.lang.reflect.Type;
import java.util.*;

/**
 * JSON模式验证器，支持JSON Schema规范
 *
 * @author noear
 * @since 4.0
 */
public class JsonSchema {
    public static JsonSchema ofJson(String jsonSchema) {
        return new JsonSchema(ONode.ofJson(jsonSchema));
    }

    public static JsonSchema ofNode(ONode jsonSchema) {
        return new JsonSchema(jsonSchema);
    }

    public static JsonSchema ofType(Type type) {
        Objects.requireNonNull(type, "type");

        ONode oNode = JsonSchemaGenerator.generate(type);

        if (oNode == null) {
            throw new JsonSchemaException("The type jsonSchema generation failed: " + type.toString());
        }

        return new JsonSchema(oNode);
    }

    private final ONode schema;
    private final Map<String, CompiledRule> compiledRules;

    public JsonSchema(ONode schema) {
        if (!schema.isObject()) {
            throw new IllegalArgumentException("Schema must be a JSON object");
        }
        this.schema = schema;
        this.compiledRules = compileSchema(schema);
    }

    @Override
    public String toString() {
        return String.valueOf(compiledRules);
    }

    public String toJson() {
        return schema.toJson();
    }

    public void validate(ONode data) throws JsonSchemaException {
        validateNode(schema, data, PathTracker.begin());
    }

    // 核心验证方法（完整实现）
    private void validateNode(ONode schemaNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        // 执行预编译规则
        CompiledRule rule = compiledRules.get(path.currentPath());
        if (rule != null) {
            rule.validate(dataNode, path);
        }

        // 处理类型校验
        if (schemaNode.hasKey("type")) {
            validateType(schemaNode.get("type"), dataNode, path);
        }

        // 处理枚举校验
        if (schemaNode.hasKey("enum")) {
            validateEnum(schemaNode.get("enum"), dataNode, path);
        }

        // 处理对象属性校验
        if (dataNode.isObject() && schemaNode.hasKey("properties")) {
            validateProperties(schemaNode.get("properties"), dataNode, path);
        }

        // 处理数组校验
        if (dataNode.isArray()) {
            validateArrayConstraints(schemaNode, dataNode, path);
        }

        // 处理数值范围校验
        if (dataNode.isNumber()) {
            validateNumericConstraints(schemaNode, dataNode, path);
        }

        // 处理字符串格式校验
        if (dataNode.isString()) {
            validateStringConstraints(schemaNode, dataNode, path);
        }

        // 处理条件校验
        validateConditional(schemaNode, dataNode, path);
    }

    // 类型校验（完整实现）
    private void validateType(ONode typeNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        if (typeNode.isString()) {
            String expectedType = typeNode.getString();
            if (!matchType(dataNode, expectedType)) {
                throw typeMismatch(expectedType, dataNode, path);
            }
        } else if (typeNode.isArray()) {
            boolean matched = false;
            for (ONode typeOption : typeNode.getArray()) {
                if (matchType(dataNode, typeOption.getString())) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                throw new JsonSchemaException("Type not in allowed types at " + path.currentPath());
            }
        }
    }

    private boolean matchType(ONode node, String type) {
        switch (type) {
            case "string":
                return node.isString();
            case "number":
                return node.isNumber();
            case "integer":
                return node.isNumber() && isInteger(node.getNumber());
            case "boolean":
                return node.isBoolean();
            case "object":
                return node.isObject();
            case "array":
                return node.isArray();
            case "null":
                return node.isNull();
            default:
                return false;
        }
    }

    private boolean isInteger(Number num) {
        return num instanceof Integer || num instanceof Long ||
                (num instanceof Double && num.doubleValue() == num.longValue());
    }

    // 枚举校验（完整实现）
    private void validateEnum(ONode enumNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        if (!enumNode.isArray()) return;

        for (ONode allowedValue : enumNode.getArray()) {
            if (deepEquals(allowedValue, dataNode)) {
                return;
            }
        }
        throw new JsonSchemaException("Value not in enum list at " + path.currentPath());
    }

    // 对象属性校验（完整实现）
    private void validateProperties(ONode propertiesNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        Map<String, ONode> properties = propertiesNode.getObject();
        Map<String, ONode> dataObj = dataNode.getObject();

        // 校验必填字段
        if (schema.hasKey("required")) {
            ONode requiredNode = schema.get("required");
            if (requiredNode.isArray()) {
                for (ONode requiredField : requiredNode.getArray()) {
                    String field = requiredField.getString();
                    if (!dataObj.containsKey(field)) {
                        throw new JsonSchemaException("Missing required field: " + field + " at " + path.currentPath());
                    }
                }
            }
        }

        // 校验每个属性
        for (Map.Entry<String, ONode> propEntry : properties.entrySet()) {
            String propName = propEntry.getKey();
            path.enterProperty(propName);
            if (dataObj.containsKey(propName)) {
                validateNode(propEntry.getValue(), dataObj.get(propName), path);
            }
            path.exit();
        }
    }

    // 数组约束校验（完整实现）
    private void validateArrayConstraints(ONode schemaNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        List<ONode> items = dataNode.getArray();

        if (schemaNode.hasKey("minItems")) {
            int min = schemaNode.get("minItems").getInt();
            if (items.size() < min) {
                throw new JsonSchemaException("Array length " + items.size() + " < minItems(" + min + ") at " + path.currentPath());
            }
        }
        if (schemaNode.hasKey("maxItems")) {
            int max = schemaNode.get("maxItems").getInt();
            if (items.size() > max) {
                throw new JsonSchemaException("Array length " + items.size() + " > maxItems(" + max + ") at " + path.currentPath());
            }
        }

        if (schemaNode.hasKey("items")) {
            ONode itemsSchema = schemaNode.get("items");
            for (int i = 0; i < items.size(); i++) {
                path.enterIndex(i);
                validateNode(itemsSchema, items.get(i), path);
                path.exit();
            }
        }
    }

    // 数值范围校验（完整实现）
    private void validateNumericConstraints(ONode schemaNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        double value = dataNode.getDouble();

        if (schemaNode.hasKey("minimum")) {
            double min = schemaNode.get("minimum").getDouble();
            if (value < min) {
                throw new JsonSchemaException("Value " + value + " < minimum(" + min + ") at " + path.currentPath());
            }
        }
        if (schemaNode.hasKey("maximum")) {
            double max = schemaNode.get("maximum").getDouble();
            if (value > max) {
                throw new JsonSchemaException("Value " + value + " > maximum(" + max + ") at " + path.currentPath());
            }
        }
    }

    // 字符串约束校验（完整实现）
    private void validateStringConstraints(ONode schemaNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        String value = dataNode.getString();

        if (schemaNode.hasKey("minLength")) {
            int min = schemaNode.get("minLength").getInt();
            if (value.length() < min) {
                throw new JsonSchemaException("String length " + value.length() + " < minLength(" + min + ") at " + path.currentPath());
            }
        }
        if (schemaNode.hasKey("maxLength")) {
            int max = schemaNode.get("maxLength").getInt();
            if (value.length() > max) {
                throw new JsonSchemaException("String length " + value.length() + " > maxLength(" + max + ") at " + path.currentPath());
            }
        }
        if (schemaNode.hasKey("pattern")) {
            String pattern = schemaNode.get("pattern").getString();
            if (!value.matches(pattern)) {
                throw new JsonSchemaException("String does not match pattern: " + pattern + " at " + path.currentPath());
            }
        }
    }

    // 条件校验（完整实现）
    private void validateConditional(ONode schemaNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        validateConditionalGroup(schemaNode, "anyOf", dataNode, path, false);
        validateConditionalGroup(schemaNode, "allOf", dataNode, path, true);
        validateConditionalGroup(schemaNode, "oneOf", dataNode, path, false);
    }

    private void validateConditionalGroup(ONode schemaNode, String key,
                                          ONode dataNode, PathTracker path,
                                          boolean requireAll) throws JsonSchemaException {
        if (!schemaNode.hasKey(key)) return;

        List<ONode> schemas = schemaNode.get(key).getArray();
        int matchCount = 0;
        List<JsonSchemaException> errors = new ArrayList<>();

        for (ONode subSchema : schemas) {
            try {
                validateNode(subSchema, dataNode, path);
                matchCount++;
            } catch (JsonSchemaException e) {
                errors.add(e);
                if (requireAll) throw e;
            }
        }

        if (requireAll && matchCount != schemas.size()) {
            throw new JsonSchemaException("Failed to satisfy allOf constraints at " + path.currentPath());
        }
        if (!requireAll && key.equals("anyOf") && matchCount == 0) {
            throw new JsonSchemaException("Failed to satisfy anyOf constraints at " + path.currentPath());
        }
        if (!requireAll && key.equals("oneOf") && matchCount != 1) {
            throw new JsonSchemaException("Must satisfy exactly one of oneOf constraints at " + path.currentPath());
        }
    }

    // 深度比较方法（完整实现）
    private boolean deepEquals(ONode a, ONode b) {
        if (a.type() != b.type()) return false;

        switch (a.type()) {
            case Null:
                return true;
            case Boolean:
                return a.getBoolean() == b.getBoolean();
            case Number:
                return a.getNumber().doubleValue() == b.getNumber().doubleValue();
            case String:
                return a.getString().equals(b.getString());
            case Array:
                List<ONode> aArr = a.getArray();
                List<ONode> bArr = b.getArray();
                if (aArr.size() != bArr.size()) return false;
                for (int i = 0; i < aArr.size(); i++) {
                    if (!deepEquals(aArr.get(i), bArr.get(i))) return false;
                }
                return true;
            case Object:
                Map<String, ONode> aObj = a.getObject();
                Map<String, ONode> bObj = b.getObject();
                if (aObj.size() != bObj.size()) return false;
                for (Map.Entry<String, ONode> entry : aObj.entrySet()) {
                    String key = entry.getKey();
                    if (!bObj.containsKey(key)) return false;
                    if (!deepEquals(entry.getValue(), bObj.get(key))) return false;
                }
                return true;
            default:
                return false;
        }
    }

    // 异常处理
    private JsonSchemaException typeMismatch(String expected, ONode actual, PathTracker path) {
        return new JsonSchemaException("Expected type " + expected + " but got " +
                SchemaUtil.getSchemaTypeName(actual) + " at " + path.currentPath());
    }

    // 预编译相关实现
    private Map<String, CompiledRule> compileSchema(ONode schema) {
        Map<String, CompiledRule> rules = new HashMap<>();
        compileSchemaRecursive(schema, rules, PathTracker.begin());
        return rules;
    }

    private void compileSchemaRecursive(ONode schemaNode, Map<String, CompiledRule> rules, PathTracker path) {
        List<ValidationRule> localRules = new ArrayList<>();

        if (schemaNode.hasKey("type")) {
            localRules.add(new TypeRule(schemaNode.get("type")));
        }
        if (schemaNode.hasKey("enum")) {
            localRules.add(new EnumRule(schemaNode.get("enum")));
        }

        if (!localRules.isEmpty()) {
            rules.put(path.currentPath(), new CompiledRule(localRules));
        }

        if (schemaNode.hasKey("properties")) {
            ONode propsNode = schemaNode.get("properties");
            for (Map.Entry<String, ONode> kv : propsNode.getObject().entrySet()) {
                path.enterProperty(kv.getKey());
                compileSchemaRecursive(kv.getValue(), rules, path);
                path.exit();
            }
        }

        if (schemaNode.hasKey("items")) {
            path.enterIndex(0);
            compileSchemaRecursive(schemaNode.get("items"), rules, path);
            path.exit();
        }
    }
}
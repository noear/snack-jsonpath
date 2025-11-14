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
import org.noear.snack4.jsonschema.generate.JsonSchemaGenerator;
import org.noear.snack4.jsonschema.rule.*;
import org.noear.snack4.jsonschema.generate.SchemaUtil;

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

        ONode oNode = new JsonSchemaGenerator(type).generate();

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


        // 处理对象属性校验
        if (dataNode.isObject() && schemaNode.hasKey("properties")) {
            validateProperties(schemaNode.get("properties"), dataNode, path);
        }

        // 处理数组项校验
        if (dataNode.isArray() && schemaNode.hasKey("items")) {
            validateArrayItems(schemaNode.get("items"), dataNode, path);
        }

        // 处理条件校验
        validateConditional(schemaNode, dataNode, path);
    }

    private void validateArrayItems(ONode itemsSchema, ONode dataNode, PathTracker path) throws JsonSchemaException {
        List<ONode> items = dataNode.getArray();
        for (int i = 0; i < items.size(); i++) {
            path.enterIndex(i);
            validateNode(itemsSchema, items.get(i), path);
            path.exit();
        }
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

    // 预编译相关实现
    private Map<String, CompiledRule> compileSchema(ONode schema) {
        Map<String, CompiledRule> rules = new HashMap<>();
        compileSchemaRecursive(schema, rules, PathTracker.begin());
        return rules;
    }

    private void compileSchemaRecursive(ONode schemaNode, Map<String, CompiledRule> rules, PathTracker path) {
        List<ValidationRule> localRules = new ArrayList<>();

        // 类型规则
        if (schemaNode.hasKey(SchemaUtil.NAME_TYPE)) {
            localRules.add(new TypeRule(schemaNode.get(SchemaUtil.NAME_TYPE)));
        }

        // 枚举规则
        if (schemaNode.hasKey("enum")) {
            localRules.add(new EnumRule(schemaNode.get("enum")));
        }

        // 字符串约束规则
        if (schemaNode.hasKey("minLength") || schemaNode.hasKey("maxLength") || schemaNode.hasKey("pattern")) {
            localRules.add(new StringConstraintRule(schemaNode));
        }

        // 数值约束规则
        if (schemaNode.hasKey("minimum") || schemaNode.hasKey("maximum")) {
            localRules.add(new NumericConstraintRule(schemaNode));
        }

        // 数组约束规则
        if (schemaNode.hasKey("minItems") || schemaNode.hasKey("maxItems")) {
            localRules.add(new ArrayConstraintRule(schemaNode));
        }

        if (!localRules.isEmpty()) {
            rules.put(path.currentPath(), new CompiledRule(localRules));
        }

        // 递归处理对象属性
        if (schemaNode.hasKey("properties")) {
            ONode propsNode = schemaNode.get("properties");
            for (Map.Entry<String, ONode> kv : propsNode.getObject().entrySet()) {
                path.enterProperty(kv.getKey());
                compileSchemaRecursive(kv.getValue(), rules, path);
                path.exit();
            }
        }

        // 递归处理数组项
        if (schemaNode.hasKey("items")) {
            path.enterIndex(0);
            compileSchemaRecursive(schemaNode.get("items"), rules, path);
            path.exit();
        }
    }
}
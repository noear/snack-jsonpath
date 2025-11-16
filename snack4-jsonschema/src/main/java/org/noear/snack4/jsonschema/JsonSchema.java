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
import org.noear.snack4.util.Asserts;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JSON模式验证器，支持JSON Schema规范
 *
 * @author noear
 * @since 4.0
 */
public class JsonSchema {
    public static JsonSchema ofJson(String jsonSchema) {
        if (Asserts.isEmpty(jsonSchema)) {
            throw new IllegalArgumentException("jsonSchema is empty");
        }
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

    // 优化点 1: 引入 Schema 片段缓存
    // 原始代码在处理 anyOf/oneOf/allOf 时，在 *运行时* 反复调用 compileSchemaFragment，
    // 这是巨大的性能黑洞。
    // 优化：我们为 JsonSchema 实例添加一个缓存。
    // 假设 ONode 的 hashCode 和 equals 是可靠的，可以作为 Map 的 Key。
    private final Map<ONode, Map<String, CompiledRule>> fragmentCache = new ConcurrentHashMap<>();

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
        // 验证从根 Schema、根数据和根路径开始
        validateNode(schema, data, PathTracker.begin());
    }

    public void validate(ONode data, PathTracker path) throws JsonSchemaException {
        // 验证从根 Schema、根数据和根路径开始
        validateNode(schema, data, path);
    }

    // 核心验证方法（完整实现）
    private void validateNode(ONode schemaNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        // 优化点 13 (Bug 修复):
        // 必须同时检查特定路径 (e.g., $[0]) 和通配符路径 (e.g., $[*])

        // 1. 检查特定路径的规则
        CompiledRule specificRule = compiledRules.get(path.currentPath());
        if (specificRule != null) {
            specificRule.validate(dataNode, path);
        }

        // 2. 检查通配符路径的规则 (对数组项至关重要)
        String wildcardPath = path.getWildcardPath();
        if (wildcardPath != null) {
            CompiledRule wildcardRule = compiledRules.get(wildcardPath);
            if (wildcardRule != null) {
                wildcardRule.validate(dataNode, path);
            }
        }

        // 2. 处理对象属性校验
        if (dataNode.isObject() && schemaNode.hasKey("properties")) {
            validateProperties(schemaNode, dataNode, path);
        }

        // 3. 处理数组项校验
        if (dataNode.isArray() && schemaNode.hasKey("items")) {
            validateArrayItems(schemaNode, dataNode, path);
        }

        // 4. 处理条件校验
        validateConditional(schemaNode, dataNode, path);
    }

    private void validateArrayItems(ONode schemaNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        ONode itemsSchema = schemaNode.get("items");

        List<ONode> items = dataNode.getArray();
        String wildcardPath = path.currentPath() + "[*]";

        for (int i = 0; i < items.size(); i++) {
            path.enterIndex(i);

            // 优化点 2: 数组校验逻辑简化
            // 原始代码在这里查找 itemRule，但这是不必要的。
            // compileSchemaRecursive 已经为 `items` 的子-schema
            // 在 `...[*]...` 路径上编译了规则。
            // 我们只需要像 properties 一样，递归调用 validateNode 即可。
            // validateNode 会在第一步自动查找 `...[i]` 和 `...[*]` 路径的规则。
            // （注意：为了让 `...[*]` 路径生效，compileSchemaRecursive 中
            //  的 `new PathTracker(itemsPath)` 是正确的。）

            // [原始的 itemRule 查找代码已被移除]

            // 递归调用 validateNode 来处理每个数组元素
            validateNode(itemsSchema, items.get(i), path);

            path.exit();
        }
    }

    // 对象属性校验（完整实现）
    private void validateProperties(ONode schemaNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        ONode propertiesNode = schemaNode.get("properties");

        Map<String, ONode> properties = propertiesNode.getObject();
        Map<String, ONode> dataObj = dataNode.getObject();

        // 优化点 3: 移除重复的 "required" 校验
        // 原始代码在这里有一大段 "required" 校验逻辑。
        // 但 "required" 关键字已经在 compileSchemaRecursive 中被编译成了
        // RequiredRule，并在 validateNode 的第一步中执行了。
        // 这里的代码是多余的，应删除，以保证“单一职责”。
        // [原始的 required 校验代码已被移除]

        // 校验每个属性
        for (Map.Entry<String, ONode> propEntry : properties.entrySet()) {
            String propName = propEntry.getKey();
            if (dataObj.containsKey(propName)) {
                path.enterProperty(propName);
                validateNode(propEntry.getValue(), dataObj.get(propName), path);
                path.exit();
            }
        }
    }

    // 条件校验（完整实现）
    private void validateConditional(ONode schemaNode, ONode dataNode, PathTracker path) throws JsonSchemaException {
        // allOf 的规则已经在编译时合并，不需要在运行时处理
        // (参见 compileSchemaRecursive 中的 优化点 4)

        // 只需要处理 anyOf 和 oneOf
        validateConditionalGroup(schemaNode, "anyOf", dataNode, path);
        validateConditionalGroup(schemaNode, "oneOf", dataNode, path);
    }

    private void validateConditionalGroup(ONode schemaNode, String key,
                                          ONode dataNode, PathTracker path) throws JsonSchemaException {
        if (!schemaNode.hasKey(key)) return;

        List<ONode> schemas = schemaNode.get(key).getArray();
        int matchCount = 0;
        List<String> errorMessages = new ArrayList<>();

        for (ONode subSchema : schemas) {
            // 优化点 1 (续): 使用缓存的编译片段
            // 不再在运行时调用 compileSchemaFragment，而是从缓存中获取。
            // computeIfAbsent 确保每个 subSchema 只被编译一次。
            Map<String, CompiledRule> tempRules = fragmentCache.computeIfAbsent(
                    subSchema,
                    s -> compileSchemaFragment(s) // 如果缓存未命中，则编译
            );

            // 使用一个临时的 PathTracker，用于隔离递归
            PathTracker tempPath = PathTracker.begin(); // 必须从 $ 开始

            try {
                // 关键修复：使用辅助方法，
                // 它使用 *临时规则集* 和 *独立的路径* 来验证
                validateNodeWithRules(subSchema, dataNode, tempPath, tempRules);
                matchCount++;
            } catch (JsonSchemaException e) {
                // 记录错误信息，以便在 anyOf/oneOf 最终失败时提供更详细的上下文
                errorMessages.add(e.getMessage());
            }
        }

        if (key.equals("anyOf") && matchCount == 0) {
            throw new JsonSchemaException("Failed to satisfy anyOf constraints at " + path.currentPath() + ". Errors: " + errorMessages);
        }
        if (key.equals("oneOf") && matchCount != 1) {
            throw new JsonSchemaException("Must satisfy exactly one of oneOf constraints (found " + matchCount + ") at " + path.currentPath() + ". Errors: " + errorMessages);
        }
    }

    /**
     * 辅助方法：编译一个 Schema 片段（只编译当前片段的所有规则）
     * 优化点 1 (续): 此方法现在只在缓存未命中时被调用。
     */
    private Map<String, CompiledRule> compileSchemaFragment(ONode schemaFragment) {
        Map<String, CompiledRule> rules = new LinkedHashMap<>();
        // 关键：片段编译必须从其自己的根（$）开始
        compileSchemaRecursive(schemaFragment, rules, PathTracker.begin());
        return rules;
    }

    /**
     * 辅助方法：验证一个 Schema 片段（使用临时的规则集合）
     * (这是对原始代码中 `validateNodeWithRules` 的保留和修复)
     */
    private void validateNodeWithRules(ONode schemaNode, ONode dataNode, PathTracker path, Map<String, CompiledRule> rules) throws JsonSchemaException {
        // 1. 执行当前节点的预编译规则
        // 关键修复：路径必须匹配。
        // tempRules 是从 $ 开始的，path 也必须是从 $ 开始。
        CompiledRule rule = rules.get(path.currentPath());
        if (rule != null) {
            rule.validate(dataNode, path);
        }

        // 2. 处理对象属性校验
        if (dataNode.isObject() && schemaNode.hasKey("properties")) {
            validatePropertiesWithRules(schemaNode, dataNode, path, rules);
        }

        // 3. 处理数组项校验
        if (dataNode.isArray() && schemaNode.hasKey("items")) {
            validateArrayItemsWithRules(schemaNode, dataNode, path, rules);
        }

        // 4. 递归处理条件（anyOf/oneOf）
        // (注意：allOf 应该在 compileSchemaFragment 时被合并，这里只处理 anyOf/oneOf)
        validateConditionalWithRules(schemaNode, "anyOf", dataNode, path, rules);
        validateConditionalWithRules(schemaNode, "oneOf", dataNode, path, rules);
    }

    // 针对 "WithRules" 版本的递归辅助方法
    private void validatePropertiesWithRules(ONode schemaNode, ONode dataNode, PathTracker path, Map<String, CompiledRule> rules) throws JsonSchemaException {
        ONode propertiesNode = schemaNode.get("properties");
        Map<String, ONode> properties = propertiesNode.getObject();
        Map<String, ONode> dataObj = dataNode.getObject();

        for (Map.Entry<String, ONode> propEntry : properties.entrySet()) {
            String propName = propEntry.getKey();
            if (dataObj.containsKey(propName)) {
                path.enterProperty(propName);
                validateNodeWithRules(propEntry.getValue(), dataObj.get(propName), path, rules);
                path.exit();
            }
        }
    }

    // 针对 "WithRules" 版本的递归辅助方法
    private void validateArrayItemsWithRules(ONode schemaNode, ONode dataNode, PathTracker path, Map<String, CompiledRule> rules) throws JsonSchemaException {
        ONode itemsSchema = schemaNode.get("items");
        List<ONode> items = dataNode.getArray();
        for (int i = 0; i < items.size(); i++) {
            path.enterIndex(i);
            validateNodeWithRules(itemsSchema, items.get(i), path, rules);
            path.exit();
        }
    }

    // 针对 "WithRules" 版本的递归辅助方法
    private void validateConditionalWithRules(ONode schemaNode, String key, ONode dataNode, PathTracker path, Map<String, CompiledRule> rules) throws JsonSchemaException {
        if (!schemaNode.hasKey(key)) return;

        List<ONode> schemas = schemaNode.get(key).getArray();
        int matchCount = 0;
        List<String> errorMessages = new ArrayList<>();

        for (ONode subSchema : schemas) {
            // 嵌套的 anyOf/oneOf 也需要使用缓存的、独立的规则集
            Map<String, CompiledRule> tempRules = fragmentCache.computeIfAbsent(
                    subSchema,
                    s -> compileSchemaFragment(s)
            );

            PathTracker tempPath = PathTracker.begin(); // 独立路径

            try {
                validateNodeWithRules(subSchema, dataNode, tempPath, tempRules);
                matchCount++;
            } catch (JsonSchemaException e) {
                errorMessages.add(e.getMessage());
            }
        }

        // 校验逻辑 (同 validateConditionalGroup)
        if (key.equals("anyOf") && matchCount == 0) {
            throw new JsonSchemaException("Failed to satisfy anyOf constraints at " + path.currentPath() + ". Errors: " + errorMessages);
        }
        if (key.equals("oneOf") && matchCount != 1) {
            throw new JsonSchemaException("Must satisfy exactly one of oneOf constraints (found " + matchCount + ") at " + path.currentPath() + ". Errors: " + errorMessages);
        }
    }


    // 预编译相关实现
    private Map<String, CompiledRule> compileSchema(ONode schema) {
        Map<String, CompiledRule> rules = new LinkedHashMap<>();
        compileSchemaRecursive(schema, rules, PathTracker.begin());
        return rules;
    }

    private void compileSchemaRecursive(ONode schemaNode, Map<String, CompiledRule> rules, PathTracker path) {
        if (schemaNode.hasKey("$ref")) {
            String refPath = schemaNode.get("$ref").getString();
            ONode referencedSchema = resolveRef(refPath);
            if (referencedSchema != null) {
                // 解析 $ref，并 *在当前路径* 编译引用的内容
                compileSchemaRecursive(referencedSchema, rules, path);
                return; // 已经处理完引用，跳过后续的规则提取
            } else {
                // 可选：抛出异常或记录警告
                // throw new JsonSchemaException("Could not resolve $ref: " + refPath);
            }
        }

        // 优化点 4: 在编译期合并 "allOf"
        // allOf 中的所有规则都必须满足，它们可以被“拍平”合并到当前路径的规则列表中。
        // 这避免了在运行时（validateNode）再去处理 allOf，提高了性能。
        if (schemaNode.hasKey("allOf")) {
            for (ONode subSchema : schemaNode.get("allOf").getArray()) {
                // 关键：使用 *相同的路径* 递归编译
                compileSchemaRecursive(subSchema, rules, path);
            }
        }

        List<ValidationRule> localRules = new ArrayList<>();

        // 类型规则
        if (schemaNode.hasKey(SchemaUtil.NAME_TYPE)) {
            localRules.add(new TypeRule(schemaNode.get(SchemaUtil.NAME_TYPE)));
        }
        // 枚举规则
        if (schemaNode.hasKey("enum")) {
            localRules.add(new EnumRule(schemaNode.get("enum")));
        }
        // 必需字段规则
        if (schemaNode.hasKey("required")) {
            localRules.add(new RequiredRule(schemaNode.get("required")));
        }
        // 字符串约束规则
        if (schemaNode.hasKey("minLength") || schemaNode.hasKey("maxLength") || schemaNode.hasKey("pattern")) {
            localRules.add(new StringConstraintRule(schemaNode));
        }
        // 数值约束规则
        if (schemaNode.hasKey("minimum") || schemaNode.hasKey("maximum") || schemaNode.hasKey("exclusiveMinimum") || schemaNode.hasKey("exclusiveMaximum")) {
            localRules.add(new NumericConstraintRule(schemaNode));
        }
        // 数组约束规则
        if (schemaNode.hasKey("minItems") || schemaNode.hasKey("maxItems")) {
            localRules.add(new ArrayConstraintRule(schemaNode));
        }
        // 额外属性规则
        if (schemaNode.hasKey("additionalProperties")) {
            // 优化点 5: (见 AdditionalPropertiesRule)
            // 传递整个 schemaNode 以便访问 'properties'
            localRules.add(new AdditionalPropertiesRule(schemaNode, this.schema));
        }

        // 优化点 6: (见 AnyOfRule/OneOfRule)
        // anyOf 和 oneOf 不能在编译期合并，它们必须在运行时作为 ValidationRule
        // 被 *独立* 验证。
        // 但这与上面 优化点 1 的“缓存片段”方案冲突了。
        // 结论：保持 优化点 1 的“运行时+缓存”方案，
        // 不将 anyOf/oneOf 编译为 ValidationRule，
        // 而是保留在 validateNode / validateNodeWithRules 中处理。
        // [原 优化点 6 的代码已被移除]

        if (!localRules.isEmpty()) {
            // 修复：不是替换，而是追加。
            // 允许多个规则 (例如 "allOf" 合并) 在同一路径上。
            CompiledRule existingRule = rules.get(path.currentPath());
            if (existingRule != null) {
                existingRule.addRules(localRules);
            } else {
                rules.put(path.currentPath(), new CompiledRule(localRules));
            }
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
            ONode itemsSchema = schemaNode.get("items");
            // 为通用 items 路径编译规则（用于没有特定索引的情况）
            String itemsPath = path.currentPath() + "[*]";
            compileSchemaRecursive(itemsSchema, rules, new PathTracker(itemsPath));
        }
    }

    private ONode resolveRef(String refPath) {
        if (refPath == null || !refPath.startsWith("#/")) {
            // 目前只支持本地根引用
            return null;
        }

        // 优化点 7: 健壮的 $ref 解析 (JSON Pointer)
        // 移除 "#/" 前缀
        String[] parts = refPath.substring(2).split("/");
        ONode current = this.schema; // 始终从根 schema 开始解析

        for (String part : parts) {
            if (current == null) {
                return null;
            }
            // JSON Pointer 规范要求:
            // 1. URL 解码
            // 2. 替换 ~1 为 /
            // 3. 替换 ~0 为 ~
            try {
                // 1. URL 解码
                part = URLDecoder.decode(part, "UTF-8");
                // 2. 和 3.
                part = part.replace("~1", "/").replace("~0", "~");
            } catch (UnsupportedEncodingException e) {
                // 不太可能发生
                throw new RuntimeException("UTF-8 encoding not supported", e);
            }

            if(current.isArray() && part.matches("\\d+")){
                // 支持数组索引
                current = current.get(Integer.parseInt(part));
            } else {
                current = current.get(part);
            }
        }

        return current;
    }
}
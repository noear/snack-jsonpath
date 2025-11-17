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

import org.noear.eggg.TypeEggg;
import org.noear.snack4.ONode;
import org.noear.snack4.jsonschema.generate.DefinerLib;
import org.noear.snack4.jsonschema.generate.JsonSchemaGenerator;
import org.noear.snack4.jsonschema.generate.SchemaDefiner;
import org.noear.snack4.jsonschema.generate.SchemaPatternDefiner;
import org.noear.snack4.jsonschema.validate.JsonSchemaValidator;
import org.noear.snack4.util.Asserts;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 *
 * @author noear 2025/11/17 created
 * @since 4.0
 */
public class JsonSchema {
    public static final JsonSchema DEFAULT = JsonSchema.builder().build();

    private final SchemaVersion version;
    private final boolean enableDefinitions;
    private final boolean printVersion;
    private final DefinerLib definerLib = DefinerLib.newInstance();

    public JsonSchema(SchemaVersion version, boolean enableDefinitions, boolean printVersion) {
        this.version = version;
        this.enableDefinitions = enableDefinitions;
        this.printVersion = printVersion;
    }

    public SchemaVersion getVersion() {
        return version;
    }

    public boolean isEnableDefinitions() {
        return enableDefinitions;
    }

    public boolean isPrintVersion() {
        return printVersion;
    }

    /**
     * 添加类型定义
     */
    public <T> void addDefiner(SchemaPatternDefiner<T> generator) {
        definerLib.addDefiner(generator);
    }

    /**
     * 添加类型定义
     */
    public <T> void addDefiner(Class<T> type, SchemaDefiner<T> generator) {
        if (generator instanceof SchemaPatternDefiner) {
            addDefiner((SchemaPatternDefiner<T>) generator);
        }

        definerLib.addDefiner(type, generator);
    }


    /**
     * 获取类型定义
     */
    public SchemaDefiner getDefiner(TypeEggg typeEggg) {
        return definerLib.getDefiner(typeEggg);
    }


    /**
     * 创建生成器
     */
    public JsonSchemaGenerator createGenerator(Type type) {
        return new JsonSchemaGenerator(type, this);
    }

    /**
     * 创建生成器
     */
    public JsonSchemaGenerator createGenerator(TypeEggg type) {
        return new JsonSchemaGenerator(type, this);
    }


    /**
     * 创建验证器
     */
    public JsonSchemaValidator createValidator(Type type) {
        Objects.requireNonNull(type, "type");

        ONode oNode = createGenerator(type).generate();
        if (oNode == null) {
            throw new JsonSchemaException("The type jsonSchema generation failed: " + type.toString());
        }

        return new JsonSchemaValidator(oNode);
    }

    /**
     * 创建验证器
     */
    public JsonSchemaValidator createValidator(TypeEggg typeEggg) {
        Objects.requireNonNull(typeEggg, "typeEggg");

        ONode oNode = createGenerator(typeEggg).generate();
        if (oNode == null) {
            throw new JsonSchemaException("The type jsonSchema generation failed: " + typeEggg.toString());
        }

        return new JsonSchemaValidator(oNode);
    }


    /**
     * 创建验证器
     */
    public JsonSchemaValidator createValidator(String jsonSchema) {
        if (Asserts.isEmpty(jsonSchema)) {
            throw new IllegalArgumentException("jsonSchema is empty");
        }
        return new JsonSchemaValidator(ONode.ofJson(jsonSchema));
    }

    /**
     * 创建验证器
     */
    public JsonSchemaValidator createValidator(ONode jsonSchema) {
        Objects.requireNonNull(jsonSchema, "jsonSchema");
        if (jsonSchema.isObject() == false) {
            throw new IllegalArgumentException("jsonSchema is invalid");
        }

        return new JsonSchemaValidator(jsonSchema);
    }


    /// ///////////////////

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private SchemaVersion version = SchemaVersion.DRAFT_7;
        private boolean enableDefinitions;
        private boolean printVersion;

        public Builder version(SchemaVersion version) {
            this.version = version;
            return this;
        }

        public Builder enableDefinitions(boolean enableDefinitions) {
            this.enableDefinitions = enableDefinitions;
            return this;
        }

        public Builder printVersion(boolean printVersion) {
            this.printVersion = printVersion;
            return this;
        }

        public JsonSchema build() {
            return new JsonSchema(version, enableDefinitions, printVersion);
        }
    }
}
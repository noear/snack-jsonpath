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
package org.noear.snack4.jsonschema.generate;

import org.noear.eggg.ClassEggg;
import org.noear.eggg.Property;
import org.noear.eggg.PropertyEggg;
import org.noear.eggg.TypeEggg;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttrHolder;
import org.noear.snack4.codec.util.EgggUtil;
import org.noear.snack4.jsonschema.JsonSchemaException;
import org.noear.snack4.jsonschema.SchemaKeyword;
import org.noear.snack4.jsonschema.SchemaType;
import org.noear.snack4.jsonschema.SchemaVersion;
import org.noear.snack4.jsonschema.generate.impl.*;
import org.noear.snack4.util.Asserts;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 对象编码器
 *
 * @author noear 2025/11/14 created
 * @since 4.0
 */
public class JsonSchemaGenerator {
    private final TypeEggg source0;
    private final Map<Object, Object> visited;
    private SchemaVersion version = SchemaVersion.DRAFT_7;
    private boolean enableDefinitions;
    private boolean printVersion;

    private final Map<String, ONode> definitions;
    private int definitionCounter = 0;

    public JsonSchemaGenerator withVersion(SchemaVersion version) {
        this.version = version;
        return this;
    }

    public JsonSchemaGenerator withEnableDefinitions(boolean enableDefinitions) {
        this.enableDefinitions = enableDefinitions;
        return this;
    }

    public JsonSchemaGenerator withPrintVersion(boolean printVersion) {
        this.printVersion = printVersion;
        return this;
    }

    public JsonSchemaGenerator(Type type) {
        Objects.requireNonNull(type, "Type cannot be null");

        if (type == void.class || type == Void.class) {
            throw new JsonSchemaException("Void type is not supported for JSON schema generation");
        }

        this.source0 = EgggUtil.getTypeEggg(type);
        this.visited = new IdentityHashMap<>();
        this.definitions = new LinkedHashMap<>();
    }

    /**
     * Java Object 编码为 ONode
     */
    public ONode generate() {
        try {
            return doGenerate();
        } catch (JsonSchemaException e) {
            throw e;
        } catch (Throwable e) {
            throw new JsonSchemaException("Failed to generate JSON schema for type: " +
                    source0.getType().getName(), e);
        }
    }

    private ONode doGenerate() throws Throwable {
        ONode target = new ONode();

        if (printVersion) {
            target.set(SchemaKeyword.SCHEMA, version.getIdentifier());
        }

        if (enableDefinitions) {
            String definitionsKey = getDefinitionsKey();
            target.getOrNew(definitionsKey);
        }

        ONode oNode = generateValueToNode(source0, null, target);

        if (oNode != null) {
            if (enableDefinitions && !definitions.isEmpty()) {
                String definitionsKey = getDefinitionsKey();
                oNode.getOrNew(definitionsKey).setAll(definitions);
            }
        }

        return oNode;
    }

    // 获取定义键名，根据版本使用不同的关键字
    private String getDefinitionsKey() {
        switch (version) {
            case DRAFT_7:
                return SchemaKeyword.DEFINITIONS;
            case DRAFT_2019_09:
            case DRAFT_2020_12:
            default:
                return SchemaKeyword.DEFS;
        }
    }

    // 判断是否应该为类型创建定义
    private boolean shouldCreateDefinition(TypeEggg typeEggg) {
        if (source0.equals(typeEggg)) {
            return false;
        }

        // 为自定义类创建定义，排除基本类型和系统类
        Class<?> clazz = typeEggg.getType();
        return !clazz.isPrimitive() && !typeEggg.isJdkType();
    }

    // 获取定义名称
    private String getDefinitionName(TypeEggg typeEggg) {
        Class<?> clazz = typeEggg.getType();
        String simpleName = clazz.getSimpleName();
        if (simpleName.isEmpty()) {
            // 对于匿名类等，使用生成的名称
            return "Definition_" + (definitionCounter++);
        }
        return simpleName;
    }

    // 创建引用节点
    private ONode createReference(String definitionName) {
        ONode refNode = new ONode().asObject();
        refNode.set(SchemaKeyword.REF, "#/" + getDefinitionsKey() + "/" + definitionName);
        return refNode;
    }

    // 值转ONode处理
    private ONode generateValueToNode(TypeEggg typeEggg, ONodeAttrHolder attr, ONode target) throws Throwable {
        // 优先使用自定义编解码器
        TypeGenerator generator = GeneratorLib.getGenerator(typeEggg);
        if (generator != null) {
            return generator.generate(attr, typeEggg, target);
        }

        if (typeEggg.isCollection()) {
            return generateCollectionToNode(typeEggg, target);
        } else if (typeEggg.isMap()) {
            return generateMapToNode(typeEggg, target);
        } else {
            if (typeEggg.isArray()) {
                return generateArrayToNode(typeEggg, target);
            } else {
                return generateBeanToNode(typeEggg, target);
            }
        }
    }

    // 对象转ONode核心逻辑
    private ONode generateBeanToNode(TypeEggg typeEggg, ONode target) throws Throwable {
        // 循环引用检测
        if (visited.containsKey(typeEggg)) {
            return handleCircularReference(typeEggg);
        } else {
            visited.put(typeEggg, null);
        }

        target.set(SchemaKeyword.TYPE, SchemaType.OBJECT);

        // 如果是复杂类型且启用了定义，先创建定义占位符
        String definitionName = null;
        if (enableDefinitions && shouldCreateDefinition(typeEggg)) {
            definitionName = getDefinitionName(typeEggg);
            definitions.put(definitionName, target); // 先放入占位符
        }


        try {
            ONode oProperties = target.getOrNew(SchemaKeyword.PROPERTIES).asObject();
            ONode oRequired = target.getOrNew(SchemaKeyword.REQUIRED).asArray();

            ClassEggg classEggg = typeEggg.getClassEggg();

            for (PropertyEggg pw : classEggg.getPropertyEgggs()) {
                final Property property;
                if (pw.getGetterEggg() != null) {
                    property = pw.getGetterEggg();
                } else {
                    property = pw.getFieldEggg();
                }

                if (property == null) {
                    continue;
                }

                ONodeAttrHolder attr = property.getDigest();
                if (property.isTransient() || attr.isEncode() == false) {
                    continue;
                }

                ONode propertyNode = generateValueToNode(property.getTypeEggg(), attr, new ONode());

                if (propertyNode != null) {
                    if (Asserts.isNotEmpty(attr.getDescription())) {
                        propertyNode.set(SchemaKeyword.DESCRIPTION, attr.getDescription());
                    }

                    if (Asserts.isNotEmpty(attr.getTitle())) {
                        propertyNode.set(SchemaKeyword.TITLE, attr.getTitle());
                    }

                    if (attr.isFlat()) {
                        if (propertyNode.isObject()) {
                            oProperties.setAll(propertyNode.getObject());

                            if (attr.isRequired()) {
                                oRequired.addAll(propertyNode.getObject().keySet());
                            }
                        }
                    } else {
                        oProperties.set(property.getAlias(), propertyNode);

                        if (attr.isRequired()) {
                            oRequired.add(property.getAlias());
                        }
                    }
                }
            }

            // 如果启用了定义，返回引用而不是完整的对象
            if (enableDefinitions && definitionName != null) {
                return createReference(definitionName);
            }

            return target;
        } finally {
            visited.remove(typeEggg);
        }
    }

    private ONode handleCircularReference(TypeEggg typeEggg) {
        if (enableDefinitions) {
            // 如果启用了定义，为循环引用创建引用
            String definitionName = getDefinitionName(typeEggg);
            if (definitions.containsKey(definitionName)) {
                return createReference(definitionName);
            }
        }

        // 即使没有启用定义，也要返回一个占位符而不是null
        return new ONode().asObject()
                .set(SchemaKeyword.TYPE, SchemaType.OBJECT)
                .set(SchemaKeyword.DESCRIPTION, "Circular reference detected for: " + typeEggg.getType().getSimpleName());
    }

    // 处理数组类型
    private ONode generateArrayToNode(TypeEggg typeEggg, ONode target) throws Throwable {
        target.set(SchemaKeyword.TYPE, SchemaType.ARRAY);

        ONode itemsType = generateValueToNode(EgggUtil.getTypeEggg(typeEggg.getType().getComponentType()), null, new ONode());
        target.set(SchemaKeyword.ITEMS, itemsType);

        return target;
    }

    // 处理集合类型
    private ONode generateCollectionToNode(TypeEggg typeEggg, ONode target) throws Throwable {
        target.set(SchemaKeyword.TYPE, SchemaType.ARRAY);

        if (typeEggg.isParameterizedType()) {
            ONode itemsType = generateValueToNode(EgggUtil.getTypeEggg(typeEggg.getActualTypeArguments()[0]), null, new ONode());
            target.set(SchemaKeyword.ITEMS, itemsType);
        }

        return target;
    }

    // 处理Map类型
    private ONode generateMapToNode(TypeEggg typeEggg, ONode target) throws Throwable {
        target.set(SchemaKeyword.TYPE, SchemaType.OBJECT);

        // 对于Map，可以添加additionalProperties来说明值类型
        if (typeEggg.isParameterizedType() && typeEggg.getActualTypeArguments().length > 1) {
            TypeEggg keyEggg = EgggUtil.getTypeEggg(typeEggg.getActualTypeArguments()[0]);
            TypeEggg valueEggg = EgggUtil.getTypeEggg(typeEggg.getActualTypeArguments()[1]);


            if (keyEggg.getType() != Object.class && keyEggg.getType() != String.class) {
                ONode keySchema = generateValueToNode(keyEggg, null, new ONode());

                if (keySchema != null) {
                    ONode propertyNamesSchema = new ONode().asObject();
                    propertyNamesSchema.set(SchemaKeyword.TYPE, SchemaType.STRING);
                    ONode propertyNamesInnerSchema = generateValueToNode(keyEggg, null, new ONode());
                    target.set(SchemaKeyword.PROPERTY_NAMES, propertyNamesInnerSchema);
                }
            }

            if (valueEggg.getType() != Object.class) {
                ONode valueSchema = generateValueToNode(valueEggg, null, new ONode());
                target.set(SchemaKeyword.ADDITIONAL_PROPERTIES, valueSchema);
            } else {
                target.set(SchemaKeyword.ADDITIONAL_PROPERTIES, true);
            }
        } else {
            target.set(SchemaKeyword.ADDITIONAL_PROPERTIES, true);
        }

        return target;
    }
}
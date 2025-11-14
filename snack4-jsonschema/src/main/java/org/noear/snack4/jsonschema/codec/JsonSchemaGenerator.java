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

import org.noear.eggg.ClassEggg;
import org.noear.eggg.Property;
import org.noear.eggg.PropertyEggg;
import org.noear.eggg.TypeEggg;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttrHolder;
import org.noear.snack4.codec.util.EgggUtil;
import org.noear.snack4.jsonschema.JsonSchemaException;
import org.noear.snack4.jsonschema.codec.generate.*;
import org.noear.snack4.util.Asserts;

import java.lang.reflect.Type;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 对象编码器
 *
 * @author noear 2025/11/14 created
 * @since 4.0
 */
public class JsonSchemaGenerator {
    static final List<TypePatternGenerator> TYPE_PATTERN_GENERATORS = new ArrayList<>();
    static final Map<Class<?>, TypeGenerator> TYPE_GENERATOR_MAP = new HashMap<>();

    static {
        TYPE_PATTERN_GENERATORS.add(new _DatePatternGenerator());
        TYPE_PATTERN_GENERATORS.add(new _EnumPatternGenerator());
        TYPE_PATTERN_GENERATORS.add(new _NumberPatternGenerator());

        TYPE_GENERATOR_MAP.put(Boolean.class, BooleanGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(boolean.class, BooleanGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(Character.class, CharGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(char.class, CharGenerator.getInstance());

        TYPE_GENERATOR_MAP.put(String.class, new StringGenerator());
        TYPE_GENERATOR_MAP.put(URI.class, new URIGenerator());

        TYPE_GENERATOR_MAP.put(LocalDate.class, new LocalDateGenerator());
        TYPE_GENERATOR_MAP.put(LocalTime.class, new LocalTimeGenerator());
        TYPE_GENERATOR_MAP.put(LocalDateTime.class, new LocalDateTimeGenerator());
    }

    private static TypeGenerator getGenerator(TypeEggg typeEggg) {
        TypeGenerator tmp = TYPE_GENERATOR_MAP.get(typeEggg.getType());

        if (tmp == null) {
            for (TypePatternGenerator b1 : TYPE_PATTERN_GENERATORS) {
                if (b1.canGenerate(typeEggg)) {
                    return b1;
                }
            }
        }

        return tmp;
    }

    /**
     * Java Object 编码为 ONode
     */
    public static ONode generate(Type type) {
        if (type == null) {
            return null;
        }

        if (type == void.class) {
            throw new JsonSchemaException("Not support the void type");
        }

        return new JsonSchemaGenerator(EgggUtil.getTypeEggg(type)).generate();
    }

    private final TypeEggg source0;

    private final Map<Object, Object> visited;


    private JsonSchemaGenerator(TypeEggg typeEggg) {
        this.source0 = typeEggg;
        this.visited = new IdentityHashMap<>();
    }

    /**
     * Java Object 编码为 ONode
     */
    public ONode generate() {
        try {
            return generateValueToNode(source0, null);
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }

            throw new JsonSchemaException("Failed to encode bean to ONode", e);
        }
    }

    // 值转ONode处理
    private ONode generateValueToNode(TypeEggg typeEggg, ONodeAttrHolder attr) throws Throwable {
        // 优先使用自定义编解码器
        TypeGenerator codec = getGenerator(typeEggg);
        if (codec != null) {
            return codec.generate(attr, typeEggg, new ONode());
        }

        if (typeEggg.isCollection()) {
            return generateCollectionToNode(typeEggg);
        } else if (typeEggg.isMap()) {
            return generateMapToNode(typeEggg);
        } else {
            if (typeEggg.isArray()) {
                return generateArrayToNode(typeEggg);
            } else {
                return generateBeanToNode(typeEggg);
            }
        }
    }

    // 对象转ONode核心逻辑
    private ONode generateBeanToNode(TypeEggg typeEggg) throws Throwable {
        // 循环引用检测
        if (visited.containsKey(typeEggg)) {
            return null;
        } else {
            visited.put(typeEggg, null);
        }

        ONode target = new ONode().asObject();

        target.set(SchemaUtil.NAME_TYPE, SchemaUtil.TYPE_OBJECT);
        ONode oProperties = target.getOrNew(SchemaUtil.NAME_PROPERTIES).asObject();
        ONode oRequired = target.getOrNew(SchemaUtil.NAME_REQUIRED).asArray();

        try {
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

                ONode propertyNode = generateValueToNode(property.getTypeEggg(), attr);

                if (propertyNode != null) {

                    if (Asserts.isNotEmpty(attr.getDescription())) {
                        propertyNode.set(SchemaUtil.NAME_DESCRIPTION, attr.getDescription());
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
        } finally {
            visited.remove(typeEggg);
        }

        return target;
    }

    // 处理数组类型
    private ONode generateArrayToNode(TypeEggg typeEggg) throws Throwable {
        ONode tmp = new ONode();
        tmp.set("type", SchemaUtil.TYPE_ARRAY);

        ONode itemsType = generateValueToNode(EgggUtil.getTypeEggg(typeEggg.getType().getComponentType()), null);
        tmp.set("items", itemsType);

        return tmp;
    }

    // 处理集合类型
    private ONode generateCollectionToNode(TypeEggg typeEggg) throws Throwable {
        ONode tmp = new ONode();
        tmp.set("type", SchemaUtil.TYPE_ARRAY);

        if (typeEggg.isParameterizedType()) {
            ONode itemsType = generateValueToNode(EgggUtil.getTypeEggg(typeEggg.getActualTypeArguments()[0]), null);
            tmp.set("items", itemsType);
        }

        return tmp;
    }

    // 处理Map类型
    private ONode generateMapToNode(TypeEggg typeEggg) throws Throwable {
        ONode tmp = new ONode();
        tmp.set("type", SchemaUtil.TYPE_OBJECT);
        return tmp;
    }
}
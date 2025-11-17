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

import org.noear.eggg.TypeEggg;
import org.noear.snack4.jsonschema.generate.impl.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 生成器库
 *
 * @author noear 2025/11/16 created
 * @since 4.0
 */
public class GeneratorLib {
    private static final GeneratorLib DEFAULT = new GeneratorLib(null).loadDefault();

    private final List<TypePatternGenerator> TYPE_PATTERN_GENERATORS = new ArrayList<>();
    private final Map<Class<?>, TypeGenerator> TYPE_GENERATOR_MAP = new ConcurrentHashMap<>();

    private final GeneratorLib parent;

    public GeneratorLib(GeneratorLib parent) {
        this.parent = parent;
    }

    public static GeneratorLib newInstance() {
        return new GeneratorLib(DEFAULT);
    }

    /**
     * 添加生成器
     */
    public <T> void addGenerator(TypePatternGenerator<T> generator) {
        TYPE_PATTERN_GENERATORS.add(generator);
    }

    /**
     * 添加生成器
     */
    public <T> void addGenerator(Class<T> type, TypeGenerator<T> generator) {
        if (generator instanceof TypePatternGenerator) {
            addGenerator((TypePatternGenerator<T>) generator);
        }

        TYPE_GENERATOR_MAP.put(type, generator);
    }


    /**
     * 获取生成器
     */
    public TypeGenerator getGenerator(TypeEggg typeEggg) {
        TypeGenerator generator = TYPE_GENERATOR_MAP.get(typeEggg.getType());

        if (generator == null) {
            for (TypePatternGenerator b1 : TYPE_PATTERN_GENERATORS) {
                if (b1.canGenerate(typeEggg)) {
                    return b1;
                }
            }

            if (parent != null) {
                return parent.getGenerator(typeEggg);
            }
        }

        return generator;
    }

    private GeneratorLib loadDefault() {
        TYPE_PATTERN_GENERATORS.add(new _DatePatternGenerator());
        TYPE_PATTERN_GENERATORS.add(new _EnumPatternGenerator());
        TYPE_PATTERN_GENERATORS.add(new _NumberPatternGenerator());

        TYPE_GENERATOR_MAP.put(Boolean.class, BooleanGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(boolean.class, BooleanGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(Character.class, CharGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(char.class, CharGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(Byte.class, ByteGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(byte.class, ByteGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(Byte[].class, ByteArrayGenerator.getInstance());
        TYPE_GENERATOR_MAP.put(byte[].class, ByteArrayGenerator.getInstance());

        TYPE_GENERATOR_MAP.put(String.class, new StringGenerator());
        TYPE_GENERATOR_MAP.put(URI.class, new URIGenerator());

        TYPE_GENERATOR_MAP.put(LocalDate.class, new LocalDateGenerator());
        TYPE_GENERATOR_MAP.put(LocalTime.class, new LocalTimeGenerator());
        TYPE_GENERATOR_MAP.put(LocalDateTime.class, new LocalDateTimeGenerator());

        return this;
    }
}
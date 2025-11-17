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
 * 定义库
 *
 * @author noear 2025/11/16 created
 * @since 4.0
 */
public class DefinerLib {
    private static final DefinerLib DEFAULT = new DefinerLib(null).loadDefault();

    private final List<TypePatternDefiner> TYPE_PATTERN_GENERATORS = new ArrayList<>();
    private final Map<Class<?>, TypeDefiner> TYPE_GENERATOR_MAP = new ConcurrentHashMap<>();

    private final DefinerLib parent;

    public DefinerLib(DefinerLib parent) {
        this.parent = parent;
    }

    public static DefinerLib newInstance() {
        return new DefinerLib(DEFAULT);
    }

    /**
     * 添加生成器
     */
    public <T> void addDefiner(TypePatternDefiner<T> generator) {
        TYPE_PATTERN_GENERATORS.add(generator);
    }

    /**
     * 添加生成器
     */
    public <T> void addDefiner(Class<T> type, TypeDefiner<T> generator) {
        if (generator instanceof TypePatternDefiner) {
            addDefiner((TypePatternDefiner<T>) generator);
        }

        TYPE_GENERATOR_MAP.put(type, generator);
    }


    /**
     * 获取生成器
     */
    public TypeDefiner getDefiner(TypeEggg typeEggg) {
        TypeDefiner tmp = TYPE_GENERATOR_MAP.get(typeEggg.getType());

        if (tmp == null) {
            for (TypePatternDefiner b1 : TYPE_PATTERN_GENERATORS) {
                if (b1.canDefine(typeEggg)) {
                    return b1;
                }
            }

            if (parent != null) {
                return parent.getDefiner(typeEggg);
            }
        }

        return tmp;
    }

    private DefinerLib loadDefault() {
        TYPE_PATTERN_GENERATORS.add(new _DatePatternDefiner());
        TYPE_PATTERN_GENERATORS.add(new _EnumPatternDefiner());
        TYPE_PATTERN_GENERATORS.add(new _NumberPatternDefiner());

        TYPE_GENERATOR_MAP.put(Boolean.class, BooleanDefiner.getInstance());
        TYPE_GENERATOR_MAP.put(boolean.class, BooleanDefiner.getInstance());
        TYPE_GENERATOR_MAP.put(Character.class, CharDefiner.getInstance());
        TYPE_GENERATOR_MAP.put(char.class, CharDefiner.getInstance());
        TYPE_GENERATOR_MAP.put(Byte.class, ByteDefiner.getInstance());
        TYPE_GENERATOR_MAP.put(byte.class, ByteDefiner.getInstance());
        TYPE_GENERATOR_MAP.put(Byte[].class, ByteArrayDefiner.getInstance());
        TYPE_GENERATOR_MAP.put(byte[].class, ByteArrayDefiner.getInstance());

        TYPE_GENERATOR_MAP.put(String.class, new StringDefiner());
        TYPE_GENERATOR_MAP.put(URI.class, new URIDefiner());

        TYPE_GENERATOR_MAP.put(LocalDate.class, new LocalDateDefiner());
        TYPE_GENERATOR_MAP.put(LocalTime.class, new LocalTimeDefiner());
        TYPE_GENERATOR_MAP.put(LocalDateTime.class, new LocalDateTimeDefiner());

        return this;
    }
}
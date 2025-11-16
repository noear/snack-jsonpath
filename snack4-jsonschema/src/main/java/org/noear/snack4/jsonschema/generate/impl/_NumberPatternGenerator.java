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
package org.noear.snack4.jsonschema.generate.impl;

import org.noear.eggg.TypeEggg;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttrHolder;
import org.noear.snack4.jsonschema.SchemaKeywords;
import org.noear.snack4.jsonschema.generate.TypePatternGenerator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import java.math.BigInteger;

/**
 *
 * @author noear 2025/11/14 created
 * @since 4.0
 */
public class _NumberPatternGenerator implements TypePatternGenerator {
    private static final Set<Class<?>> INTEGER_TYPES = new HashSet<>(
            Arrays.asList(Byte.class, byte.class,
                    Short.class, short.class,
                    Integer.class, int.class,
                    Long.class, long.class,
                    BigInteger.class
            )
    );

    @Override
    public boolean canGenerate(TypeEggg typeEggg) {
        return typeEggg.isNumber();
    }

    @Override
    public ONode generate(ONodeAttrHolder att, TypeEggg typeEggg, ONode target) {
        if (INTEGER_TYPES.contains(typeEggg.getType())) {
            target.set(SchemaKeywords.KEYWORD_TYPE, SchemaKeywords.TYPE_INTEGER);
        } else {
            target.set(SchemaKeywords.KEYWORD_TYPE, SchemaKeywords.TYPE_NUMBER);
        }

        return target;
    }
}
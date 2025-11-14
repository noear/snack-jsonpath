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
package org.noear.snack4.jsonschema.codec.generate;

import org.noear.eggg.TypeEggg;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttrHolder;
import org.noear.snack4.jsonschema.codec.SchemaUtil;
import org.noear.snack4.jsonschema.codec.TypePatternGenerator;

import java.math.BigInteger;

/**
 *
 * @author noear 2025/11/14 created
 * @since 4.0
 */
public class _NumberPatternGenerator implements TypePatternGenerator {
    @Override
    public boolean canGenerate(TypeEggg typeEggg) {
        return typeEggg.isNumber();
    }

    @Override
    public ONode generate(ONodeAttrHolder att, TypeEggg typeEggg, ONode target) {
        Class<?> type = typeEggg.getType();

        if (type.equals(Byte.class) || type.equals(byte.class) ||
                type.equals(Short.class) || type.equals(short.class) ||
                type.equals(Integer.class) || type.equals(int.class) ||
                type.equals(Long.class) || type.equals(long.class) ||
                type.equals(BigInteger.class)) {
            target.set(SchemaUtil.NAME_TYPE, SchemaUtil.TYPE_INTEGER);
        } else {
            target.set(SchemaUtil.NAME_TYPE, SchemaUtil.TYPE_NUMBER);
        }

        return target;
    }
}

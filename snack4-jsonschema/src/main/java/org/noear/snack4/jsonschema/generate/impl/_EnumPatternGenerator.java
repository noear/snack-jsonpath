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
import org.noear.snack4.jsonschema.SchemaKeyword;
import org.noear.snack4.jsonschema.SchemaType;
import org.noear.snack4.jsonschema.generate.TypePatternGenerator;

/**
 *
 * @author noear 2025/11/14 created
 * @since 4.0
 */
public class _EnumPatternGenerator implements TypePatternGenerator {
    @Override
    public boolean canGenerate(TypeEggg typeEggg) {
        return typeEggg.isEnum();
    }

    @Override
    public ONode generate(ONodeAttrHolder att, TypeEggg typeEggg, ONode target) {
        target.set(SchemaKeyword.TYPE, SchemaType.STRING);

        target.getOrNew(SchemaKeyword.ENUM).then(n -> {
            for (Object e : typeEggg.getType().getEnumConstants()) {
                n.add(e.toString());
            }
        });

        return target;
    }
}

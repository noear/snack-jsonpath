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
import org.noear.snack4.jsonschema.SchemaFormat;
import org.noear.snack4.jsonschema.SchemaKeyword;
import org.noear.snack4.jsonschema.SchemaType;
import org.noear.snack4.jsonschema.generate.SchemaPatternDefiner;

import java.util.Date;

/**
 *
 * @author noear 2025/10/3 created
 * @since 4.0
 */
public class _DatePatternDefiner implements SchemaPatternDefiner {
    @Override
    public boolean canDefine(TypeEggg typeEggg) {
        return Date.class.isAssignableFrom(typeEggg.getType());
    }

    @Override
    public ONode define(TypeEggg typeEggg, ONode target) {
        return target.set(SchemaKeyword.TYPE, SchemaType.STRING)
                .set(SchemaKeyword.FORMAT, SchemaFormat.DATE_TIME);
    }
}
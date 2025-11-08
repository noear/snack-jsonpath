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
package org.noear.snack4.codec;

import org.noear.eggg.TypeEggg;
import org.noear.snack4.Feature;
import org.noear.snack4.Options;
import org.noear.snack4.annotation.ONodeAttrHolder;

import java.lang.reflect.Type;

/**
 *
 * @author noear 2025/10/7 created
 * @since 4.0
 */
public class DecodeContext<T> {
    private final Options options;
    private final ONodeAttrHolder attr;
    private final T target;
    private final TypeEggg typeEggg;

    public DecodeContext(Options options, ONodeAttrHolder attr, T target, TypeEggg typeEggg) {
        this.options = options;
        this.attr = attr;
        this.target = target;
        this.typeEggg = typeEggg;
    }

    public Options getOptions() {
        return options;
    }

    public ONodeAttrHolder getAttr() {
        return attr;
    }

    public T getTarget() {
        return target;
    }

    public TypeEggg getTypeEggg() {
        return typeEggg;
    }

    public Class<?> getType() {
        return typeEggg.getType();
    }

    public Type getGenericType() {
        return typeEggg.getGenericType();
    }

    public boolean hasFeature(Feature feature) {
        if (attr != null) {
            if (attr.hasFeature(feature)) {
                return true;
            }
        }

        return options.hasFeature(feature);
    }
}
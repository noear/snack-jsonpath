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

import org.noear.eggg.GenericResolver;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;

/**
 * 类型引用
 *
 * @author noear 2019/11/22 created
 * @since 4.0
 * */
public abstract class TypeRef<T> {
    private final Type initialType;
    private Map<String, Type> genericInfo;

    protected TypeRef() {
        Type sc = this.getClass().getGenericSuperclass();
        this.initialType = ((ParameterizedType) sc).getActualTypeArguments()[0];
    }


    public TypeRef<T> where(String typeVar, Type type) {
        if (genericInfo == null) {
            genericInfo = new HashMap<>();
        }

        genericInfo.put(typeVar, type);
        return this;
    }

    /**
     * 获取类型
     *
     */
    public Type getType() {
        return resolveType(this.initialType, this.genericInfo);
    }

    private static Type resolveType(Type type, Map<String, Type> genericInfo) {
        if (genericInfo != null && genericInfo.size() > 0) {
            if (type instanceof TypeVariable) {
                TypeVariable<?> typeVar = (TypeVariable<?>) type;
                String name = typeVar.getName();
                if (genericInfo.containsKey(name)) {
                    return genericInfo.get(name);
                }

                return type;

            } else if (type instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) type;
                Type rawType = pt.getRawType();
                Type ownerType = pt.getOwnerType();
                Type[] args = pt.getActualTypeArguments();

                Type[] resolvedArgs = new Type[args.length];
                boolean changed = false;
                for (int i = 0; i < args.length; i++) {
                    resolvedArgs[i] = resolveType(args[i], genericInfo);
                    if (resolvedArgs[i] != args[i]) {
                        changed = true;
                    }
                }

                if (changed) {
                    return new GenericResolver.ParameterizedTypeImpl((Class<?>) rawType, resolvedArgs, ownerType);
                }
                return type;

            }
        }

        return type;
    }

    public static <E> TypeRef<List<E>> listOf(Type elementType) {
        return new TypeRef<List<E>>() {
        }.where("E", elementType);
    }

    public static <E> TypeRef<Set<E>> setOf(Type elementType) {
        return new TypeRef<Set<E>>() {
        }.where("E", elementType);
    }

    public static <K, V> TypeRef<Map<K, V>> mapOf(Type keyType, Type valueType) {
        return new TypeRef<Map<K, V>>() {
        }.where("K", keyType).where("V", valueType);
    }
}
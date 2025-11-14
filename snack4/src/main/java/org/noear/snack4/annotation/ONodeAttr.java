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
package org.noear.snack4.annotation;

import org.noear.snack4.Feature;
import org.noear.snack4.codec.ObjectDecoder;
import org.noear.snack4.codec.ObjectEncoder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 节点属性元
 *
 * @author noear 2025/3/16 created
 * @since 4.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
public @interface ONodeAttr {
    /**
     * 键名
     */
    String name() default "";

    /**
     * 标题
     */
    String title() default "";

    /**
     * 描述
     */
    String description() default "";

    /**
     * 必须的
     */
    boolean required() default true;

    /**
     * 格式化
     */
    String format() default "";

    /**
     * 时区
     */
    String timezone() default "";

    /**
     * 扁平化
     */
    boolean flat() default false;

    /**
     * 特性
     */
    Feature[] features() default {};

    /**
     * 乎略
     */
    boolean ignore() default false;

    /**
     * 是否编码（序列化）
     */
    boolean encode() default true;

    /**
     * 是否解码（反序列化）
     */
    boolean decode() default true;

    /**
     * 自定义编码器
     */
    Class<? extends ObjectEncoder> encoder() default ObjectEncoder.class;

    /**
     * 自定义解码器
     */
    Class<? extends ObjectDecoder> decoder() default ObjectDecoder.class;
}
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
package org.noear.snack4.codec.encode;

import org.noear.eggg.FieldEggg;
import org.noear.snack4.Feature;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttrHolder;
import org.noear.snack4.codec.EncodeContext;
import org.noear.snack4.codec.ObjectPatternEncoder;
import org.noear.snack4.codec.util.EgggUtil;
import org.noear.snack4.codec.util.EnumWrap;

/**
 *
 * @author noear 2025/10/3 created
 * @since 4.0
 */
public class _EnumPatternEncoder implements ObjectPatternEncoder<Enum> {

    @Override
    public boolean canEncode(Object value) {
        return value instanceof Enum;
    }

    @Override
    public ONode encode(EncodeContext ctx, Enum value, ONode target) {
        EnumWrap ew = EnumWrap.from(value.getClass());
        Object o = ew.getCustomValue(value);

        //如果为空代表该枚举没有被标注继续采用常规序列化方式
        if (o != null) {
            return target.setValue(o);
        } else {
            if (ctx.hasFeature(Feature.Write_EnumShapeAsObject)) {
                for (FieldEggg fe : EgggUtil.getClassEggg(value.getClass()).getAllFieldEgggs()) {
                    if (fe.isStatic() ||
                            fe.isTransient() ||
                            fe.<ONodeAttrHolder>getDigest().isEncode() == false ||
                            fe.getField().getDeclaringClass() == Enum.class) {
                        continue;
                    }

                    target.set(fe.getAlias(), fe.getValue(value));
                }

                if (target.isObject()) {
                    //说明上面有 set 进去了
                    return target;
                }
            }

            if (ctx.hasFeature(Feature.Write_EnumUsingToString)) {
                return target.setValue(value.toString());
            } else if (ctx.hasFeature(Feature.Write_EnumUsingName)) {
                return target.setValue(value.name());
            } else {
                return target.setValue(value.ordinal());
            }
        }
    }
}
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
package org.noear.snack4.codec.decode;

import org.noear.eggg.ConstrEggg;
import org.noear.eggg.ParamEggg;
import org.noear.snack4.ONode;
import org.noear.snack4.codec.DecodeContext;
import org.noear.snack4.codec.ObjectPatternDecoder;
import org.noear.snack4.codec.util.EnumWrap;
import org.noear.snack4.SnackException;

/**
 *
 * @author noear 2025/10/3 created
 * @since 4.0
 */
public class _EnumPatternDecoder implements ObjectPatternDecoder<Object> {

    @Override
    public boolean canDecode(Class<?> clazz) {
        return clazz.isEnum();
    }

    @Override
    public Object decode(DecodeContext ctx, ONode node) {
        ConstrEggg constrEggg = ctx.getTypeEggg().getClassEggg().getCreator();
        Enum eItem = null;

        if (constrEggg != null && constrEggg.isStatic()) {
            if (constrEggg.getParamCount() != 1) {
                throw new SnackException("Enum creator must be 1 param: " + ctx.getType().getTypeName());
            }

            try {
                ParamEggg p1 = constrEggg.getParamEgggAt(0);
                Object arg1;

                if (node.isObject()) {
                    //可能是对象
                    arg1 = node.get(p1.getName()).toBean(p1.getType());
                } else {
                    //否则作单值处理
                    arg1 = node.toBean(p1.getType());
                }

                eItem = constrEggg.newInstance(arg1);
            } catch (Exception e) {
                throw new SnackException(
                        "Decode failure for '" + ctx.getType().getTypeName() +
                                "' from value: " + node.getString(), e);
            }
        } else {
            EnumWrap ew = EnumWrap.from(ctx.getType());

            //尝试自定义获取
            if (ew.hasCustom()) {
                //按自定义获取
                eItem = ew.getCustom(node.getString());
                // 获取不到则按名字获取
                if (eItem == null) {
                    eItem = ew.get(node.getString());
                }
            } else {
                if (node.isNumber()) {
                    //按顺序位获取
                    eItem = ew.get(node.getInt());
                } else {
                    //按名字获取
                    eItem = ew.get(node.getString());
                }
            }
        }

        if (eItem == null) {
            throw new SnackException(
                    "Decode failure for '" + ctx.getType().getTypeName() +
                            "' from value: " + node.getString());
        }

        return eItem;
    }
}
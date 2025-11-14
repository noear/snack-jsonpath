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
 *
 */
public class _NumberPatternGenerator implements TypePatternGenerator {
    @Override
    public boolean canEncode(TypeEggg typeEggg) {
        return typeEggg.isNumber();
    }

    @Override
    public ONode encode(ONodeAttrHolder att, TypeEggg typeEggg, ONode target) {
        Class<?> type = typeEggg.getType();

        if (type.equals(Byte.class) || type.equals(byte.class) ||
                type.equals(char.class) ||
                type.equals(Short.class) || type.equals(short.class) ||
                type.equals(Integer.class) || type.equals(int.class) ||
                type.equals(Long.class) || type.equals(long.class) ||
                type.equals(BigInteger.class)) {
            target.set("type", SchemaUtil.TYPE_INTEGER);
        } else {
            target.set("type", SchemaUtil.TYPE_NUMBER);
        }

        return target;
    }
}

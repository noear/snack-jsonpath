package org.noear.snack4.jsonschema.codec.generate;

import org.noear.eggg.TypeEggg;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttrHolder;
import org.noear.snack4.jsonschema.codec.SchemaUtil;
import org.noear.snack4.jsonschema.codec.TypePatternGenerator;

/**
 *
 * @author noear 2025/11/14 created
 *
 */
public class _EnumPatternGenerator implements TypePatternGenerator {
    @Override
    public boolean canEncode(TypeEggg typeEggg) {
        return typeEggg.isEnum();
    }

    @Override
    public ONode encode(ONodeAttrHolder att, TypeEggg typeEggg, ONode target) {
        target.set("type", SchemaUtil.TYPE_STRING);
        target.getOrNew("enum").then(n -> {
            for (Object e : typeEggg.getType().getEnumConstants()) {
                n.add(e.toString());
            }
        });

        return target;
    }
}

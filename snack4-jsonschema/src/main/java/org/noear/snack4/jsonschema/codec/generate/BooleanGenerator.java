package org.noear.snack4.jsonschema.codec.generate;

import org.noear.eggg.TypeEggg;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttrHolder;
import org.noear.snack4.jsonschema.codec.SchemaUtil;
import org.noear.snack4.jsonschema.codec.TypeGenerator;

/**
 *
 * @author noear 2025/11/14 created
 *
 */
public class BooleanGenerator implements TypeGenerator {
    private static final BooleanGenerator _instance = new BooleanGenerator();
    public static BooleanGenerator getInstance() {
        return _instance;
    }

    @Override
    public ONode encode(ONodeAttrHolder att, TypeEggg typeEggg, ONode target) {
        return target.set("type", SchemaUtil.TYPE_BOOLEAN);
    }
}

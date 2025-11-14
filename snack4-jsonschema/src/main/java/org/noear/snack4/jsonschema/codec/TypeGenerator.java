package org.noear.snack4.jsonschema.codec;

import org.noear.eggg.TypeEggg;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttrHolder;

/**
 *
 * @author noear 2025/11/14 created
 *
 */
public interface TypeGenerator {
    ONode encode(ONodeAttrHolder att, TypeEggg typeEggg, ONode target);
}

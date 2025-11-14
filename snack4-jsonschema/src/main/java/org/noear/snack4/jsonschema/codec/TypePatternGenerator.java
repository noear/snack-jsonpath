package org.noear.snack4.jsonschema.codec;

import org.noear.eggg.TypeEggg;

/**
 *
 * @author noear 2025/11/14 created
 *
 */
public interface TypePatternGenerator extends TypeGenerator {
    /**
     * 可以编码的
     */
    boolean canEncode(TypeEggg typeEggg);
}

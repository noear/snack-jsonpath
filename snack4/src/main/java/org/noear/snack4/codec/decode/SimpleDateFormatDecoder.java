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

import org.noear.snack4.ONode;
import org.noear.snack4.codec.DecodeContext;
import org.noear.snack4.codec.ObjectDecoder;
import org.noear.snack4.codec.CodecException;

import java.text.SimpleDateFormat;

/**
 *
 * @author noear 2025/10/3 created
 * @since 4.0
 */
public class SimpleDateFormatDecoder implements ObjectDecoder<SimpleDateFormat> {
    @Override
    public SimpleDateFormat decode(DecodeContext ctx, ONode node) {
        if (node.isNotEmptyString()) {
            return new SimpleDateFormat(node.<String>getValueAs());
        } else if (node.isObject()) {
            String pattern = node.get("pattern").getString();
            return new SimpleDateFormat(pattern);
        }

        throw new CodecException("Cannot be converted to SimpleDateFormat: " + node);
    }
}

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
import org.noear.snack4.codec.ObjectPatternDecoder;

import java.util.Currency;

/**
 *
 * @author noear 2025/10/13 created
 * @since 4.0
 */
public class _CurrencytPatternDecoder implements ObjectPatternDecoder<Currency> {

    @Override
    public boolean canDecode(Class<?> clazz) {
        return Currency.class.isAssignableFrom(clazz);
    }

    @Override
    public Currency decode(DecodeContext<Currency> ctx, ONode node) {
        if (node.isNotEmptyString()) {
            return Currency.getInstance(node.<String>getValueAs());
        } else {
            return null;
        }
    }
}

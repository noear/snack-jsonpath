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
package org.noear.snack4.json.util;

/**
 *
 * @author noear 2025/11/5 created
 * @since 4.0
 */
public class FormatUtil {
    public static boolean hasNestedJsonBlock(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        int start = 0;
        int end = str.length() - 1;

        // 跳过开头空白
        while (start <= end && Character.isWhitespace(str.charAt(start))) {
            start++;
        }

        // 跳过结尾空白
        while (end >= start && Character.isWhitespace(str.charAt(end))) {
            end--;
        }

        // 检查有效长度
        if (start >= end) {
            return false;
        }

        char first = str.charAt(start);
        char last = str.charAt(end);

        return (first == '{' && last == '}') || (first == '[' && last == ']');
    }
}

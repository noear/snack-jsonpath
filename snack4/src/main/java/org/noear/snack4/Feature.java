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
package org.noear.snack4;

/**
 * JSON 特性枚举（按读写方向分类）
 *
 * @author noear 2019/2/12 created
 * @since 4.0
 */
public enum Feature {
    //-----------------------------
    // 读取（反序列化）
    //-----------------------------

    /**
     * 读取时允许使用注释（只支持开头或结尾有注释）
     */
    Read_AllowComment,

    /**
     * 读取时禁止单引号字符串（默认支持）
     */
    Read_DisableSingleQuotes,

    /**
     * 读取时禁止未用引号包裹的键名（默认支持）
     */
    Read_DisableUnquotedKeys,

    /**
     * 读取时允许空的键名
     */
    Read_AllowEmptyKeys,

    /**
     * 读取时允许零开头的数字
     */
    Read_AllowZeroLeadingNumbers,


    /**
     * 读取时小蛇转为小驼峰风格
     */
    Read_ConvertSnakeToSmlCamel,

    /**
     * 读取时小驼峰转为小蛇风格
     */
    Read_ConvertCamelToSmlSnake,

    /**
     * 读取时自动展开行内JSON字符串 (如 {"data": "{\"id\":1}"} )
     */
    Read_UnwrapJsonString,

    /**
     * 读取时允许对任何字符进行反斜杠转义
     */
    Read_AllowBackslashEscapingAnyCharacter,

    /**
     * 读取时允许无效的转义符
     */
    Read_AllowInvalidEscapeCharacter,

    /**
     * 读取时允许未编码的控制符
     */
    Read_AllowUnescapedControlCharacters,

    /**
     * 读取使用大数字模式（避免精度丢失），用 BigDecimal 替代 Double,
     */
    Read_UseBigDecimalMode,

    /**
     * 读取使用大整型模式，用 BigInteger 替代 Long
     * */
    Read_UseBigIntegerMode,

    /**
     * 读取时允许使用获取器
     */
    Read_AllowUseGetter,

    /**
     * 读取时只能使用获取器
     */
    Read_OnlyUseGetter,

    /**
     * 读取数据中的类名（支持读取 @type 属性）
     */
    Read_AutoType,


    //-----------------------------
    // 写入（序列化）
    //-----------------------------
    /**
     * 遇到未知属性时是否抛出异常
     */
    Write_FailOnUnknownProperties,

    /**
     * 写入用无引号字段名
     *
     */
    Write_UnquotedFieldNames,

    /**
     * 写入时使用单引号
     */
    Write_UseSingleQuotes,

    /**
     * 写入 null
     */
    Write_Nulls,

    /**
     * 写入列表为 null 时转为空
     */
    Write_NullListAsEmpty,

    /**
     * 写入字符串为 null 时转为空
     */
    Write_NullStringAsEmpty,

    /**
     * 写入布尔为 null 时转为 false
     *
     */
    Write_NullBooleanAsFalse,

    /**
     * 写入数字为 null 时转为 0
     *
     */
    Write_NullNumberAsZero,

    /**
     * 写入允许使用设置器（默认为字段模式）
     */
    Write_AllowUseSetter,

    /**
     * 写入只能使用设置器
     */
    Write_OnlyUseSetter,

    /**
     * 写入允许使用有参数的构造器（默认为无参模式）
     */
    Write_AllowParameterizedConstructor,

    /**
     * 写入时使用漂亮格式（带缩进和换行）
     */
    Write_PrettyFormat,

    /**
     * 写入时名字使用小蛇风格
     */
    Write_UseSmlSnakeStyle,

    /**
     * 写入时名字使用小骆峰风格
     */
    Write_UseSmlCamelStyle,

    /**
     * 写入时枚举使用名称（默认使用名称）
     */
    Write_EnumUsingName,

    /**
     * 写入时枚举使用 toString
     */
    Write_EnumUsingToString,

    /**
     * 写入时枚举形状为对象
     */
    Write_EnumShapeAsObject,

    /**
     * 写入布尔时转为数字
     * */
    Write_BooleanAsNumber,

    /**
     * 写入类名
     */
    Write_ClassName,

    /**
     * 不写入Map类名
     */
    Write_NotMapClassName,

    /**
     * 不写入根类名
     */
    Write_NotRootClassName,

    /**
     * 写入使用原始反斜杠（`\\` 不会转为 `\\\\`）
     */
    Write_UseRawBackslash,

    /**
     * 写入兼容浏览器显示（转义非 ASCII 字符）
     */
    Write_BrowserCompatible,

    /**
     * 写入使用日期格式化（默认使用时间戳）
     */
    Write_UseDateFormat,

    /**
     * 写入数字类型
     */
    Write_NumberTypeSuffix,

    /**
     * 写入数字时使用字符串模式
     */
    Write_NumbersAsString,

    /**
     * 写入长整型时使用字符串模式
     */
    Write_LongAsString,

    /**
     * 写入双精度浮点数时使用字符串模式
     */
    Write_DoubleAsString,

    /**
     * 写入大数时使用 plain 模式
     */
    Write_BigDecimalAsPlain,

    /**
     * IETF_RFC_9535 兼容模式（默认）
     */
    JsonPath_IETF_RFC_9535,

    /**
     * Jayway 兼容模式
     */
    JsonPath_JaywayMode,

    /**
     * 无论路径是否明确，总是返回一个 List
     */
    JsonPath_AlwaysReturnList,

    /**
     * 作为路径列表
     */
    JsonPath_AsPathList,

    /**
     * 抑制异常。如果启用了 ALWAYS_RETURN_LIST，返回空列表 []；否则返回 null。
     */
    JsonPath_SuppressExceptions,
    ;


    private final long _mask;

    Feature() {
        _mask = (1L << ordinal());
    }

    public long mask() {
        return _mask;
    }

    public static long addFeatures(long ref, Feature... features) {
        for (Feature feature : features) {
            ref |= feature.mask();
        }
        return ref;
    }

    public static long removeFeatures(long ref, Feature... features) {
        for (Feature feature : features) {
            ref &= ~feature.mask();
        }
        return ref;
    }

    public static boolean hasFeature(long ref, Feature feature) {
        return (ref & feature.mask()) != 0;
    }
}
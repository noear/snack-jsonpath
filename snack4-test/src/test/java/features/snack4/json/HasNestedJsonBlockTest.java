package features.snack4.json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.CsvSource;
import org.noear.snack4.json.util.FormatUtil;

import static org.junit.jupiter.api.Assertions.*;


class HasNestedJsonBlockTest {

    // ========== 基础边界测试 ==========

    @ParameterizedTest
    @NullAndEmptySource
    void testNullAndEmpty(String input) {
        assertFalse(FormatUtil.hasNestedJsonBlock(input));
    }

    @Test
    void testSingleCharacter() {
        assertFalse(FormatUtil.hasNestedJsonBlock("a"));
        assertFalse(FormatUtil.hasNestedJsonBlock("{"));
        assertFalse(FormatUtil.hasNestedJsonBlock("}"));
        assertFalse(FormatUtil.hasNestedJsonBlock("["));
        assertFalse(FormatUtil.hasNestedJsonBlock("]"));
        assertFalse(FormatUtil.hasNestedJsonBlock(" "));
    }

    // ========== 有效JSON对象测试 ==========

    @Test
    void testValidJsonObject() {
        assertTrue(FormatUtil.hasNestedJsonBlock("{}"));
        assertTrue(FormatUtil.hasNestedJsonBlock("{\"key\":\"value\"}"));
        assertTrue(FormatUtil.hasNestedJsonBlock("{\"name\":\"John\",\"age\":30}"));
        assertTrue(FormatUtil.hasNestedJsonBlock("{\"nested\":{\"inner\":\"value\"}}"));
    }

    // ========== 有效JSON数组测试 ==========

    @Test
    void testValidJsonArray() {
        assertTrue(FormatUtil.hasNestedJsonBlock("[]"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[1,2,3]"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[\"a\",\"b\",\"c\"]"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[{\"obj\":\"value\"}]"));
    }

    // ========== 带空白字符测试 ==========

    @Test
    void testWithWhitespace() {
        // 前后空格
        assertTrue(FormatUtil.hasNestedJsonBlock("  {}  "));
        assertFalse(FormatUtil.hasNestedJsonBlock("  {  "));
        assertFalse(FormatUtil.hasNestedJsonBlock("  }  "));

        assertTrue(FormatUtil.hasNestedJsonBlock("  []  "));
        assertFalse(FormatUtil.hasNestedJsonBlock("  [  "));
        assertFalse(FormatUtil.hasNestedJsonBlock("  ]  "));

        assertTrue(FormatUtil.hasNestedJsonBlock("  {\"key\":\"value\"}  "));
        assertFalse(FormatUtil.hasNestedJsonBlock("  {\"key\":\"value\"  "));
        assertFalse(FormatUtil.hasNestedJsonBlock("  \"key\":\"value\"}  "));

        assertTrue(FormatUtil.hasNestedJsonBlock("  [1,2,3]  "));

        // 制表符和换行符
        assertTrue(FormatUtil.hasNestedJsonBlock("\t{}\t"));
        assertTrue(FormatUtil.hasNestedJsonBlock("\n[]\n"));
        assertTrue(FormatUtil.hasNestedJsonBlock("\r\n{\"key\":\"value\"}\r\n"));
    }

    @Test
    void testInternalWhitespace() {
        // 内部有空格但首尾正确
        assertTrue(FormatUtil.hasNestedJsonBlock("{ }"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[ ]"));
        assertTrue(FormatUtil.hasNestedJsonBlock("{ \"key\" : \"value\" }"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[ 1 , 2 , 3 ]"));
    }

    // ========== 无效格式测试 ==========

    @ParameterizedTest
    @ValueSource(strings = {
            "abc",
            "123",
            "true",
            "false",
            "null",
            "\"string\"",
            "'string'",
            "{invalid",
            "invalid}",
            "[invalid",
            "invalid]",
            "}{",
            "][",
            "{]",
            "[}"
    })
    void testInvalidFormats1(String input) {
        assertFalse(FormatUtil.hasNestedJsonBlock(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{{}",
            "[[]",
            "{}}",
            "[]]"
    })
    void testInvalidFormats2(String input) {
        assertTrue(FormatUtil.hasNestedJsonBlock(input));
    }

    // ========== 边界情况测试 ==========

    @Test
    void testOnlyWhitespace() {
        assertFalse(FormatUtil.hasNestedJsonBlock("   "));
        assertFalse(FormatUtil.hasNestedJsonBlock("\t\t"));
        assertFalse(FormatUtil.hasNestedJsonBlock("\n\n"));
        assertFalse(FormatUtil.hasNestedJsonBlock(" \t\n "));
    }

    @Test
    void testMixedWhitespaceAndContent() {
        // 开头有空白但内容无效
        assertFalse(FormatUtil.hasNestedJsonBlock("  invalid  "));
        assertFalse(FormatUtil.hasNestedJsonBlock("\t{invalid\t"));
        assertFalse(FormatUtil.hasNestedJsonBlock("\n[invalid\n"));

        // 结尾有空白但内容无效
        assertFalse(FormatUtil.hasNestedJsonBlock("invalid}  "));
        assertFalse(FormatUtil.hasNestedJsonBlock("invalid]\t"));
    }

    // ========== 性能相关测试 ==========

    @Test
    void testLongStringWithWhitespace() {
        // 测试长字符串带前后空格的性能 - 使用 StringBuilder 替代 repeat
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("x");
        }
        String longContent = sb.toString();
        String input = "   " + longContent + "   ";
        assertFalse(FormatUtil.hasNestedJsonBlock(input));
    }

    @Test
    void testValidLongJson() {
        // 测试有效长JSON - 使用 StringBuilder
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        for (int i = 0; i < 100; i++) {
            keyBuilder.append("x");
            valueBuilder.append("y");
        }

        String longKey = keyBuilder.toString();
        String longValue = valueBuilder.toString();
        String json = "{\"" + longKey + "\":\"" + longValue + "\"}";
        assertTrue(FormatUtil.hasNestedJsonBlock(json));
    }

    // ========== 特殊字符测试 ==========

    @Test
    void testJsonWithEscapedQuotes() {
        // 包含转义引号的JSON应该仍然被识别
        assertTrue(FormatUtil.hasNestedJsonBlock("{\"key\":\"value with \\\"quotes\\\"\"}"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[\"string with \\\"quotes\\\"\"]"));
    }

    @Test
    void testUnicodeCharacters() {
        // Unicode字符
        assertTrue(FormatUtil.hasNestedJsonBlock("{\"name\":\"中文\"}"));
        assertTrue(FormatUtil.hasNestedJsonBlock("{\"emoji\":\"😀\"}"));
    }

    // ========== 复杂嵌套测试 ==========

    @Test
    void testComplexNestedStructures() {
        // 复杂嵌套对象
        assertTrue(FormatUtil.hasNestedJsonBlock("{\"users\":[{\"name\":\"John\",\"pets\":[{\"type\":\"dog\"}]}]}"));

        // 复杂嵌套数组
        assertTrue(FormatUtil.hasNestedJsonBlock("[[[1,2],[3,4]],[[5,6],[7,8]]]"));
    }

    // ========== 参数化综合测试 ==========

    @ParameterizedTest
    @CsvSource({
            "{}, true",
            "{}, true",
            "[], true",
            "  {}  , true",
            "  []  , true",
            "{ \"key\": \"value\" }, true",
            "abc, false",
            "123, false",
            "true, false",
            "\"string\", false",
            "'string', false",
            "{, false",
            "}, false",
            "[, false",
            "], false",
            "'', false",
            "'   ', false"
    })
    void testComprehensive(String input, boolean expected) {
        assertEquals(expected, FormatUtil.hasNestedJsonBlock(input));
    }

    // ========== 额外边界测试 ==========

    @Test
    void testMinimalValidCases() {
        // 最小有效情况
        assertTrue(FormatUtil.hasNestedJsonBlock("{}"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[]"));

        // 几乎最小但无效
        assertFalse(FormatUtil.hasNestedJsonBlock("{ "));
        assertFalse(FormatUtil.hasNestedJsonBlock(" }"));
        assertFalse(FormatUtil.hasNestedJsonBlock("[ "));
        assertFalse(FormatUtil.hasNestedJsonBlock(" ]"));
    }

    @Test
    void testWhitespaceOnlyBetweenBraces() {
        // 只有空白在括号内
        assertTrue(FormatUtil.hasNestedJsonBlock("{ }"));
        assertTrue(FormatUtil.hasNestedJsonBlock("{  }"));
        assertTrue(FormatUtil.hasNestedJsonBlock("{\t}"));
        assertTrue(FormatUtil.hasNestedJsonBlock("{\n}"));
        assertTrue(FormatUtil.hasNestedJsonBlock("{\r\n}"));

        assertTrue(FormatUtil.hasNestedJsonBlock("[ ]"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[  ]"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[\t]"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[\n]"));
        assertTrue(FormatUtil.hasNestedJsonBlock("[\r\n]"));
    }
}
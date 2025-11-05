package features.snack4.json;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.CsvSource;
import org.noear.snack4.ONode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * ONode.hasNestedJson 方法单元测试
 */
class ONodeHasNestedJsonTest {

    // ========== 基础边界测试 ==========

    @ParameterizedTest
    @NullAndEmptySource
    void testNullAndEmpty(String input) {
        assertFalse(ONode.hasNestedJson(input));
    }

    @Test
    void testSingleCharacter() {
        assertFalse(ONode.hasNestedJson("a"));
        assertFalse(ONode.hasNestedJson("{"));
        assertFalse(ONode.hasNestedJson("}"));
        assertFalse(ONode.hasNestedJson("["));
        assertFalse(ONode.hasNestedJson("]"));
        assertFalse(ONode.hasNestedJson(" "));
    }

    // ========== 有效JSON对象测试 ==========

    @Test
    void testValidJsonObject() {
        assertTrue(ONode.hasNestedJson("{}"));
        assertTrue(ONode.hasNestedJson("{\"key\":\"value\"}"));
        assertTrue(ONode.hasNestedJson("{\"name\":\"John\",\"age\":30}"));
        assertTrue(ONode.hasNestedJson("{\"nested\":{\"inner\":\"value\"}}"));
    }

    // ========== 有效JSON数组测试 ==========

    @Test
    void testValidJsonArray() {
        assertTrue(ONode.hasNestedJson("[]"));
        assertTrue(ONode.hasNestedJson("[1,2,3]"));
        assertTrue(ONode.hasNestedJson("[\"a\",\"b\",\"c\"]"));
        assertTrue(ONode.hasNestedJson("[{\"obj\":\"value\"}]"));
    }

    // ========== 带空白字符测试 ==========

    @Test
    void testWithWhitespace() {
        // 前后空格
        assertTrue(ONode.hasNestedJson("  {}  "));
        assertFalse(ONode.hasNestedJson("  {  "));
        assertFalse(ONode.hasNestedJson("  }  "));

        assertTrue(ONode.hasNestedJson("  []  "));
        assertFalse(ONode.hasNestedJson("  [  "));
        assertFalse(ONode.hasNestedJson("  ]  "));

        assertTrue(ONode.hasNestedJson("  {\"key\":\"value\"}  "));
        assertFalse(ONode.hasNestedJson("  {\"key\":\"value\"  "));
        assertFalse(ONode.hasNestedJson("  \"key\":\"value\"}  "));

        assertTrue(ONode.hasNestedJson("  [1,2,3]  "));

        // 制表符和换行符
        assertTrue(ONode.hasNestedJson("\t{}\t"));
        assertTrue(ONode.hasNestedJson("\n[]\n"));
        assertTrue(ONode.hasNestedJson("\r\n{\"key\":\"value\"}\r\n"));
    }

    @Test
    void testInternalWhitespace() {
        // 内部有空格但首尾正确
        assertTrue(ONode.hasNestedJson("{ }"));
        assertTrue(ONode.hasNestedJson("[ ]"));
        assertTrue(ONode.hasNestedJson("{ \"key\" : \"value\" }"));
        assertTrue(ONode.hasNestedJson("[ 1 , 2 , 3 ]"));
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
        assertFalse(ONode.hasNestedJson(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "{{}",
            "[[]",
            "{}}",
            "[]]"
    })
    void testInvalidFormats2(String input) {
        assertTrue(ONode.hasNestedJson(input));
    }

    // ========== 边界情况测试 ==========

    @Test
    void testOnlyWhitespace() {
        assertFalse(ONode.hasNestedJson("   "));
        assertFalse(ONode.hasNestedJson("\t\t"));
        assertFalse(ONode.hasNestedJson("\n\n"));
        assertFalse(ONode.hasNestedJson(" \t\n "));
    }

    @Test
    void testMixedWhitespaceAndContent() {
        // 开头有空白但内容无效
        assertFalse(ONode.hasNestedJson("  invalid  "));
        assertFalse(ONode.hasNestedJson("\t{invalid\t"));
        assertFalse(ONode.hasNestedJson("\n[invalid\n"));

        // 结尾有空白但内容无效
        assertFalse(ONode.hasNestedJson("invalid}  "));
        assertFalse(ONode.hasNestedJson("invalid]\t"));
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
        assertFalse(ONode.hasNestedJson(input));
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
        assertTrue(ONode.hasNestedJson(json));
    }

    // ========== 特殊字符测试 ==========

    @Test
    void testJsonWithEscapedQuotes() {
        // 包含转义引号的JSON应该仍然被识别
        assertTrue(ONode.hasNestedJson("{\"key\":\"value with \\\"quotes\\\"\"}"));
        assertTrue(ONode.hasNestedJson("[\"string with \\\"quotes\\\"\"]"));
    }

    @Test
    void testUnicodeCharacters() {
        // Unicode字符
        assertTrue(ONode.hasNestedJson("{\"name\":\"中文\"}"));
        assertTrue(ONode.hasNestedJson("{\"emoji\":\"😀\"}"));
    }

    // ========== 复杂嵌套测试 ==========

    @Test
    void testComplexNestedStructures() {
        // 复杂嵌套对象
        assertTrue(ONode.hasNestedJson("{\"users\":[{\"name\":\"John\",\"pets\":[{\"type\":\"dog\"}]}]}"));

        // 复杂嵌套数组
        assertTrue(ONode.hasNestedJson("[[[1,2],[3,4]],[[5,6],[7,8]]]"));
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
        assertEquals(expected, ONode.hasNestedJson(input));
    }

    // ========== 额外边界测试 ==========

    @Test
    void testMinimalValidCases() {
        // 最小有效情况
        assertTrue(ONode.hasNestedJson("{}"));
        assertTrue(ONode.hasNestedJson("[]"));

        // 几乎最小但无效
        assertFalse(ONode.hasNestedJson("{ "));
        assertFalse(ONode.hasNestedJson(" }"));
        assertFalse(ONode.hasNestedJson("[ "));
        assertFalse(ONode.hasNestedJson(" ]"));
    }

    @Test
    void testWhitespaceOnlyBetweenBraces() {
        // 只有空白在括号内
        assertTrue(ONode.hasNestedJson("{ }"));
        assertTrue(ONode.hasNestedJson("{  }"));
        assertTrue(ONode.hasNestedJson("{\t}"));
        assertTrue(ONode.hasNestedJson("{\n}"));
        assertTrue(ONode.hasNestedJson("{\r\n}"));

        assertTrue(ONode.hasNestedJson("[ ]"));
        assertTrue(ONode.hasNestedJson("[  ]"));
        assertTrue(ONode.hasNestedJson("[\t]"));
        assertTrue(ONode.hasNestedJson("[\n]"));
        assertTrue(ONode.hasNestedJson("[\r\n]"));
    }
}
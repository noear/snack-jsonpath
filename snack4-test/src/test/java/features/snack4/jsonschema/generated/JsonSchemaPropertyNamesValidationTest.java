package features.snack4.jsonschema.generated;

import org.noear.snack4.ONode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.noear.snack4.jsonschema.JsonSchema;
import org.noear.snack4.jsonschema.JsonSchemaException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JsonSchema propertyNames 验证测试
 */
@DisplayName("JsonSchema propertyNames 验证测试")
class JsonSchemaPropertyNamesValidationTest {

    @Test
    @DisplayName("验证属性名长度约束")
    void testPropertyNameLength() {
        String schemaJson = "{" +
                "  \"type\": \"object\"," +
                "  \"propertyNames\": {" +
                "    \"type\": \"string\"," +
                "    \"minLength\": 3," +
                "    \"maxLength\": 5" +
                "  }" +
                "}";
        JsonSchema schema = JsonSchema.ofJson(schemaJson);

        // 有效用例：所有属性名长度在 3 到 5 之间
        assertDoesNotThrow(() -> schema.validate(ONode.ofJson("{\"key\": 1, \"name\": 2, \"id01\": 3}")));
        assertDoesNotThrow(() -> schema.validate(ONode.ofJson("{}"))); // 空对象，属性名集合为空，通过

        // 无效用例 1：属性名太短 (2个字符)
        assertThrows(JsonSchemaException.class, () -> schema.validate(ONode.ofJson("{\"k\": 1}")));

        // 无效用例 2：属性名太长 (6个字符)
        assertThrows(JsonSchemaException.class, () -> schema.validate(ONode.ofJson("{\"user_id\": 1}")));

        // 无效用例 3：混合无效和有效
        assertThrows(JsonSchemaException.class, () -> schema.validate(ONode.ofJson("{\"key\": 1, \"too_long\": 2}")));
    }

    @Test
    @DisplayName("验证属性名必须是数字格式的字符串")
    void testPropertyNameFormat() {
        String schemaJson = "{" +
                "  \"type\": \"object\"," +
                "  \"propertyNames\": {" +
                "    \"type\": \"string\"," +
                "    \"pattern\": \"^[0-9]+$\"" + // 属性名必须只包含数字
                "  }" +
                "}";
        JsonSchema schema = JsonSchema.ofJson(schemaJson);

        // 有效用例：属性名全部是数字
        assertDoesNotThrow(() -> schema.validate(ONode.ofJson("{\"101\": 1, \"25\": 2}")));
        assertDoesNotThrow(() -> schema.validate(ONode.ofJson("{\"9\": 9}")));

        // 无效用例 1：包含非数字字符
        assertThrows(JsonSchemaException.class, () -> schema.validate(ONode.ofJson("{\"id_1\": 1}")));

        // 无效用例 2：混合数字和非数字属性名
        assertThrows(JsonSchemaException.class, () -> schema.validate(ONode.ofJson("{\"222\": 1, \"name\": 2}")));
    }

    @Test
    @DisplayName("验证属性名必须是有效的URI")
    void testPropertyNameURIFormat() {
        String schemaJson = "{" +
                "  \"type\": \"object\"," +
                "  \"propertyNames\": {" +
                "    \"type\": \"string\"," +
                "    \"format\": \"uri\"" +
                "  }" +
                "}";
        JsonSchema schema = JsonSchema.ofJson(schemaJson);

        // 有效用例：属性名是有效的URI（通常是URL）
        assertDoesNotThrow(() -> schema.validate(ONode.ofJson("{\"http://example.com/id\": 1}")));
        assertDoesNotThrow(() -> schema.validate(ONode.ofJson("{\"urn:isbn:12345\": 2}")));

        // 无效用例 1：属性名不是有效的URI (通常的普通字符串)
        assertThrows(JsonSchemaException.class, () -> schema.validate(ONode.ofJson("{\"a a\": 1}")));

        // 无效用例 2：属性名包含非法字符
        assertThrows(JsonSchemaException.class, () -> schema.validate(ONode.ofJson("{\"http://bad url.com\": 2}")));
    }
}
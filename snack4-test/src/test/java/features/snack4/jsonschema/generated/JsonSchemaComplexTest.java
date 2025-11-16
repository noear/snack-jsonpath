package features.snack4.jsonschema.generated;

import org.noear.snack4.ONode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.noear.snack4.jsonschema.JsonSchema;
import org.noear.snack4.jsonschema.JsonSchemaException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JsonSchema 复杂模式测试
 */
@DisplayName("JsonSchema 复杂模式测试")
class JsonSchemaComplexTest {

    @Test
    @DisplayName("验证复杂嵌套模式")
    void testComplexNestedSchema() {
        String schemaJson = "{" +
                "\"$schema\": \"http://json-schema.org/draft-07/schema#\"," +
                "\"type\": \"object\"," +
                "\"properties\": {" +
                "  \"user\": {" +
                "    \"type\": \"object\"," +
                "    \"properties\": {" +
                "      \"name\": {\"type\": \"string\", \"minLength\": 1}," +
                "      \"age\": {\"type\": \"integer\", \"minimum\": 0}," +
                "      \"emails\": {" +
                "        \"type\": \"array\"," +
                "        \"items\": {\"type\": \"string\", \"pattern\": \"^\\\\S+@\\\\S+\\\\.\\\\S+$\"}" +
                "      }" +
                "    }," +
                "    \"required\": [\"name\", \"age\"]" +
                "  }," +
                "  \"settings\": {" +
                "    \"type\": \"object\"," +
                "    \"additionalProperties\": {\"type\": \"boolean\"}" +
                "  }" +
                "}," +
                "\"required\": [\"user\"]" +
                "}";

        JsonSchema schema = JsonSchema.ofJson(schemaJson);

        // 无效用例 - 邮箱格式错误
        String invalidData2 = "{" +
                "\"user\": {" +
                "  \"name\": \"John Doe\"," +
                "  \"age\": 30," +
                "  \"emails\": [\"invalid-email\"]" +
                "}" +
                "}";
        assertThrows(JsonSchemaException.class, () -> schema.validate(ONode.ofJson(invalidData2)));
    }

    @Test
    @DisplayName("验证引用模式")
    void testReferenceSchema() {
        String schemaJson = "{" +
                "\"definitions\": {" +
                "  \"address\": {" +
                "    \"type\": \"object\"," +
                "    \"properties\": {" +
                "      \"street\": {\"type\": \"string\"}," +
                "      \"city\": {\"type\": \"string\"}" +
                "    }," +
                "    \"required\": [\"street\", \"city\"]" +
                "  }" +
                "}," +
                "\"type\": \"object\"," +
                "\"properties\": {" +
                "  \"billingAddress\": {\"$ref\": \"#/definitions/address\"}," +
                "  \"shippingAddress\": {\"$ref\": \"#/definitions/address\"}" +
                "}" +
                "}";

        JsonSchema schema = JsonSchema.ofJson(schemaJson);

        // 有效用例
        String validData = "{" +
                "\"billingAddress\": {" +
                "  \"street\": \"123 Main St\"," +
                "  \"city\": \"Anytown\"" +
                "}," +
                "\"shippingAddress\": {" +
                "  \"street\": \"456 Oak Ave\"," +
                "  \"city\": \"Somewhere\"" +
                "}" +
                "}";
        assertDoesNotThrow(() -> schema.validate(ONode.ofJson(validData)));

        // 无效用例 - 地址缺少必需字段
        String invalidData = "{" +
                "\"billingAddress\": {" +
                "  \"street\": \"123 Main St\"" +
                "}" +
                "}";
        assertThrows(JsonSchemaException.class, () -> schema.validate(ONode.ofJson(invalidData)));
    }
}
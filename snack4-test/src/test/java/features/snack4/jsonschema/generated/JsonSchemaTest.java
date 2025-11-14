package features.snack4.jsonschema.generated;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttr;
import org.noear.snack4.jsonschema.JsonSchema;
import org.noear.snack4.jsonschema.generate.JsonSchemaGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JsonSchema 单元测试
 */
class JsonSchemaTest {

    // ========== 基础类型测试 ==========

    @Test
    void testOfType_String() {
        JsonSchema schema = JsonSchema.ofType(String.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("string", node.get("type").getString());
        assertFalse(node.hasKey("properties"));
    }

    @Test
    void testOfType_Integer() {
        JsonSchema schema = JsonSchema.ofType(Integer.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("integer", node.get("type").getString());
    }

    @Test
    void testOfType_int() {
        JsonSchema schema = JsonSchema.ofType(int.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("integer", node.get("type").getString());
    }

    @Test
    void testOfType_Double() {
        JsonSchema schema = JsonSchema.ofType(Double.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("number", node.get("type").getString());
    }

    @Test
    void testOfType_double() {
        JsonSchema schema = JsonSchema.ofType(double.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("number", node.get("type").getString());
    }

    @Test
    void testOfType_Boolean() {
        JsonSchema schema = JsonSchema.ofType(Boolean.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("boolean", node.get("type").getString());
    }

    @Test
    void testOfType_boolean() {
        JsonSchema schema = JsonSchema.ofType(boolean.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("boolean", node.get("type").getString());
    }

    @Test
    void testOfType_BigInteger() {
        JsonSchema schema = JsonSchema.ofType(BigInteger.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("integer", node.get("type").getString());
    }

    @Test
    void testOfType_BigDecimal() {
        JsonSchema schema = JsonSchema.ofType(BigDecimal.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("number", node.get("type").getString());
    }

    @Test
    void testOfType_Date() {
        JsonSchema schema = JsonSchema.ofType(Date.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("string", node.get("type").getString());
        assertEquals("date-time", node.get("format").getString());
    }

    @Test
    void testOfType_URI() {
        JsonSchema schema = JsonSchema.ofType(URI.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("string", node.get("type").getString());
        assertEquals("uri", node.get("format").getString());
    }

    @Test
    void testOfType_void() {
        Assertions.assertThrows(Throwable.class, ()->{
            JsonSchema.ofType(void.class);
        });
    }

    // ========== 数组和集合测试 ==========

    @Test
    void testOfType_StringArray() {
        JsonSchema schema = JsonSchema.ofType(String[].class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("array", node.get("type").getString());
        assertEquals("string", node.get("items").get("type").getString());
    }

    @Test
    void testOfType_IntegerArray() {
        JsonSchema schema = JsonSchema.ofType(Integer[].class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("array", node.get("type").getString());
        assertEquals("integer", node.get("items").get("type").getString());
    }

    @Test
    void testOfType_List() {
        JsonSchema schema = JsonSchema.ofType(List.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("array", node.get("type").getString());
    }

    @Test
    void testOfType_ArrayList() {
        JsonSchema schema = JsonSchema.ofType(ArrayList.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("array", node.get("type").getString());
    }

    @Test
    void testOfType_Set() {
        JsonSchema schema = JsonSchema.ofType(Set.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("array", node.get("type").getString());
    }

    // ========== Map 测试 ==========

    @Test
    void testOfType_Map() {
        JsonSchema schema = JsonSchema.ofType(Map.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());
    }

    @Test
    void testOfType_HashMap() {
        JsonSchema schema = JsonSchema.ofType(HashMap.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());
    }

    // ========== 枚举测试 ==========

    enum TestEnum {
        VALUE1, VALUE2, VALUE3
    }

    @Test
    void testOfType_Enum() {
        JsonSchema schema = JsonSchema.ofType(TestEnum.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("string", node.get("type").getString());
        assertTrue(node.get("enum").isArray());
        assertEquals(3, node.get("enum").getArray().size());
        assertTrue(node.get("enum").getArray().stream()
                .anyMatch(n -> "VALUE1".equals(n.getString())));
    }

    // ========== 自定义 POJO 测试 ==========

    static class SimpleUser {
        public String name;
        public int age;
        public boolean active;
    }

    @Test
    void testOfType_SimplePojo() {
        JsonSchema schema = JsonSchema.ofType(SimpleUser.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());

        ONode properties = node.get("properties");
        assertEquals("string", properties.get("name").get("type").getString());
        assertEquals("integer", properties.get("age").get("type").getString());
        assertEquals("boolean", properties.get("active").get("type").getString());
    }

    static class UserWithAnnotations {
        @ONodeAttr(name = "userName", required = true, description = "用户姓名")
        public String name;

        @ONodeAttr(required = false, description = "用户年龄")
        public Integer age;

        public String email; // 无注解字段
    }

    @Test
    void testOfType_PojoWithAnnotations() {
        JsonSchema schema = JsonSchema.ofType(UserWithAnnotations.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());

        ONode properties = node.get("properties");
        System.out.println(properties.toJson());

        assertEquals("string", properties.get("userName").get("type").getString());
        assertEquals("用户姓名", properties.get("userName").get("description").getString());

        assertEquals("integer", properties.get("age").get("type").getString());
        assertEquals("用户年龄", properties.get("age").get("description").getString());

        assertEquals("string", properties.get("email").get("type").getString());

        // 检查 required 字段
        ONode required = node.get("required");
        assertTrue(required.isArray());
        assertTrue(required.getArray().stream()
                .anyMatch(n -> "userName".equals(n.getString())));
    }

    // ========== 嵌套对象测试 ==========

    static class Address {
        public String street;
        public String city;
        public String zipCode;
    }

    static class UserWithAddress {
        public String name;
        public Address address;
    }

    @Test
    void testOfType_NestedPojo() {
        JsonSchema schema = JsonSchema.ofType(UserWithAddress.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());

        ONode properties = node.get("properties");
        assertEquals("string", properties.get("name").get("type").getString());

        ONode addressProps = properties.get("address").get("properties");
        assertEquals("string", addressProps.get("street").get("type").getString());
        assertEquals("string", addressProps.get("city").get("type").getString());
        assertEquals("string", addressProps.get("zipCode").get("type").getString());
    }

    // ========== 泛型类型测试 ==========

    static class GenericResponse<T> {
        public boolean success;
        public String message;
        public T data;
    }

    static class PageResult<T> {
        public List<T> items;
        public int total;
        public int pageSize;
    }

    @Test
    void testOfType_GenericClass() {
        JsonSchema schema = JsonSchema.ofType(GenericResponse.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());

        ONode properties = node.get("properties");
        assertEquals("boolean", properties.get("success").get("type").getString());
        assertEquals("string", properties.get("message").get("type").getString());
        // data 字段类型会根据泛型处理
    }

    // ========== Optional 测试 ==========

    @Test
    void testOfType_OptionalString() {
        JsonSchema schema = JsonSchema.ofType(Optional.class);
        String json = schema.toJson();

        // Optional 类型应该被解包处理
        ONode node = ONode.ofJson(json);
        // 根据 handleParameterizedType 逻辑，Optional 会被处理为其内部类型
    }

    // ========== 复杂集合测试 ==========

    @Test
    void testOfType_ListOfStrings() {
        // 注意：由于类型擦除，直接测试 List<String> 可能无法获取泛型信息
        // 这里主要测试基础 List 类型
        JsonSchema schema = JsonSchema.ofType(List.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("array", node.get("type").getString());
    }

    @Test
    void testOfType_MapStringObject() {
        JsonSchema schema = JsonSchema.ofType(Map.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());
    }

    // ========== 继承关系测试 ==========

    static class BaseEntity {
        public Long id;
        public Date createTime;
    }

    static class Employee extends BaseEntity {
        public String name;
        public String department;
    }

    @Test
    void testOfType_Inheritance() {
        JsonSchema schema = JsonSchema.ofType(Employee.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());

        ONode properties = node.get("properties");
        // 应该包含基类和子类的所有字段
        assertTrue(properties.hasKey("id"));
        assertTrue(properties.hasKey("createTime"));
        assertTrue(properties.hasKey("name"));
        assertTrue(properties.hasKey("department"));
    }

    // ========== 静态内部类测试 ==========

    static class StaticNestedClass {
        public String nestedField;
    }

    @Test
    void testOfType_StaticNestedClass() {
        JsonSchema schema = JsonSchema.ofType(StaticNestedClass.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());
        assertTrue(node.get("properties").hasKey("nestedField"));
    }

    // ========== 接口测试 ==========

    interface SomeInterface {
        String getValue();
    }

    @Test
    void testOfType_Interface() {
        JsonSchema schema = JsonSchema.ofType(SomeInterface.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        // 接口应该被处理为 object 类型
        assertEquals("object", node.get("type").getString());
    }

    // ========== 验证 toJson 输出格式 ==========

    @Test
    void testToJson_ValidJson() {
        JsonSchema schema = JsonSchema.ofType(String.class);
        String json = schema.toJson();

        // 验证输出是有效的 JSON
        assertDoesNotThrow(() -> ONode.ofJson(json));

        // 验证基本结构
        ONode node = ONode.ofJson(json);
        assertTrue(node.hasKey("type"));
    }

    @Test
    void testToJson_ConsistentOutput() {
        JsonSchema schema1 = JsonSchema.ofType(String.class);
        JsonSchema schema2 = JsonSchema.ofType(String.class);

        String json1 = schema1.toJson();
        String json2 = schema2.toJson();

        assertEquals(json1, json2);
    }

    // ========== 边界情况测试 ==========

    @Test
    void testOfType_NullType() {
        // 测试 null 类型（应该使用 Void 或 void）
        assertThrows(Exception.class, () -> {
            JsonSchema.ofType(null);
        });
    }

    @Test
    void testOfType_PrimitiveTypes() {
        // 测试所有基本类型
        testPrimitiveType(byte.class, "integer");
        testPrimitiveType(short.class, "integer");
        testPrimitiveType(int.class,"integer");
        testPrimitiveType(long.class,"integer");
        testPrimitiveType(float.class, "number");
        testPrimitiveType(double.class,"number");
        testPrimitiveType(boolean.class,"boolean");
        testPrimitiveType(char.class,"string");
    }

    private void testPrimitiveType(Class<?> primitiveType, String ref) {
        JsonSchema schema = JsonSchema.ofType(primitiveType);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertTrue(node.hasKey("type"));

        String type = node.get("type").getString();
        System.out.println(type);
        assertTrue(type.equals(ref));//type.equals("number") || type.equals("boolean") || type.equals("string"));
    }

    // ========== 性能测试 ==========

    @Test
    void testToJson_Performance() {
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 100; i++) {
            JsonSchema schema = JsonSchema.ofType(ComplexObject.class);
            String json = schema.toJson();
            assertNotNull(json);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 确保 100 次转换在合理时间内完成（例如 5 秒内）
        assertTrue(duration < 5000, "Performance test took too long: " + duration + "ms");
    }

    // 复杂对象用于性能测试
    static class ComplexObject {
        public String name;
        public List<String> tags;
        public Map<String, Object> metadata;
        public NestedObject nested;
        public ComplexObject[] children;
    }

    static class NestedObject {
        public int level;
        public String description;
        public Date timestamp;
    }

    // ========== 验证 Schema 正确性测试 ==========

    @Test
    void testSchemaValidation_SimpleObject() {
        JsonSchema schema = JsonSchema.ofType(SimpleUser.class);
        String jsonSchema = schema.toJson();
        System.out.println(jsonSchema);

        // 使用生成的 schema 验证有效数据
        ONode validData = new ONode();
        validData.set("name", "John Doe");
        validData.set("age", 30);
        validData.set("active", true);

        assertDoesNotThrow(() -> schema.validate(validData));
    }

    @Test
    void testToJson_WithEnumDescription() {
        JsonSchema schema = JsonSchema.ofType(TestEnum.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        assertEquals("string", node.get("type").getString());

        ONode enumArray = node.get("enum");
        assertTrue(enumArray.isArray());
        assertTrue(enumArray.getArray().size() > 0);
    }

    // ========== 特殊场景测试 ==========

    static class CircularReference {
        public String name;
        public CircularReference next;
    }

    @Test
    void testOfType_CircularReference() {
        // 测试循环引用场景
        ONode schema = new JsonSchemaGenerator(CircularReference.class)
                .withEnableDefinitions(true)
                .generate();
        String json = schema.toJson();

        System.out.println(json);

        ONode node = ONode.ofJson(json);
        assertEquals("object", node.get("type").getString());

        ONode properties = node.get("properties");
        assertEquals("string", properties.get("name").get("type").getString());

        // next 字段应该被正确处理
        assertTrue(properties.hasKey("next"));
    }

    static class ClassWithTransient {
        public String normalField;
        public transient String transientField;
        public static String staticField = "test";
    }

    @Test
    void testOfType_TransientAndStaticFields() {
        JsonSchema schema = JsonSchema.ofType(ClassWithTransient.class);
        String json = schema.toJson();

        ONode node = ONode.ofJson(json);
        ONode properties = node.get("properties");

        // 应该只包含普通字段，不包含 transient 和 static 字段
        assertTrue(properties.hasKey("normalField"));
        assertFalse(properties.hasKey("transientField"));
        assertFalse(properties.hasKey("staticField"));
    }
}
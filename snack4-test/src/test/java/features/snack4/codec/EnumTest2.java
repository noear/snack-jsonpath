package features.snack4.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.noear.snack4.Feature;
import org.noear.snack4.ONode;
import org.noear.snack4.Options;
import org.noear.snack4.annotation.ONodeAttr;
import org.noear.snack4.annotation.ONodeCreator;

/**
 *
 * @author noear 2025/11/8 created
 *
 */
public class EnumTest2 {
    @Test
    public void case10() {
        String json = ONode.serialize(Gender.MALE);
        System.out.println(json);
        Assertions.assertEquals("1", json);

        json = ONode.serialize(Gender.MALE, Feature.Write_EnumUsingName);
        System.out.println(json);
        Assertions.assertEquals("\"MALE\"", json);

        json = ONode.serialize(Gender.MALE, Feature.Write_EnumShapeAsObject);
        System.out.println(json);
        Assertions.assertEquals("{\"code\":11,\"name\":\"男\"}", json);

        json = ONode.serialize(Gender.MALE, Options.of()
                .addEncoder(Gender.class, (ctx, value, target) -> {
                    return target.setValue(value.code + 100);
                })
                .addDecoder(Gender.class, (ctx, node) -> {
                    return Gender.fromCode(node.getInt() - 100);
                }));

        System.out.println(json);
        Assertions.assertEquals("111", json);

        json = ONode.serialize(Gender3.MALE);
        System.out.println(json);
        Assertions.assertEquals("11", json);
    }

    @Test
    public void case11() {
        Assertions.assertEquals(Gender.MALE, ONode.deserialize("1", Gender.class));
        Assertions.assertEquals(Gender.MALE, ONode.deserialize("\"MALE\"", Gender.class));

        Assertions.assertEquals(Gender2.MALE, ONode.deserialize("11", Gender2.class));
        Assertions.assertEquals(Gender2.MALE, ONode.deserialize("{\"code\":11,\"name\":\"男\"}", Gender2.class));
    }

    public static enum Gender {
        UNKNOWN(10, "未知的性别"),
        MALE(11, "男"),
        FEMALE(12, "女"),
        UNSTATED(19, "未说明的性别");

        private final int code;
        private final String name;

        Gender(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static Gender fromCode(Integer code) {
            for (Gender gender : Gender.values()) {
                if (gender.code == code) {
                    return gender;
                }
            }

            return UNKNOWN;
        }
    }

    public static enum Gender2 {
        UNKNOWN(10, "未知的性别"),
        MALE(11, "男"),
        FEMALE(12, "女"),
        UNSTATED(19, "未说明的性别");

        private final int code;
        private final String name;

        Gender2(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        @ONodeCreator
        public static Gender2 fromCode(Integer code) {
            for (Gender2 gender : Gender2.values()) {
                if (gender.code == code) {
                    return gender;
                }
            }

            return UNKNOWN;
        }
    }

    public static enum Gender3 {
        UNKNOWN(10, "未知的性别"),
        MALE(11, "男"),
        FEMALE(12, "女"),
        UNSTATED(19, "未说明的性别");

        @ONodeAttr
        private final int code;
        private final String name;

        Gender3(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
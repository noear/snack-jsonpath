package features.snack4.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.noear.snack4.Feature;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttr;
import org.noear.snack4.annotation.ONodeCreator;

/**
 *
 * @author noear 2025/11/8 created
 *
 */
public class EnumTest {
    private User user = new User("Pack_xg", 33, Gender.MALE);
    private User2 user2 = new User2("Pack_xg", 33, Gender.MALE);

    @Test
    public void case11() {
        String json = ONode.serialize(user);
        System.out.println(json);

        Assertions.assertEquals("{\"name\":\"Pack_xg\",\"age\":33,\"gender\":1}", json);
    }

    @Test
    public void case12() {
        String json = ONode.serialize(user, Feature.Write_EnumUsingName);
        System.out.println(json);

        Assertions.assertEquals("{\"name\":\"Pack_xg\",\"age\":33,\"gender\":\"MALE\"}", json);
    }

    @Test
    public void case13() {
        String json = ONode.serialize(user, Feature.Write_EnumShapeAsObject);
        System.out.println(json);

        Assertions.assertEquals("{\"name\":\"Pack_xg\",\"age\":33,\"gender\":{\"code\":11,\"name\":\"男\"}}", json);
    }

    @Test
    public void case14() {
        String json = ONode.serialize(user2);
        System.out.println(json);

        Assertions.assertEquals("{\"name\":\"Pack_xg\",\"age\":33,\"gender\":{\"code\":11,\"name\":\"男\"}}", json);
    }

    @Test
    public void case21() {
        String json = ONode.serialize(user);
        System.out.println(json);

        User user1 = ONode.deserialize(json, User.class);

        Assertions.assertEquals(user.name, user1.name);
        Assertions.assertEquals(user.age, user1.age);
    }

    @Test
    public void case22() {
        String json = "{\"name\":\"Pack_xg\",\"age\":33,\"gender\":11}";
        System.out.println(json);

        User user1 = ONode.deserialize(json, User.class);

        Assertions.assertEquals(user.name, user1.name);
        Assertions.assertEquals(user.age, user1.age);
        Assertions.assertEquals(user.gender, user1.gender);
    }

    public static class User {
        private final String name;
        private final int age;
        private final Gender gender;

        public User(String name, int age, Gender gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String name() {
            return name;
        }

        public int age() {
            return age;
        }

        public Gender gender() {
            return gender;
        }
    }

    public static class User2 {
        private final String name;
        private final int age;
        @ONodeAttr(features = Feature.Write_EnumShapeAsObject)
        private final Gender gender;

        public User2(String name, int age, Gender gender) {
            this.name = name;
            this.age = age;
            this.gender = gender;
        }

        public String name() {
            return name;
        }

        public int age() {
            return age;
        }

        public Gender gender() {
            return gender;
        }
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

        @ONodeCreator
        public static Gender fromCode(Integer code) {
            for (Gender gender : Gender.values()) {
                if (gender.code == code) {
                    return gender;
                }
            }

            return UNKNOWN;
        }
    }
}
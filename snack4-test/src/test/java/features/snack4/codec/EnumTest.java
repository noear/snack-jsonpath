package features.snack4.codec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.noear.snack4.Feature;
import org.noear.snack4.ONode;
import org.noear.snack4.annotation.ONodeAttr;

/**
 *
 * @author noear 2025/11/8 created
 *
 */
public class EnumTest {
    private User user = new User("Pack_xg", 33, Gender.MALE);
    private User2 user2 = new User2("Pack_xg", 33, Gender.MALE);

    @Test
    public void case1() {
        String json = ONode.serialize(user);
        System.out.println(json);

        Assertions.assertEquals("{\"name\":\"Pack_xg\",\"age\":33,\"gender\":1}", json);
    }

    @Test
    public void case2() {
        String json = ONode.serialize(user, Feature.Write_EnumUsingName);
        System.out.println(json);

        Assertions.assertEquals("{\"name\":\"Pack_xg\",\"age\":33,\"gender\":\"MALE\"}", json);
    }

    @Test
    public void case3() {
        String json = ONode.serialize(user, Feature.Write_EnumShapeAsObject);
        System.out.println(json);

        Assertions.assertEquals("{\"name\":\"Pack_xg\",\"age\":33,\"gender\":{\"code\":1,\"name\":\"男\"}}", json);
    }

    @Test
    public void case4() {
        String json = ONode.serialize(user2);
        System.out.println(json);

        Assertions.assertEquals("{\"name\":\"Pack_xg\",\"age\":33,\"gender\":{\"code\":1,\"name\":\"男\"}}", json);
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
        UNKNOWN(0, "未知的性别"),
        MALE(1, "男"),
        FEMALE(2, "女"),
        UNSTATED(9, "未说明的性别");

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
    }
}
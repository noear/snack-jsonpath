package features.snack4.codec;

import org.junit.jupiter.api.Test;
import org.noear.snack4.Feature;
import org.noear.snack4.ONode;

/**
 *
 * @author noear 2025/11/2 created
 *
 */
public class StaticTest {
    @Test
    public void case1() {
        String json = ONode.serialize(new DemoDo());
        System.out.println(json);

        assert "{\"name\":\"aaa\"}".equals(json);
    }

    @Test
    public void case2() {
        String json = ONode.serialize(new DemoDo2(), Feature.Read_AllowUseGetter);
        System.out.println(json);

        assert "{\"name\":\"aaa\"}".equals(json);
    }


    public static class DemoDo {
        public static String KEY1 = "key1";
        public static final String KEY2 = "key2";

        public String name = "aaa";
    }

    public static class DemoDo2 {
        public static String KEY1 = "key1";
        public static final String KEY2 = "key2";

        public static String getKEY1() {
            return KEY1;
        }

        public static String getKEY2() {
            return KEY2;
        }

        public String getName() {
            return name;
        }

        public String name = "aaa";
    }
}
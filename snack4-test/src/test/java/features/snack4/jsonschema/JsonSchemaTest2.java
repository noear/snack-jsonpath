package features.snack4.jsonschema;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.noear.eggg.MethodEggg;
import org.noear.snack4.annotation.ONodeAttr;
import org.noear.snack4.codec.util.EgggUtil;
import org.noear.snack4.jsonschema.JsonSchema;
import org.noear.solon.annotation.Param;

import java.util.List;

/**
 *
 * @author noear 2025/11/6 created
 *
 */
public class JsonSchemaTest2 {
    @Test
    public void csae1() throws Exception {
        MethodEggg methodEggg = EgggUtil.getTypeEggg(Tools.class).getClassEggg().findMethodEggg("getUserList");

        String json = JsonSchema.ofType(methodEggg.getGenericReturnType()).toJson();
        System.out.println(json);

        Assertions.assertEquals("{\"type\":\"object\",\"properties\":{\"items\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"number\",\"description\":\"用户ID\"},\"name\":{\"type\":\"string\",\"description\":\"用户名\"}},\"required\":[\"id\",\"name\"]}},\"total\":{\"type\":\"number\"}}}"
        , json);
    }

    public static class Tools {
        @ONodeAttr(description = "获取用户列表")
        public Result<User> getUserList() {
            return new Result<User>();
        }
    }


    public static class Result<T> {
        @ONodeAttr(description = "数据列表", required = true)
        private List<T> items;

        @ONodeAttr(description = "总数", required = true)
        private Integer total;
    }

    // 具体数据类
    public static class User {
        @ONodeAttr(description = "用户ID", required = true)
        private Long id;

        @ONodeAttr(description = "用户名", required = true)
        private String name;
    }
}
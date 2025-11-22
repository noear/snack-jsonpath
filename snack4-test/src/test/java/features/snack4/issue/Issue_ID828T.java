package features.snack4.issue;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.noear.snack4.ONode;

import java.util.List;

/**
 *
 * @author noear 2025/11/23 created
 *
 */
public class Issue_ID828T {

    @Test
    public void case1() {
        PageResult<String> pageResult1 = new PageResult<>();
        pageResult1.setData(new PageData<>());


        String json = ONode.serialize(pageResult1);
        System.out.println(json);

        assert "{\"data\":{\"page\":0,\"size\":0,\"total\":0},\"id\":\"bb9f852a-b9a9-4593-891a-0d7f46d02001\",\"code\":0,\"msg\":\"操作成功\"}".equals(json);
    }

    @Data
    public static class PageResult<T> extends R<PageData<T>> {
        private PageData<T> data;
    }

    @Data
    public static class PageData<T> {
        private long page;
        private long size;
        private long total;
        private List<T> list;
    }

    @Data
    //@Accessors(chain = true)
    public static class R<T> {
        /**
         * 本次请求的id，通常用于日志打印排查
         */
        private String id = "bb9f852a-b9a9-4593-891a-0d7f46d02001";
        /**
         * 响应码
         */
        private int code;
        /**
         * 消息
         */
        private String msg = "操作成功";
        /**
         * 响应数据
         */
        private T data;
    }
}
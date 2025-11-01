package features.snack4.v3_composite;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import demo.snack4._model2.Data;
import demo.snack4._model2.House;
import demo.snack4._model2.Result;
import demo.snack4._model3.MessageListItem;
import demo.snack4._models.*;
import org.junit.jupiter.api.Test;
import org.noear.snack4.ONode;
import org.noear.snack4.codec.TypeRef;

import java.util.*;

/**
 * @author noear 2021/1/2 created
 */
public class GenericTest {
    @Test
    public void test() {
        ComplexModel<Point> model = new ComplexModel<Point>();
        model.setId(1);
        Person person = new Person();
        person.setName("Tom");
        person.setAge(86);
        person.setBirthDay(new Date());
        person.setSensitiveInformation("This should be private over the wire");
        model.setPerson(person);

        List<Point> points = new ArrayList<Point>();
        Point point = new Point();
        point.setX(3);
        point.setY(4);
        points.add(point);

        point = new Point();
        point.setX(100);
        point.setY(100);
        points.add(point);

        //远程方法调用
        model.setPoints(points);

        String json = ONode.ofBean(model).toJson();

        System.out.println(json);

        ComplexModel<Point> model1 = ONode.ofJson(json).toBean(new TypeRef<ComplexModel<Point>>() {
        });

        List<Point> points1 = model1.getPoints();
        for (Point p1 : points1) {
            System.out.println(p1.getX());
        }
    }

    @Test
    public void test2() {
        String json = "{\n" +
                "\t\"code\": \"2000\",\n" +
                "\t\"data\": {\n" +
                "\t\t\"content\": [{\n" +
                "\t\t\t\"sn\": \"P009-F07-H002-B001-R0001\",\n" +
                "\t\t\t\"dver_type\": \"1\",\n" +
                "\t\t\t\"data_status\": \"0\",\n" +
                "\t\t\t\"created_by\": \"lvm\",\n" +
                "\t\t\t\"created_date\": \"2021-12-21 14:44:36\",\n" +
                "\t\t\t\"updated_by\": \"lvm\",\n" +
                "\t\t\t\"updated_date\": \"2021-12-21 14:44:36\"\n" +
                "\t\t}],\n" +
                "\t\t\"pageNum\": 1,\n" +
                "\t\t\"pageSize\": 20,\n" +
                "\t\t\"totalElements\": 2,\n" +
                "\t\t\"pages\": 1\n" +
                "\t}\n" +
                "}";

        String json2 = ONode.ofJson(json).toJson();
        System.out.println(json2);

        Result<House> result = JSON.parseObject(json, new TypeReference<Result<House>>() {
        });
        assert result.getData().getContent().size() == 1;
        assert result.getData().getContent().get(0).getClass() == House.class;


        Result<House> result1 = ONode.ofJson(json).toBean(new Result<House>() {
        }.getClass());
        assert result1.getData().getContent().size() == 1;
        assert result1.getData().getContent().get(0).getClass() == House.class;


        Result<House> result2 = ONode.ofJson(json).toBean(new TypeRef<Result<House>>() {
        }.getType());
        assert result2.getData().getContent().size() == 1;
        assert result2.getData().getContent().get(0).getClass() == House.class;
    }

    @Test
    public void test3() {
        String json = "{\n" +
                "\t\"data\": {\n" +
                "\t\t\"content\": [{\n" +
                "\t\t\t\"sn\": \"P009-F07-H002-B001-R0001\",\n" +
                "\t\t\t\"dver_type\": \"1\",\n" +
                "\t\t\t\"data_status\": \"0\",\n" +
                "\t\t\t\"created_by\": \"lvm\",\n" +
                "\t\t\t\"created_date\": \"2021-12-21 14:44:36\",\n" +
                "\t\t\t\"updated_by\": \"lvm\",\n" +
                "\t\t\t\"updated_date\": \"2021-12-21 14:44:36\"\n" +
                "\t\t}],\n" +
                "\t\t\"pageNum\": 1,\n" +
                "\t\t\"pageSize\": 20,\n" +
                "\t\t\"totalElements\": 2,\n" +
                "\t\t\"pages\": 1\n" +
                "\t}\n" +
                "}";


        Map<String, Data> map = ONode.ofJson(json).toBean(new HashMap<String, Data>() {
        }.getClass());

        assert map.get("data").getClass() == Data.class;
    }

    @Test
    public void test4() {
        String json = "[{\n" +
                "\t\t\t\"sn\": \"P009-F07-H002-B001-R0001\",\n" +
                "\t\t\t\"dver_type\": \"1\",\n" +
                "\t\t\t\"data_status\": \"0\",\n" +
                "\t\t\t\"created_by\": \"lvm\",\n" +
                "\t\t\t\"created_date\": \"2021-12-21 14:44:36\",\n" +
                "\t\t\t\"updated_by\": \"lvm\",\n" +
                "\t\t\t\"updated_date\": \"2021-12-21 14:44:36\"\n" +
                "\t\t}]";


        List<House> ary = ONode.ofJson(json).toBean(new ArrayList<House>() {
        }.getClass());

        assert ary.size() > 0;
        assert ary.get(0).getClass() == House.class;
    }

    @Test
    public void test5() {
        String json = "{\"code\":\"2000\",\"data\":{\"content\":[{\"sn\":\"P0008\",\"dver_type\":\"1\",\"data_status\":\"0\",\"created_by\":\"xieb\",\"created_date\":\"2021-12-16 13:25:16\",\"updated_by\":\"xieb\",\"updated_date\":\"2021-12-16 13:25:16\"},{\"sn\":\"P0009\",\"dver_type\":\"1\",\"data_status\":\"0\",\"created_by\":\"xieb\",\"created_date\":\"2021-12-16 13:41:36\",\"updated_by\":\"xieb\",\"updated_date\":\"2021-12-16 17:09:02\"}],\"obj\":{\"sn\":\"P0008\",\"dver_type\":\"1\",\"data_status\":\"0\",\"created_by\":\"xieb\",\"created_date\":\"2021-12-16 13:25:16\",\"updated_by\":\"xieb\",\"updated_date\":\"2021-12-16 13:25:16\"},\"pageNum\":1,\"pageSize\":20,\"totalElements\":4,\"pages\":1}}";

        Result<House> result1 = ONode.ofJson(json).toBean(new Result<House>() {
        }.getClass());
        assert result1.getData().getContent().size() > 0;
        assert result1.getData().getContent().get(0).getClass() == House.class;

        assert result1.getData().getObj() != null;
        assert result1.getData().getObj().getClass() == House.class;
    }

    @Test
    public void test6() {
//        String s = "[{\"type\":\"receive\",\"message\":{\"msg_id\":30603422,\"id\":1639933436,\"sender\":\"8a6c90e660ed11ecbbec52540025c377\",\"r_status\":2,\"type\":2,\"content\":{\"out_trade_no\":\"202112200103454952555517322\",\"show_amount\":\"5.00\",\"total_amount\":\"5.00\",\"per_month\":\"5.00\",\"month\":1,\"discount\":\"0.00\",\"is_upgrade\":0,\"remark\":\"\",\"ext\":{\"address\":{\"name\":\"\",\"phone\":\"\",\"address\":\"\"}},\"pay_type\":2,\"product_type\":0,\"sku_detail\":[],\"sku_count\":0,\"plan\":{\"plan_id\":\"a4fa470c988411e9a72252540025c377\",\"rank\":0,\"user_id\":\"0e4112d2988411e9adc252540025c377\",\"status\":1,\"name\":\"请吃5包辣条\",\"pic\":\"\",\"desc\":\"\",\"price\":\"5.00\",\"update_time\":1561603416,\"pay_month\":1,\"show_price\":\"5.00\",\"independent\":0,\"permanent\":0,\"can_buy_hide\":1,\"need_address\":0,\"product_type\":0,\"sale_limit_count\":-1,\"need_invite_code\":false},\"time_range\":{\"begin_time\":1639929600,\"end_time\":1642608000}},\"send_time\":1639933436}},{\"type\":\"receive\",\"message\":{\"msg_id\":30603692,\"id\":1639933644,\"sender\":\"8a6c90e660ed11ecbbec52540025c377\",\"r_status\":2,\"type\":1,\"content\":\"landmark的不录了嘛\",\"send_time\":1639933644}},{\"type\":\"send\",\"message\":{\"msg_id\":30644266,\"id\":1640009303,\"sender\":\"0e4112d2988411e9adc252540025c377\",\"r_status\":2,\"type\":1,\"content\":\"直播有广告，过不了审\",\"send_time\":1640009303}},{\"type\":\"receive\",\"message\":{\"msg_id\":30689842,\"id\":1640085379,\"sender\":\"8a6c90e660ed11ecbbec52540025c377\",\"r_status\":2,\"type\":1,\"content\":\"啊好想看\",\"send_time\":1640085379}}]";
        String s = "[{\"type\":\"receive\",\"message\":{\"msg_id\":30603422,\"id\":1639933436,\"sender\":\"8a6c90e660ed11ecbbec52540025c377\",\"r_status\":2,\"type\":2,\"content\":{\"out_trade_no\":\"202112200103454952555517322\",\"show_amount\":\"5.00\",\"total_amount\":\"5.00\",\"per_month\":\"5.00\",\"month\":1,\"discount\":\"0.00\",\"is_upgrade\":0,\"remark\":\"\",\"ext\":{\"address\":{\"name\":\"\",\"phone\":\"\",\"address\":\"\"}},\"pay_type\":2,\"product_type\":0,\"sku_detail\":[],\"sku_count\":0,\"plan\":{\"plan_id\":\"a4fa470c988411e9a72252540025c377\",\"rank\":0,\"user_id\":\"0e4112d2988411e9adc252540025c377\",\"status\":1,\"name\":\"请吃5包辣条\",\"pic\":\"\",\"desc\":\"\",\"price\":\"5.00\",\"update_time\":1561603416,\"pay_month\":1,\"show_price\":\"5.00\",\"independent\":0,\"permanent\":0,\"can_buy_hide\":1,\"need_address\":0,\"product_type\":0,\"sale_limit_count\":-1,\"need_invite_code\":false},\"time_range\":{\"begin_time\":1639929600,\"end_time\":1642608000}},\"send_time\":1639933436}}]";
        System.out.println(s);
        Collection<MessageListItem> messageListItems = ONode.ofJson(s).toBean(new TypeRef<List<MessageListItem>>() {
        });
        for (MessageListItem messageListItem : messageListItems) {
            System.out.println(messageListItem);
            assert messageListItem.getMessage().getContent() instanceof String;
        }
        assert messageListItems.size() > 0;

        messageListItems = ONode.ofJson(s).toBean(TypeRef.listOf(MessageListItem.class));
        for (MessageListItem messageListItem : messageListItems) {
            System.out.println(messageListItem);
            assert messageListItem.getMessage().getContent() instanceof String;
        }
        assert messageListItems.size() > 0;

        messageListItems = ONode.ofJson(s).toBean(TypeRef.setOf(MessageListItem.class));
        for (MessageListItem messageListItem : messageListItems) {
            System.out.println(messageListItem);
            assert messageListItem.getMessage().getContent() instanceof String;
        }
        assert messageListItems.size() > 0;
    }

    @Test
    public void test7() {
        String s = "[[1,2,4],[6,9,1001]]";

        List<List<Integer>> list = ONode.ofJson(s).toBean(new TypeRef<List<List<Integer>>>() {
        }.getType());

        assert list != null;
        assert list.size() == 2;
        assert list.get(0).size() == 3;
    }

    @Test
    public void test8() {
        String s = "{\"list\":[[1,2,4],[6,9,1001]],\"book\":{id:1,\"name\":\"test\"}}";

        GModel model = ONode.ofJson(s).toBean(GModel.class);

        assert model.list != null;
        assert model.list.size() == 2;
        assert model.list.get(0).size() == 3;

        assert model.book != null;
        assert model.book.id == 1;
        assert "test".equals(model.book.bookname);
    }

    @Test
    public void test9() {
        String json = "{page:1,pageSize:3,data:{id:5,name:'noear'}}";
        PageRequest<UserModel> request = ONode.ofJson(json).toBean(new TypeRef<PageRequest<UserModel>>() {
        });

        assert request.getPage() == 1;

        UserModel userModel = request.getData();
        assert userModel.id == 5;
    }

    @Test
    public void test10() {
        String json = "[[1],[3,5],[6,3,1]]";
        List<List<Long>> list = ONode.ofJson(json).toBean(new TypeRef<List<List<Long>>>() {
        });

        assert list != null;
        assert list.get(0).get(0) instanceof Long;
    }

    @Test
    public void test10_2() {
        String json = "{list:[[1],[3,5],[6,3,1]]}";
        List<List<Long>> list = ONode.ofJson(json).get("list").toBean(new TypeRef<List<List<Long>>>() {
        });

        assert list != null;
        assert list.get(0).get(0) instanceof Long;
    }

    @Test
    public void test11() {
        String json = "{a:[1],b:[2,4]}";
        Map<String, List<Long>> map = ONode.ofJson(json).toBean(new TypeRef<Map<String, List<Long>>>() {
        });

        assert map != null;
        assert map.get("a").get(0) instanceof Long;
    }

    @Test
    public <K, V extends List> void test11_2() {
        String json = "{a:[1],b:[2,4]}";
        Map map = ONode.ofJson(json).toBean(new TypeRef<Map<K, V>>() {
        }.where("K", String.class).where("V", TypeRef.listOf(Long.class).getType()));

        assert map != null;
        assert ((List) map.get("a")).get(0) instanceof Long;
    }

    @Test
    public void test12() {
        String json = "{a:1,b:2}";
        Map<String, Long> map = ONode.ofJson(json).toBean(TypeRef.mapOf(String.class, Long.class));

        assert map != null;
        assert map.get("a") instanceof Long;
    }

    @Test
    public <V> void test12_2() {
        String json = "{a:1,b:2}";
        Map map = ONode.ofJson(json).toBean(new TypeRef<Map<String, V>>() {
        }.where("V", Long.class));

        assert map != null;
        assert map.get("a") instanceof Long;
    }
}

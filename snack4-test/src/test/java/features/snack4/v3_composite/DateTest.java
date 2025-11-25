package features.snack4.v3_composite;

import demo.snack4._models.DateModel;
import demo.snack4._models.DateModel2;
import demo.snack4._models.DateModel3;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.noear.snack4.Feature;
import org.noear.snack4.ONode;
import org.noear.snack4.Options;
import org.noear.snack4.codec.util.DateUtil;

import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author noear 2021/6/13 created
 */
public class DateTest {

    @Test
    public void test2() {
        String json = "{date1:'2021-06-13T20:54:51.566Z', date2:'2021-06-13T20:54:51', date3:'2021-06-13 20:54:51', date4:'20210613205451566+0800', date5:'2021-06-13', date6:'2021-06-13T20:54:51.566+08:00', date7:'2021-06-13 20:54:51,566', date8:'2021-06-13 20:54:51.566', date9:'20:54:51'}";
        DateModel dateModel = ONode.ofJson(json).toBean(DateModel.class);

        Assertions.assertEquals(1623617691566L, dateModel.date1.getTime());

        Assertions.assertEquals(1623588891000L, dateModel.date2.getTime());
        Assertions.assertEquals(1623588891000L, dateModel.date3.getTime());

        Assertions.assertEquals(1623588891566L, dateModel.date4.getTime());
        Assertions.assertEquals(1623513600000L, dateModel.date5.getTime());

        Assertions.assertEquals(1623588891566L, dateModel.date6.getTime());
        Assertions.assertEquals(1623588891566L, dateModel.date7.getTime());
        Assertions.assertEquals(1623588891566L, dateModel.date8.getTime());
        Assertions.assertEquals(46491000L, dateModel.date9.getTime());
    }

    @Test
    public void test3() {
        String json = "{date1:'2021-06-13T20:54:51.566Z', date2:'2021-06-13T20:54:51', date3:'2021-06-13 20:54:51', date4:'20210613205451566+0800', date5:'2021-06-13', date6:'2021-06-13T20:54:51.566+08:00', date7:'2021-06-13 20:54:51,566', date8:'2021-06-13 20:54:51.566', date9:'20:54:51', date10:'2021-06-13T20:54:51.566+08:00', date11:'2021-06-13T20:54:51.566+08:00', date12:'20:54:51.566+08:00'}";

        DateModel dateModel0 = ONode.ofJson(json).toBean(DateModel.class);
        DateModel2 dateModel = ONode.ofJson(json).toBean(DateModel2.class);

        String json2 = ONode.ofBean(dateModel).toJson();
        System.out.println(json2);

        DateModel2 dateModel2 = ONode.ofJson(json2).toBean(DateModel2.class);


        assert dateModel.date1.equals(dateModel2.date1);
        assert dateModel.date2.equals(dateModel2.date2);
        assert dateModel.date3.equals(dateModel2.date3);
        assert dateModel.date4.equals(dateModel2.date4);
        assert dateModel.date5.equals(dateModel2.date5);
        assert dateModel.date6.equals(dateModel2.date6);
        assert dateModel.date7.equals(dateModel2.date7);
        assert dateModel.date8.equals(dateModel2.date8);
        assert dateModel.date9.equals(dateModel2.date9);
        assert dateModel.date10.equals(dateModel2.date10);
        assert dateModel.date11.equals(dateModel2.date11);
        assert dateModel.date12.equals(dateModel2.date12);
    }

    @Test
    public void test4() {
        String json = "{date1:1670774400000}";

        DateModel dateModel0 = ONode.ofJson(json).toBean(DateModel.class);
        DateModel2 dateModel = ONode.ofJson(json).toBean(DateModel2.class);

        String json2 = ONode.ofBean(dateModel).toJson();
        System.out.println(json2);

        DateModel2 dateModel2 = ONode.ofJson(json2).toBean(DateModel2.class);


        assert dateModel.date1.equals(dateModel2.date1);
    }

    @Test
    public void test5() {
        DateModel3 dateModel3 = new DateModel3();
        dateModel3.date1 = new Date(1680602276520L);
        dateModel3.date2 = dateModel3.date1;
        dateModel3.date3 = dateModel3.date1;


        String json = ONode.ofBean(dateModel3).toJson();

        System.out.println(json);

        assert json.contains("2023-04-04 17:57:56");
        assert json.contains("2023-04-04 16:57:56");
    }

    @Test
    public void test6() throws Exception {
        DateUtil.parse(LocalDateTime.now().toString());
        DateUtil.parse(LocalDate.now().toString());
        DateUtil.parse(LocalTime.now().toString());

        DateUtil.parse(ZonedDateTime.now().toString());
        DateUtil.parse(OffsetDateTime.now().toString());
        DateUtil.parse(OffsetTime.now().toString());
    }

    @Test
    public void test7() throws Exception {
        Date date = new java.sql.Date(DateUtil.parse(LocalDate.now().toString()).getTime());
        Map map = new HashMap();
        map.put("date", date);
        System.out.println(date);

        DateMapModel model = ONode.ofBean(map).toBean(DateMapModel.class);
        System.out.println(model.date.toString());

        assert date.toString().equals(model.date.toString());
    }

    @Test
    public void test8() {
        String json = "{\n" + "\"patientName\":\"乔宪同\",\n" +
                "\"studyDatetime\":\"2025-07-23 08:12:33.0\",\n" +
                "\"sqDatetime\":\"2025-07-23 08:10:54.093\",\n" +
                "\"reportDatetime\":\"2025-07-23 08:15:31.0\",\n" +
                "\"shDatetime\":\"2025-07-23 08:15:40\"\n" + "}";
        Options options = Options.of(Feature.Write_AllowParameterizedConstructor);
        //添加编码器
        options.addEncoder(Date.class, (ctx, value, target) -> target.setValue((DateUtil.format(value, "yyyy-MM-dd HH:mm:ss"))));
        options.addEncoder(LocalDateTime.class, (ctx, value, target) -> target.setValue((value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))));
        DemoEntity rxPacsOrder = ONode.ofJson(json, options).toBean(DemoEntity.class);
        String jsonText = ONode.ofBean(rxPacsOrder).toJson();
        System.out.println("1 snack添加编码器" + jsonText);

        assert "{\"patientName\":\"乔宪同\",\"studyDatetime\":1753229553000,\"sqDatetime\":1753229454093,\"reportDatetime\":1753229731000,\"shDatetime\":1753229740000}".equals(jsonText);
    }

    public static class DateMapModel {
        public LocalDate date;
    }

    @Data
    public class DemoEntity implements Serializable {
        private String patientName;
        private Date studyDatetime;
        private Date sqDatetime;
        private LocalDateTime reportDatetime;
        private LocalDateTime shDatetime;
    }
}
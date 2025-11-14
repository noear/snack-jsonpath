package features.snack4.jsonschema.victools;

import org.junit.jupiter.api.Test;
import org.noear.snack4.jsonschema.JsonSchema;

/**
 *
 * @author noear 2025/11/14 created
 *
 */
public class CompatibleTest1 extends BaseCompatibleTest {
    @Test
    public void case1() {
        String json = schemaGenerator.generateSchema(TestSampleEntity.class).toString();
        System.out.println(json);

        String json2 = JsonSchema.ofType(TestSampleEntity.class).toJson();
        System.out.println(json2);
    }
}
package features.snack4.jsonschema.victools;

import org.junit.jupiter.api.Test;
import org.noear.snack4.jsonschema.generate.JsonSchemaGenerator;

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

        String json2 = new JsonSchemaGenerator(TestSampleEntity.class)
                .withEnableDefinitions(true)
                .generate()
                .toJson();

        System.out.println(json2);
    }
}
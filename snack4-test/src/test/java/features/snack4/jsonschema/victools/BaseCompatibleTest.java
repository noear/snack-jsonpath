package features.snack4.jsonschema.victools;

import com.github.victools.jsonschema.generator.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.noear.snack4.annotation.ONodeAttr;

import java.util.Optional;

/**
 *
 * @author noear 2025/11/14 created
 */
public class BaseCompatibleTest {
    protected final SchemaGenerator schemaGenerator;

    public BaseCompatibleTest() {
        // 选择所需的JSON Schema版本和配置预设
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(
                SchemaVersion.DRAFT_7,
                OptionPreset.PLAIN_JSON);

        configBuilder
                .without(Option.DEFINITIONS_FOR_ALL_OBJECTS)
                .without(Option.DEFINITIONS_FOR_MEMBER_SUPERTYPES)
                .without(Option.DEFINITION_FOR_MAIN_SCHEMA)
                .with(Option.FLATTENED_SUPPLIERS)
                .with(Option.FLATTENED_OPTIONALS)
                .with(Option.FLATTENED_ENUMS);

        // --- 1. 定制字段名称 (Name Override) 和 忽略 (Ignore) ---
        configBuilder.forFields()
                // 检查 @JSONField 注解，如果指定了 name，则使用它作为 JSON 属性名
                .withPropertyNameOverrideResolver(field -> {
                    ONodeAttr jsonField = field.getAnnotationConsideringFieldAndGetter(ONodeAttr.class);
                    if (jsonField != null && !jsonField.name().isEmpty()) {
                        return jsonField.name();
                    }
                    return null; // 返回 null 表示使用默认名称
                })
                // 检查 @JSONField 注解，如果设置了 serialize = false，则忽略该字段
                .withIgnoreCheck(field -> {
                    ONodeAttr jsonField = field.getAnnotationConsideringFieldAndGetter(ONodeAttr.class);
                    return jsonField != null && !jsonField.encode();
                });

        // --- 2. 定制描述 (Description) ---
        configBuilder.forFields()
                // 检查 @JSONField 注解，如果指定了 description，则将其作为 Schema 的 description
                .withDescriptionResolver(field -> {
                    return Optional.ofNullable(field.getAnnotationConsideringFieldAndGetter(ONodeAttr.class))
                            .map(ONodeAttr::description)
                            .filter(desc -> !desc.isEmpty())
                            .orElse(null);
                });

        // --- 3. (可选) 定制额外属性 (例如 @JSONField(ordinal=...)) ---
        configBuilder.forFields()
                // 使用 withInstanceAttributeOverride 来添加非标准属性，例如 'title' 或其他定制关键字
                .withInstanceAttributeOverride(this::handleFastjson2Attributes);

        schemaGenerator = new SchemaGenerator(configBuilder.build());
    }

    private void handleFastjson2Attributes(ObjectNode jsonSchemaAttributesNode, FieldScope field, SchemaGenerationContext context) {
        ONodeAttr jsonField = field.getAnnotationConsideringFieldAndGetter(ONodeAttr.class);
        if (jsonField != null) {
            // Fastjson2 没有内置的 title，但我们可以想象有一个自定义逻辑
            // 假设我们想把 Fastjson2 的 name 映射为 JSON Schema 的 title
            if (!jsonField.name().isEmpty() && !jsonSchemaAttributesNode.has("title")) {
                jsonSchemaAttributesNode.put("title", jsonField.title());
            }
            // 假设我们有一个虚拟的 @JSONField(format="email")，并想将其转为 JSON Schema 的 format
            // if (jsonField.format().equals("email")) {
            //      jsonSchemaAttributesNode.put("format", "email");
            // }
        }
    }
}
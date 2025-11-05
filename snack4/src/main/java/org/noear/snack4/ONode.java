/*
 * Copyright 2005-2025 noear.org and authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.noear.snack4;

import org.noear.snack4.codec.BeanDecoder;
import org.noear.snack4.codec.BeanEncoder;
import org.noear.snack4.codec.TypeRef;
import org.noear.snack4.codec.util.DateUtil;
import org.noear.snack4.json.JsonReader;
import org.noear.snack4.json.JsonWriter;
import org.noear.snack4.jsonpath.JsonPathProvider;
import org.noear.snack4.jsonpath.PathSource;
import org.noear.snack4.util.Asserts;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

/**
 * JSON 节点抽象
 *
 * @author noear 2019/2/12 created
 * @since 4.0
 */
public final class ONode {
    private static JsonPathProvider jsonPathProvider = () -> "Requires 'snack4-jsonpath' dependency";

    static {
        ServiceLoader<JsonPathProvider> jsonPathSL = ServiceLoader.load(JsonPathProvider.class);
        for (JsonPathProvider provider : jsonPathSL) {
            jsonPathProvider = provider;
        }
    }

    private Object value;
    private transient DataType type;
    private transient Options options;

    public transient PathSource source;

    public ONode() {
        this(Options.DEF_OPTIONS);
    }

    public ONode(Object value) {
        this(Options.DEF_OPTIONS, value);
    }

    public ONode(Options opts) {
        this.type = DataType.Undefined;
        this.options = opts == null ? Options.DEF_OPTIONS : opts;
    }

    public ONode(Options opts, Object value) {
        this.value = value;
        this.type = DataType.resolveType(value);
        this.options = opts == null ? Options.DEF_OPTIONS : opts;
    }

    public DataType type() {
        return type;
    }

    public Options options() {
        return options;
    }

    public ONode options(Options opts) {
        options = opts;
        return this;
    }

    // Getters and Setters
    public boolean isUndefined() {
        return type == DataType.Undefined;
    }

    public boolean isNull() {
        return type == DataType.Null || isUndefined();
    }

    public boolean isEmpty() {
        return isNull() ||
                (isArray() && getArray().isEmpty()) ||
                (isObject() && getObject().isEmpty()) ||
                (isString() && getString().isEmpty());
    }

    public boolean isBoolean() {
        return type == DataType.Boolean;
    }

    public boolean isNumber() {
        return type == DataType.Number;
    }

    public boolean isString() {
        return type == DataType.String;
    }

    public boolean isDate() {
        return type == DataType.Date;
    }

    public boolean isArray() {
        return type == DataType.Array;
    }

    public boolean isObject() {
        return type == DataType.Object;
    }

    public boolean isValue() {
        return DataType.isValue(type);
    }

    public Object getValue() {
        return value;
    }

    public <T> T getValueAs() {
        return (T) value;
    }


    public List<ONode> getArray() {
        asArray();

        return (List<ONode>) value;
    }

    public List<ONode> getArrayUnsafe() {
        return (List<ONode>) value;
    }

    public Map<String, ONode> getObject() {
        asObject();

        return (Map<String, ONode>) value;
    }

    public Map<String, ONode> getObjectUnsafe() {
        return (Map<String, ONode>) value;
    }

    public ONode asObject() {
        if (value == null) {
            value = new LinkedHashMap<String, ONode>();
            type = DataType.Object;
        }

        return this;
    }

    public ONode asArray() {
        if (value == null) {
            value = new ArrayList<ONode>();
            type = DataType.Array;
        }

        return this;
    }


    public Boolean getBoolean() {
        return getBoolean(false);
    }

    public Boolean getBoolean(Boolean def) {
        if (isBoolean()) {
            return (Boolean) value;
        } else if (isNumber()) {
            return getNumber().longValue() > 0;
        } else if (isString()) {
            String str = getValueAs();
            if (str.length() > 0) {
                return Boolean.parseBoolean((String) value);
            } else {
                return false;
            }
        } else {
            return def;
        }
    }

    public Number getNumber() {
        return (Number) value;
    }

    public Number getNumber(Number def) {
        if (value == null) {
            return def;
        } else {
            return (Number) value;
        }
    }

    public String getString() {
        if (isString()) {
            return (String) value;
        } else if (isNumber()) {
            if (value instanceof BigDecimal) {
                if (options.hasFeature(Feature.Write_BigDecimalAsPlain)) {
                    return ((BigDecimal) value).toPlainString();
                }
            }

            return value.toString();
        } else if (isObject() || isArray()) {
            return toJson();
        } else if (isNull()) {
            if (options.hasFeature(Feature.Write_NullStringAsEmpty)) {
                return "";
            } else {
                return null;
            }
        } else {
            return String.valueOf(value);
        }
    }

    public Date getDate() {
        if (isDate()) {
            return (Date) value;
        } else if (isNumber()) {
            return new Date(getNumber().longValue());
        } else if (isString()) {
            return DateUtil.parseTry(getString());
        } else {
            return null;
        }
    }

    public Short getShort() {
        return getShort((short) 0);
    }

    public Short getShort(Short def) {
        if (isNumber()) {
            return getNumber().shortValue();
        } else if (isEmpty()) {
            return def;
        } else if (isString()) {
            return Short.parseShort(getString());
        } else {
            return def;
        }
    }

    public Integer getInt() {
        return getInt(0);
    }

    public Integer getInt(Integer def) {
        if (isNumber()) {
            return getNumber().intValue();
        } else if (isEmpty()) {
            return def;
        } else if (isString()) {
            return Integer.parseInt(getString());
        } else if (isBoolean()) {
            return getBoolean() ? 1 : 0;
        } else {
            return def;
        }
    }

    public Long getLong() {
        return getLong(0L);
    }

    public Long getLong(Long def) {
        if (isNumber()) {
            return getNumber().longValue();
        } else if (isEmpty()) {
            return def;
        } else if (isString()) {
            return Long.parseLong(getString());
        } else if (isBoolean()) {
            return getBoolean() ? 1L : 0L;
        } else if (isDate()) {
            return getDate().getTime();
        } else {
            return def;
        }
    }

    public Float getFloat() {
        return getFloat(0F);
    }

    public Float getFloat(Float def) {
        if (isNumber()) {
            return getNumber().floatValue();
        } else if (isEmpty()) {
            return def;
        } else if (isString()) {
            return Float.parseFloat(getString());
        } else if (isBoolean()) {
            return getBoolean() ? 1F : 0F;
        } else {
            return def;
        }
    }

    public Double getDouble() {
        return getDouble(0D);
    }

    public Double getDouble(Double def) {
        if (isNumber()) {
            return getNumber().doubleValue();
        } else if (isEmpty()) {
            return def;
        } else if (isString()) {
            return Double.parseDouble(getString());
        } else if (isBoolean()) {
            return getBoolean() ? 1D : 0D;
        } else {
            return def;
        }
    }

    public ONode get(String key) {
        ONode tmp = getObject().get(key);
        if (tmp == null) {
            return new ONode(options);
        } else {
            return tmp;
        }
    }

    public ONode getOrNew(String key) {
        return getObject().computeIfAbsent(key, k -> new ONode(options, null));
    }

    public ONode getOrNull(String key) {
        if (isObject()) {
            return getObjectUnsafe().get(key);
        } else {
            return null;
        }
    }

    public ONode remove(String key) {
        if (isObject()) {
            return getObjectUnsafe().remove(key);
        } else {
            return null;
        }
    }

    public ONode rename(String oldName, String newName) {
        ONode tmp = remove(oldName);
        if (tmp != null) {
            getObjectUnsafe().put(newName, tmp);
        }

        return this;
    }

    public ONode setValue(Object value) {
        this.value = value;
        this.type = DataType.resolveValueType(value);
        return this;
    }

    public ONode fill(Object source) {
        ONode oNode = ONode.ofBean(source, options);

        this.value = oNode.value;
        this.type = oNode.type;
        return this;
    }

    public ONode fillJson(String json) {
        return this.fill(ONode.ofJson(json, options));
    }

    public ONode setAll(Map<?, ?> map) {
        if (map == null) {
            return this;
        }

        for (Map.Entry entry : map.entrySet()) {
            set(String.valueOf(entry.getKey()), entry.getValue());
        }

        return this;
    }

    public ONode set(String key, Object value) {
        ONode oNode;
        if (value == null) {
            oNode = new ONode(options, null);
        } else if (value instanceof ONode) {
            oNode = (ONode) value;
        } else if (value instanceof Collection) {
            oNode = BeanEncoder.encode(value);
        } else if (value instanceof Map) {
            oNode = BeanEncoder.encode(value);
        } else {
            if (value.getClass().isArray()) {
                oNode = new ONode(options).addAll(Arrays.asList((Object[]) value));
            } else {
                oNode = new ONode(options, value);
            }
        }

        getObject().put(key, oNode);
        return this;
    }

    public ONode get(int index) {
        List<ONode> self = getArray();
        int size = self.size();

        if (index < 0) {
            index += size;
        }

        if (index >= 0 && size > index) {
            return self.get(index);
        }

        return new ONode(options);
    }

    public ONode getOrNew(int index) {
        return getOrNew(index, null);
    }

    public ONode getOrNew(int index, Consumer<ONode> thenApply) {
        List<ONode> self = getArray();
        int size = self.size();

        if (index < 0) {
            index += size;
        }

        if (size > index) {
            return self.get(index);
        } else {
            ONode last = null;
            int count = index + 1 - size;

            for (int i = 0; i < count; i++) {
                last = new ONode(options, null);
                if (thenApply != null) {
                    thenApply.accept(last);
                }
                self.add(last);
            }

            return last;
        }
    }

    public ONode getOrNull(int index) {
        if (isArray()) {
            int size = getArrayUnsafe().size();

            if (index < 0) {
                index += size;
            }

            if (index >= 0 && size > index) {
                return getArrayUnsafe().get(index);
            }
        }

        return null;
    }

    public ONode remove(int index) {
        List<ONode> self = getArray();
        int size = self.size();

        if (index < 0) {
            index += size;
        }

        return self.remove(index);
    }

    public ONode add(Object value) {
        ONode oNode;
        if (value instanceof ONode) {
            oNode = (ONode) value;
        } else if (value instanceof Collection) {
            oNode = BeanEncoder.encode(value);
        } else if (value instanceof Map) {
            oNode = BeanEncoder.encode(value);
        } else {
            oNode = new ONode(options, value);
        }

        getArray().add(oNode);
        return this;
    }

    public ONode addAll(Collection collection) {
        if (collection == null) {
            return this;
        }

        for (Object o : collection) {
            add(o);
        }
        return this;
    }

    public ONode addNew() {
        asArray();

        ONode oNode = new ONode(options, null);
        getArrayUnsafe().add(oNode);
        return oNode;
    }

    public ONode then(Consumer<ONode> builder) {
        builder.accept(this);
        return this;
    }

    public int size() {
        //内部集合大小
        if (isArray()) return getArray().size();
        if (isObject()) return getObject().size();
        return 0;
    }

    public void clear() {
        if (isObject()) {
            ((Map<?, ?>) value).clear();
        } else if (isArray()) {
            ((List<?>) value).clear();
        } else {
            this.value = null;
            this.type = DataType.Null;
        }
    }

    public boolean hasKey(String key) {
        return isObject() && getObject().containsKey(key);
    }

    public boolean hasValue(Object value) {
        if (isObject()) {
            for (ONode n : getObject().values()) {
                if (n.equals(value)) {
                    return true;
                }
            }
            return false;
        } else if (isArray()) {
            for (ONode n : getArray()) {
                if (n.equals(value)) {
                    return true;
                }
            }
            return false;
        } else if (isValue()) {
            return getValue().equals(value);
        } else {
            return false;
        }
    }

    /// /////////////

    /**
     * 根据 jsonpath 查询
     */
    public ONode select(String jsonpath) {
        return jsonPathProvider.select(this, jsonpath);
    }

    public boolean exists(String jsonpath) {
        return false == select(jsonpath).isUndefined();
    }

    /**
     * 根据 jsonpath 删除
     */
    public void delete(String jsonpath) {
        jsonPathProvider.delete(this, jsonpath);
    }

    /**
     * 根据 jsonpath 生成
     */
    public ONode create(String jsonpath) {
        return jsonPathProvider.create(this, jsonpath);
    }


    /// /////////////

    public static ONode ofBean(Object bean, Feature... features) {
        if (Asserts.isEmpty(features)) {
            return BeanEncoder.encode(bean, Options.DEF_OPTIONS);
        } else {
            return BeanEncoder.encode(bean, Options.of(features));
        }
    }

    public static ONode ofBean(Object bean, Options opts) {
        return BeanEncoder.encode(bean, opts);
    }

    public static ONode ofJson(String json, Feature... features) {
        if (Asserts.isEmpty(features)) {
            return ofJson(json, Options.DEF_OPTIONS);
        } else {
            return ofJson(json, Options.of(features));
        }
    }

    public static ONode ofJson(String json, Options opts) {
        try {
            return JsonReader.read(json, opts);
        } catch (SnackException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new SnackException(ex);
        }
    }

    public static ONode ofJson(Reader reader, Options opts) {
        try {
            return JsonReader.read(reader, opts);
        } catch (SnackException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new SnackException(ex);
        }
    }

    /// ///////////

    public <T> T bindTo(T target) {
        return BeanDecoder.decode(this, target.getClass(), target, options);
    }

    public <T> T toBean(Type type) {
        return BeanDecoder.decode(this, type, null, options);
    }


    public <T> T toBean(TypeRef<T> typeRef) {
        return toBean(typeRef.getType());
    }

    public <T> T toBean() {
        return toBean(Object.class);
    }

    /**
     * @deprecated 4.0
     */
    @Deprecated
    public <T> T toData() {
        return toBean();
    }

    public String toJson() {
        try {
            return JsonWriter.write(this, options);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    public void toJson(Writer writer) {
        try {
            JsonWriter.write(this, options, writer);
        } catch (RuntimeException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return isNull();
        }

        if (isArray()) {
            if (o instanceof ONode) {
                ONode o1 = (ONode) o;
                return o1.isArray() && Objects.equals(getArray(), o1.getArray());
            } else {
                return Objects.equals(getArray(), o);
            }
        }

        if (isObject()) {
            if (o instanceof ONode) {
                ONode o1 = (ONode) o;
                return o1.isObject() && Objects.equals(getObject(), ((ONode) o).getObject());
            } else {
                return Objects.equals(getObject(), o);
            }
        }

        if (isValue()) {
            if (o instanceof ONode) {
                ONode o1 = (ONode) o;
                return o1.isValue() && Objects.equals(getValue(), ((ONode) o).getValue());
            } else {
                return Objects.equals(getValue(), o);
            }
        }

        //最后是null type
        if (o instanceof ONode) {
            return ((ONode) o).isNull(); //都是 null
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        if (isNull()) {
            return 0;
        } else {
            return value.hashCode();
        }
    }

    @Override
    public String toString() {
        return toJson();
    }


    /// ///////////

    public ONode usePaths() {
        PathSource.resolvePath(this);
        return this;
    }

    public ONode parent() {
        if (source == null) {
            return null;
        } else {
            return source.parent;
        }
    }

    public ONode parents(int depth) {
        if (source == null) {
            return null;
        } else {
            ONode tmp = this;
            while (depth > 0) {
                if (tmp == null) {
                    break;
                } else {
                    tmp = tmp.parent();
                }

                depth--;
            }

            return tmp;
        }
    }

    public List<String> pathList() {
        List<String> paths = new ArrayList<>();
        String tmp = this.path();

        if (tmp != null) {
            paths.add(tmp);
        }

        if (isArray()) {
            for (ONode node : getArray()) {
                tmp = node.path();
                if (tmp != null) {
                    paths.add(tmp);
                }
            }
        }

        return paths;
    }

    private transient String path;

    public String path() {
        if (path == null) {
            if (source == null) {
                path = null;
            } else {
                String parentPath = source.parent.path();

                if (source.key == null) {
                    if (Asserts.isEmpty(parentPath)) {
                        path = "$[" + source.index + "]";
                    } else {
                        path = parentPath + "[" + source.index + "]";
                    }
                } else {
                    if (Asserts.isEmpty(parentPath)) {
                        path = "$['" + source.key + "']";
                    } else {
                        path = parentPath + "['" + source.key + "']";
                    }
                }
            }
        }

        return path;
    }

    ///
    ///
    ///
    public static boolean hasNestedJson(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        int start = 0;
        int end = str.length() - 1;

        // 跳过开头空白
        while (start <= end && Character.isWhitespace(str.charAt(start))) {
            start++;
        }

        // 跳过结尾空白
        while (end >= start && Character.isWhitespace(str.charAt(end))) {
            end--;
        }

        // 检查有效长度
        if (start >= end) {
            return false;
        }

        char first = str.charAt(start);
        char last = str.charAt(end);

        return (first == '{' && last == '}') || (first == '[' && last == ']');
    }

    public static String serialize(Object object, Feature... features) {
        if (Asserts.isEmpty(features)) {
            return serialize(object, Options.DEF_OPTIONS);
        } else {
            return serialize(object, Options.of(features));
        }
    }

    public static String serialize(Object object, Options opts) {
        return ONode.ofBean(object, opts).toJson();
    }

    public static <T> T deserialize(String json, Feature... features) {
        if (Asserts.isEmpty(features)) {
            return deserialize(json, Object.class, Options.DEF_OPTIONS);
        } else {
            return deserialize(json, Object.class, Options.of(features));
        }
    }

    public static <T> T deserialize(String json, Options opts) {
        return deserialize(json, Object.class, opts);
    }

    public static <T> T deserialize(String json, Type type, Feature... features) {
        if (Asserts.isEmpty(features)) {
            return deserialize(json, type, Options.DEF_OPTIONS);
        } else {
            return deserialize(json, type, Options.of(features));
        }
    }

    public static <T> T deserialize(String json, Type type, Options opts) {
        return ONode.ofJson(json, opts).toBean(type);
    }

    public static <T> T deserialize(String json, TypeRef<T> type, Feature... features) {
        if (Asserts.isEmpty(features)) {
            return deserialize(json, type, Options.DEF_OPTIONS);
        } else {
            return deserialize(json, type, Options.of(features));
        }
    }

    public static <T> T deserialize(String json, TypeRef<T> type, Options opts) {
        return ONode.ofJson(json, opts).toBean(type);
    }
}
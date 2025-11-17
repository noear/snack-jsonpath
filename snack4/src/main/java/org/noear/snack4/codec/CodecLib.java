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
package org.noear.snack4.codec;

import org.noear.snack4.codec.decode.*;
import org.noear.snack4.codec.encode.*;
import org.noear.snack4.codec.create.*;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

/**
 * 编解码库
 *
 * @author noear 2025/10/3 created
 * @since 4.0
 */
public class CodecLib {
    private static CodecLib DEFAULT = new CodecLib(null).loadDefault();

    private final Map<Class<?>, ObjectCreator<?>> creators = new HashMap<>();
    private final List<ObjectPatternCreator<?>> patternCreators = new ArrayList<>();

    private final Map<Class<?>, ObjectDecoder<?>> decoders = new HashMap<>();
    private final List<ObjectPatternDecoder<?>> patternDecoders = new ArrayList<>();

    private final Map<Class<?>, ObjectEncoder<?>> encoders = new HashMap<>();
    private final List<ObjectPatternEncoder<?>> patternEncoders = new ArrayList<>();

    private final CodecLib parent;

    private CodecLib(CodecLib parent) {
        this.parent = parent;
    }

    public static CodecLib newInstance() {
        return new CodecLib(DEFAULT);
    }

    /**
     * 添加创建器
     */
    public <T> void addCreator(Class<T> type, ObjectCreator<T> creator) {
        creators.put(type, creator);
    }

    /**
     * 添加创建器
     */
    public void addCreator(ObjectPatternCreator creator) {
        patternCreators.add(creator);
    }

    /**
     * 添加解码器
     */
    public void addDecoder(ObjectPatternDecoder decoder) {
        patternDecoders.add(decoder);
    }

    /**
     * 添加解码器
     */
    public <T> void addDecoder(Class<T> type, ObjectDecoder<T> decoder) {
        if (decoder instanceof ObjectPatternDecoder<?>) {
            patternDecoders.add((ObjectPatternDecoder<?>) decoder);
        }

        decoders.put(type, decoder);
    }

    /**
     * 添加编码器
     */
    public void addEncoder(ObjectPatternEncoder encoder) {
        patternEncoders.add(encoder);
    }

    /**
     * 添加编码器
     */
    public <T> void addEncoder(Class<T> type, ObjectEncoder<T> encoder) {
        if (encoder instanceof ObjectPatternEncoder) {
            patternEncoders.add((ObjectPatternEncoder<T>) encoder);
        }

        encoders.put(type, encoder);
    }

    public ObjectDecoder getDecoder(Class<?> clazz) {
        ObjectDecoder tmp = decoders.get(clazz);

        if (tmp == null) {
            for (ObjectPatternDecoder decoder1 : patternDecoders) {
                if (decoder1.canDecode(clazz)) {
                    return decoder1;
                }
            }

            if (parent != null) {
                return parent.getDecoder(clazz);
            }
        }

        return tmp;
    }

    public ObjectCreator getCreator(Class<?> clazz) {
        ObjectCreator tmp = creators.get(clazz);

        if (tmp == null) {
            for (ObjectPatternCreator<?> creator1 : patternCreators) {
                if (creator1.calCreate(clazz)) {
                    return creator1;
                }
            }

            if (parent != null) {
                return parent.getCreator(clazz);
            }
        }

        return tmp;
    }

    public ObjectEncoder getEncoder(Object value) {
        ObjectEncoder encoder = encoders.get(value.getClass());

        if (encoder == null) {
            for (ObjectPatternEncoder encoder1 : patternEncoders) {
                if (encoder1.canEncode(value)) {
                    return encoder1;
                }
            }

            if (parent != null) {
                return parent.getEncoder(value);
            }
        }

        return encoder;
    }

    /// //////////////////////

    private void loadDefaultCreators() {
        addCreator(new _ThrowablePatternCreator());

        addCreator(HashMap.class, ((opts, node, clazz) -> new HashMap()));
        addCreator(LinkedHashMap.class, ((opts, node, clazz) -> new LinkedHashMap()));
        addCreator(Map.class, ((opts, node, clazz) -> new LinkedHashMap()));

        addCreator(ArrayList.class, ((opts, node, clazz) -> new ArrayList()));
        addCreator(Collection.class, ((opts, node, clazz) -> new ArrayList()));
        addCreator(List.class, ((opts, node, clazz) -> new ArrayList()));

        addCreator(Set.class, ((opts, node, clazz) -> new HashSet()));
        addCreator(HashSet.class, ((opts, node, clazz) -> new HashSet()));
    }

    private void loadDefaultDecoders() {
        addDecoder(new _ArrayPatternDecoder());
        addDecoder(new _EnumPatternDecoder());
        addDecoder(new _PropertiesPatternDecoder());

        addDecoder(Charset.class, new _CharsetPatternDecoder());
        addDecoder(TimeZone.class, new _TimeZonePatternDecoder());
        addDecoder(Currency.class, new _CurrencytPatternDecoder());

        addDecoder(StackTraceElement.class, new StackTraceElementDecoder());
        addDecoder(InetSocketAddress.class, new InetSocketAddressDecoder());
        addDecoder(SimpleDateFormat.class, new SimpleDateFormatDecoder());
        addDecoder(File.class, new FileDecoder());
        addDecoder(Class.class, new ClassDecoder());
        addDecoder(Duration.class, new DurationDecoder());

        addDecoder(URL.class, new URLDecoder());

        addDecoder(Date.class, new DateDecoder());

        addDecoder(LongAdder.class, new LongAdderDecoder());
        addDecoder(DoubleAdder.class, new DoubleAdderDecoder());

        addDecoder(LocalTime.class, new LocalTimeDecoder());
        addDecoder(LocalDateTime.class, new LocalDateTimeDecoder());
        addDecoder(LocalDate.class, new LocalDateDecoder());

        addDecoder(OffsetDateTime.class, new OffsetDateTimeDecoder());
        addDecoder(OffsetTime.class, new OffsetTimeDecoder());

        addDecoder(ZonedDateTime.class, new ZonedDateTimeDecoder());

        addDecoder(BigDecimal.class, new BigDecimalDecoder());
        addDecoder(BigInteger.class, new BigIntegerDecoder());

        addDecoder(URI.class, (c, o) -> URI.create(o.getString()));
        addDecoder(UUID.class, (c, o) -> UUID.fromString(o.getString()));

        addDecoder(java.sql.Date.class, (c, o) -> new java.sql.Date(o.getLong()));
        addDecoder(java.sql.Time.class, (c, o) -> new java.sql.Time(o.getLong()));
        addDecoder(java.sql.Timestamp.class, (c, o) -> new java.sql.Timestamp(o.getLong()));

        addDecoder(String.class, (c, o) -> o.getString());

        addDecoder(Boolean.class, (c, o) -> o.getBoolean(null));
        addDecoder(Boolean.TYPE, (c, o) -> o.getBoolean(false));

        addDecoder(Double.class, (c, o) -> o.getDouble(null));
        addDecoder(Double.TYPE, (c, o) -> o.getDouble(0D));

        addDecoder(Float.class, (c, o) -> o.getFloat(null));
        addDecoder(Float.TYPE, (c, o) -> o.getFloat(0F));

        addDecoder(Long.class, (c, o) -> o.getLong(null));
        addDecoder(Long.TYPE, (c, o) -> o.getLong(0L));

        addDecoder(Integer.class, (c, o) -> o.getInt(null));
        addDecoder(Integer.TYPE, (c, o) -> o.getInt(0));

        addDecoder(Short.class, (c, o) -> o.getShort(null));
        addDecoder(Short.TYPE, (c, o) -> o.getShort((short) 0));

        addDecoder(Byte.class, (c, o) -> o.getNumber().byteValue());
        addDecoder(Byte.TYPE, (c, o) -> o.getNumber().byteValue());
    }


    private void loadDefaultEncoders() {
        addEncoder(new _EnumPatternEncoder());
        addEncoder(new _PropertiesPatternEncoder());

        addEncoder(Charset.class, new _CharsetPatternEncoder());
        addEncoder(Date.class, new _DatePatternEncoder());
        addEncoder(Number.class, new _NumberPatternEncoder());
        addEncoder(Calendar.class, new _CalendarPatternEncoder());
        addEncoder(Clob.class, new _ClobPatternEncoder());
        addEncoder(TimeZone.class, new _TimeZonePatternEncoder());
        addEncoder(Currency.class, new _CurrencyPatternEncoder());

        addEncoder(KeyValueList.class, new KeyValueListEncoder());
        addEncoder(StackTraceElement.class, new StackTraceElementEncoder());
        addEncoder(InetSocketAddress.class, new InetSocketAddressEncoder());
        addEncoder(SimpleDateFormat.class, new SimpleDateFormatEncoder());

        addEncoder(String.class, new StringEncoder());

        addEncoder(LocalDateTime.class, new LocalDateTimeEncoder());
        addEncoder(LocalDate.class, new LocalDateEncoder());
        addEncoder(LocalTime.class, new LocalTimeEncoder());

        addEncoder(OffsetDateTime.class, new OffsetDateTimeEncoder());
        addEncoder(OffsetTime.class, new OffsetTimeEncoder());

        addEncoder(ZonedDateTime.class, new ZonedDateTimeEncoder());

        addEncoder(Boolean.class, new BooleanEncoder());
        addEncoder(Boolean.TYPE, new BooleanEncoder());

        addEncoder(Duration.class, (c, v, t) -> t.setValue(v.toString()));
        addEncoder(File.class, (c, v, t) -> t.setValue(v.getPath()));

        addEncoder(LongAdder.class, (c, v, t) -> t.setValue(v.longValue()));
        addEncoder(DoubleAdder.class, (c, v, t) -> t.setValue(v.doubleValue()));

        addEncoder(URL.class, (c, v, t) -> t.setValue(v.toString()));
        addEncoder(URI.class, (c, v, t) -> t.setValue(v.toString()));
        addEncoder(Class.class, (c, v, t) -> t.setValue(v.getName()));

        addEncoder(UUID.class, (ctx, value, target) -> target.setValue(value.toString()));
    }

    private CodecLib loadDefault() {
        loadDefaultCreators();
        loadDefaultDecoders();
        loadDefaultEncoders();
        return this;
    }
}
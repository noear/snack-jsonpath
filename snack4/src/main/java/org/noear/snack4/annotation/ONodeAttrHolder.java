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
package org.noear.snack4.annotation;

import org.noear.snack4.Feature;
import org.noear.snack4.codec.ObjectDecoder;
import org.noear.snack4.codec.ObjectEncoder;
import org.noear.snack4.codec.util.ClassUtil;
import org.noear.snack4.codec.util.DateUtil;
import org.noear.snack4.util.Asserts;

import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

/**
 * 节点属性元
 *
 * @author noear 2025/10/8 created
 * @since 4.0
 */
public class ONodeAttrHolder {
    private String alias;
    private String title;
    private String description;

    private String format;
    private ZoneId zoneId;
    private TimeZone timezone;

    private boolean flat;
    private boolean required;
    private final boolean empty;

    private boolean encode = true;
    private boolean decode = true;
    private ObjectEncoder encoder;
    private ObjectDecoder decoder;
    private long featuresValue;

    public ONodeAttrHolder(String alias, String title, String description, boolean required) {
        this.alias = alias;
        this.title = title;
        this.description = description;
        this.required = required;
        this.empty = true;
    }

    public ONodeAttrHolder(ONodeAttr attrAnno, String realName) {
        if (attrAnno != null) {
            alias = attrAnno.name();
            title = attrAnno.title();
            description = attrAnno.description();

            format = attrAnno.format();
            if (Asserts.isNotEmpty(attrAnno.timezone())) {
                zoneId = ZoneId.of(attrAnno.timezone());
                timezone = TimeZone.getTimeZone(zoneId);
            }

            flat = attrAnno.flat();
            required = attrAnno.required();


            if (attrAnno.ignore()) {
                encode = false;
                decode = false;
            } else {
                encode = attrAnno.encode();
                decode = attrAnno.decode();
            }

            if (attrAnno.encoder().isInterface() == false) {
                encoder = ClassUtil.newInstance(attrAnno.encoder());
            }

            if (attrAnno.decoder().isInterface() == false) {
                decoder = ClassUtil.newInstance(attrAnno.decoder());
            }

            featuresValue = Feature.addFeatures(0L, attrAnno.features());

            //为空
            empty = false;
        } else {
            //非空（即没有注解）
            empty = true;
        }

        if (Asserts.isEmpty(alias)) {
            alias = realName;
        }
    }

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFormat() {
        return format;
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    /**
     * @deprecated 4.0
     * */
    @Deprecated
    public TimeZone getTimezone() {
        return timezone;
    }

    public String formatDate(Date value) {
        if (getZoneId() != null) {
            return DateUtil.format(value, getFormat(), getZoneId());
        } else {
            return DateUtil.format(value, getFormat());
        }
    }

    public boolean isFlat() {
        return flat;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isEmpty() {
        return empty;
    }

    public boolean hasFeature(Feature feature) {
        return Feature.hasFeature(featuresValue, feature);
    }

    public boolean isEncode() {
        return encode;
    }

    public boolean isDecode() {
        return decode;
    }

    public ObjectEncoder getEncoder() {
        return encoder;
    }

    public ObjectDecoder getDecoder() {
        return decoder;
    }
}
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
package org.noear.snack4.jsonschema;

/**
 *
 * @author noear 2025/11/14 created
 * @since 4.0
 */
public enum SchemaVersion {
    DRAFT_7("http://json-schema.org/draft-07/schema#"),
    DRAFT_2019_09("https://json-schema.org/draft/2019-09/schema"),
    DRAFT_2020_12("https://json-schema.org/draft/2020-12/schema");

    private final String identifier;

    private SchemaVersion(String schemaIdentifier) {
        this.identifier = schemaIdentifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}

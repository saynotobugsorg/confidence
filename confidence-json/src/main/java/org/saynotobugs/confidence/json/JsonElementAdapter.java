/*
 * Copyright 2024 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.saynotobugs.confidence.json;

import org.dmfs.jems2.Optional;

/**
 * An adapter to access JSON values in a type safe way.
 */
public interface JsonElementAdapter
{
    Optional<JsonObjectAdapter> asObject();

    Optional<JsonArrayAdapter> asArray();

    Optional<String> asString();

    Optional<Number> asNumber();

    Optional<Boolean> asBoolean();

    boolean isNull();
}

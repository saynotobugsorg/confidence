/*
 * Copyright 2023 dmfs GmbH
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.saynotobugs.confidence.json.jsonpathadapter;

import io.restassured.path.json.JsonPath;
import org.dmfs.jems2.Optional;
import org.dmfs.jems2.optional.*;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;

import java.util.List;
import java.util.Map;

public final class JsonPathElementAdapter implements JsonElementAdapter
{
    private final JsonPath mJsonPath;
    private final String mPath;

    public JsonPathElementAdapter(JsonPath jsonPath, String path)
    {
        mJsonPath = jsonPath;
        mPath = path;
    }

    @Override
    public Optional<JsonObjectAdapter> asObject()
    {
        return mJsonPath.get(mPath) instanceof Map
            ? new Present<>(new ObjectAdapter(mJsonPath, mPath))
            : new Absent<>();
    }

    @Override
    public Optional<JsonArrayAdapter> asArray()
    {
        return mJsonPath.get(mPath) instanceof List
            ? new Present<>(new ArrayAdapter(mJsonPath, mPath))
            : new Absent<>();
    }

    @Override
    public Optional<String> asString()
    {
        // avoid class cast exception by getting json element as an `Object` and safely cast afterward
        return new Mapped<>(String.class::cast, new Sieved<>(String.class::isInstance, new NullSafe<Object>(mJsonPath.get(mPath))));
    }

    @Override
    public Optional<Number> asNumber()
    {
        return new Mapped<>(Number.class::cast, new Sieved<>(Number.class::isInstance, new NullSafe<Object>(mJsonPath.get(mPath))));
    }

    @Override
    public Optional<Boolean> asBoolean()
    {
        return new Mapped<>(Boolean.class::cast, new Sieved<>(Boolean.class::isInstance, new NullSafe<Object>(mJsonPath.get(mPath))));
    }

    @Override
    public boolean isNull()
    {
        return mJsonPath.get(mPath) == null;
    }

    @Override
    public String toString()
    {
        return mJsonPath.prettify();
    }
}

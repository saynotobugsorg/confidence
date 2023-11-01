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

package org.saynotobugs.confidence.json.jsonstringadapter;

import org.dmfs.jems2.Optional;
import org.dmfs.jems2.optional.*;
import org.dmfs.jems2.predicate.AnyOf;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;

public final class JsonStringElementAdapter implements JsonElementAdapter
{
    private final String mJsonString;

    public JsonStringElementAdapter(String jsonString)
    {
        mJsonString = jsonString;
    }

    @Override
    public Optional<JsonObjectAdapter> asObject()
    {
        try
        {
            return new Present<>(new ObjectAdapter(new JSONObject(mJsonString)));
        }
        catch (JSONException e)
        {
            return Absent.absent();
        }
    }

    @Override
    public Optional<JsonArrayAdapter> asArray()
    {
        try
        {
            return new Present<>(new ArrayAdapter(new JSONArray(mJsonString)));
        }
        catch (JSONException e)
        {
            return Absent.absent();
        }
    }

    @Override
    public Optional<String> asString()
    {
        try
        {
            return new Restrained<>(
                () -> mJsonString.startsWith("\"") && mJsonString.endsWith("\""),
                new Mapped<>(Object::toString,
                    new Sieved<>(String.class::isInstance,
                        new NullSafe<>(new JSONArray("[" + mJsonString + "]").get(0)))));
        }
        catch (JSONException e)
        {
            return Absent.absent();
        }
    }

    @Override
    public Optional<Number> asNumber()
    {
        try
        {
            return new Mapped<>(Number.class::cast,
                new Sieved<>(Number.class::isInstance,
                    new NullSafe<>(new JSONArray("[" + mJsonString + "]").opt(0))));
        }
        catch (JSONException e)
        {
            return Absent.absent();
        }
    }

    @Override
    public Optional<Boolean> asBoolean()
    {
        return new Mapped<>(Boolean::parseBoolean, new Sieved<>(new AnyOf<>("true", "false"), new Present<>(mJsonString)));
    }

    @Override
    public boolean isNull()
    {
        return "null".equals(mJsonString);
    }

    @Override
    public String toString()
    {
        return mJsonString;
    }
}

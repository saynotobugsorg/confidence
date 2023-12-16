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
import org.dmfs.jems2.optional.Absent;
import org.dmfs.jems2.optional.Present;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;

import java.util.concurrent.atomic.AtomicInteger;

final class ObjectAdapter implements JsonObjectAdapter
{
    private static final AtomicInteger IDENTIFIER = new AtomicInteger(0);

    private final JsonPath mJsonPath;
    private final String mPath;
    private final int mId;

    public ObjectAdapter(JsonPath jsonPath, String path)
    {
        mJsonPath = jsonPath;
        mPath = path;
        mId = IDENTIFIER.getAndIncrement();
    }

    @Override
    public Optional<JsonElementAdapter> member(String key)
    {
        String param = "key" + mId;
        JsonPath withParam = mJsonPath.param(param, key);
        return withParam.getBoolean(mPath.isEmpty() ? "containsKey(" + param + ")" : mPath + ".containsKey(" + param + ")") ?
            new Present<>(new JsonPathElementAdapter(withParam, mPath.isEmpty() ? "get(" + param + ")" : mPath + ".get(" + param + ")")) :
            new Absent<>();
    }

    @Override
    public int length()
    {
        return mJsonPath.get(mPath.isEmpty() ? "size()" : mPath + ".size()");
    }


    @Override
    public String toString()
    {
        return mJsonPath.prettify();
    }
}

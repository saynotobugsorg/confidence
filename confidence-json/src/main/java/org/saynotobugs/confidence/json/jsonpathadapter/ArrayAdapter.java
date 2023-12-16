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
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;

final class ArrayAdapter implements JsonArrayAdapter
{
    private final JsonPath mJsonPath;
    private final String mPath;

    public ArrayAdapter(JsonPath jsonPath, String path)
    {
        mJsonPath = jsonPath;
        mPath = path;
    }

    @Override
    public Optional<JsonElementAdapter> elementAt(int index)
    {
        int length = length();
        if (index < 0)
        {
            index = length + index;
        }
        return 0 <= index && index < length
            ? new Present<>(new JsonPathElementAdapter(mJsonPath, mPath + "[" + index + "]"))
            : new Absent<>();
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

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
import org.dmfs.jems2.optional.Mapped;
import org.dmfs.jems2.optional.NullSafe;
import org.dmfs.jems2.optional.Sieved;
import org.dmfs.jems2.single.Backed;
import org.json.JSONObject;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;

import java.util.Objects;

import static java.lang.Boolean.TRUE;

public final class ObjectMemberAdapter implements JsonElementAdapter
{
    private final JSONObject mObject;
    private final String mKey;

    public ObjectMemberAdapter(JSONObject object, String key)
    {
        mObject = object;
        mKey = key;
    }


    @Override
    public Optional<JsonObjectAdapter> asObject()
    {
        return new Mapped<>(ObjectAdapter::new, new NullSafe<>(mObject.optJSONObject(mKey, null)));
    }

    @Override
    public Optional<JsonArrayAdapter> asArray()
    {
        return new Mapped<>(ArrayAdapter::new, new NullSafe<>(mObject.optJSONArray(mKey)));
    }

    @Override
    public Optional<String> asString()
    {
        return new Mapped<>(Object::toString, new Sieved<>(String.class::isInstance, new NullSafe<>(mObject.opt(mKey))));
    }

    @Override
    public Optional<Number> asNumber()
    {
        return new Mapped<>(Number.class::cast, new Sieved<>(Number.class::isInstance, new NullSafe<>(mObject.opt(mKey))));
    }

    @Override
    public Optional<Boolean> asBoolean()
    {
        return new Mapped<>(TRUE::equals, new Sieved<>(Boolean.class::isInstance, new NullSafe<>(mObject.opt(mKey))));
    }

    @Override
    public boolean isNull()
    {
        return mObject.has(mKey) && mObject.isNull(mKey);
    }


    @Override
    public String toString()
    {
        return new Backed<>(new Mapped<>(Objects::toString, new NullSafe<>(mObject.opt(mKey))), "null").value();
    }
}

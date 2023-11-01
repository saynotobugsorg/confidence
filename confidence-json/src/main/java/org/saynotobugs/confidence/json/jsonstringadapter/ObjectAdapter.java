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
import org.dmfs.jems2.optional.Absent;
import org.dmfs.jems2.optional.Present;
import org.json.JSONObject;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;

public final class ObjectAdapter implements JsonObjectAdapter
{
    private final JSONObject mDelegate;

    public ObjectAdapter(JSONObject delegate)
    {
        mDelegate = delegate;
    }

    @Override
    public Optional<JsonElementAdapter> member(String key)
    {
        return mDelegate.has(key) ? new Present<>(new ObjectMemberAdapter(mDelegate, key)) : new Absent<>();
    }

    @Override
    public int length()
    {
        return mDelegate.length();
    }


    @Override
    public String toString()
    {
        return mDelegate.toString();
    }
}

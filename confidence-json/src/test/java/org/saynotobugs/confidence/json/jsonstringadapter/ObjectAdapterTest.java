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

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.HasToString;

import static org.dmfs.jems2.confidence.Jems2.absent;
import static org.dmfs.jems2.confidence.Jems2.present;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ObjectAdapterTest
{
    @Test
    void testEmptyObject()
    {
        assertThat(new ObjectAdapter(new JSONObject()),
            new AllOf<>(
                new Has<>(o -> o.member("any"), absent()),
                new Has<>(JsonObjectAdapter::length, new Is<>(0)),
                new HasToString("{}")
            ));
    }

    @Test
    void testNonEmptyObject()
    {
        assertThat(new ObjectAdapter(new JSONObject("{\"abc\": 123}")),
            new AllOf<>(
                new Has<>(o -> o.member("any"), absent()),
                new Has<>(o -> o.member("abc"), present(new Has<>(JsonElementAdapter::asNumber, present(123)))),
                new Has<>(JsonObjectAdapter::length, new Is<>(1)),
                new HasToString("{\"abc\":123}")
            ));
    }
}
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

package org.saynotobugs.confidence.json.jsonstringadapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.HasToString;

import static java.util.Arrays.asList;
import static org.dmfs.jems2.confidence.Jems2.absent;
import static org.dmfs.jems2.confidence.Jems2.present;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ArrayElementAdapterTest
{
    @Test
    void testEmptyArray()
    {
        assertThat(new ArrayElementAdapter(new JSONArray(), 0),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>(JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("null")
            ));
    }

    @Test
    void testObject()
    {
        assertThat(new ArrayElementAdapter(new JSONArray(asList("", 123, new JSONObject())), 2),
            new AllOf<>(
                new Has<>("asArray", JsonElementAdapter::asArray, absent()),
                new Has<>("asObject", JsonElementAdapter::asObject, present(new Anything())),
                new Has<>("asString", JsonElementAdapter::asString, absent()),
                new Has<>("asNumber", JsonElementAdapter::asNumber, absent()),
                new Has<>("asBoolean", JsonElementAdapter::asBoolean, absent()),
                new Has<>("isNull", JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("{}")
            ));
    }


    @Test
    void testArray()
    {
        assertThat(new ArrayElementAdapter(new JSONArray(asList("", 123, new JSONArray())), 2),
            new AllOf<>(
                new Has<>("asArray", JsonElementAdapter::asArray, present(new Anything())),
                new Has<>("asObject", JsonElementAdapter::asObject, absent()),
                new Has<>("asString", JsonElementAdapter::asString, absent()),
                new Has<>("asNumber", JsonElementAdapter::asNumber, absent()),
                new Has<>("asBoolean", JsonElementAdapter::asBoolean, absent()),
                new Has<>("isNull", JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("[]")
            ));
    }


    @Test
    void testString()
    {
        assertThat(new ArrayElementAdapter(new JSONArray(asList("", 123, "abc")), 2),
            new AllOf<>(
                new Has<>("asArray", JsonElementAdapter::asArray, absent()),
                new Has<>("asObject", JsonElementAdapter::asObject, absent()),
                new Has<>("asString", JsonElementAdapter::asString, present("abc")),
                new Has<>("asNumber", JsonElementAdapter::asNumber, absent()),
                new Has<>("asBoolean", JsonElementAdapter::asBoolean, absent()),
                new Has<>("isNull", JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("abc")
            ));
    }


    @Test
    void testNumberString()
    {
        assertThat(new ArrayElementAdapter(new JSONArray(asList("", 123, "123")), 2),
            new AllOf<>(
                new Has<>("asArray", JsonElementAdapter::asArray, absent()),
                new Has<>("asObject", JsonElementAdapter::asObject, absent()),
                new Has<>("asString", JsonElementAdapter::asString, present("123")),
                new Has<>("asNumber", JsonElementAdapter::asNumber, absent()),
                new Has<>("asBoolean", JsonElementAdapter::asBoolean, absent()),
                new Has<>("isNull", JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("123")
            ));
    }


    @Test
    void testBoolString()
    {
        assertThat(new ArrayElementAdapter(new JSONArray(asList("", 123, "true")), 2),
            new AllOf<>(
                new Has<>("asArray", JsonElementAdapter::asArray, absent()),
                new Has<>("asObject", JsonElementAdapter::asObject, absent()),
                new Has<>("asString", JsonElementAdapter::asString, present("true")),
                new Has<>("asNumber", JsonElementAdapter::asNumber, absent()),
                new Has<>("asBoolean", JsonElementAdapter::asBoolean, absent()),
                new Has<>("isNull", JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("true")
            ));
    }


    @Test
    void testNumber()
    {
        assertThat(new ArrayElementAdapter(new JSONArray(asList("", 123, 456)), 2),
            new AllOf<>(
                new Has<>("asArray", JsonElementAdapter::asArray, absent()),
                new Has<>("asObject", JsonElementAdapter::asObject, absent()),
                new Has<>("asString", JsonElementAdapter::asString, absent()),
                new Has<>("asNumber", JsonElementAdapter::asNumber, present(456)),
                new Has<>("asBoolean", JsonElementAdapter::asBoolean, absent()),
                new Has<>("isNull", JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("456")
            ));
    }


    @Test
    void testBoolean()
    {
        assertThat(new ArrayElementAdapter(new JSONArray(asList("", 123, true)), 2),
            new AllOf<>(
                new Has<>("asArray", JsonElementAdapter::asArray, absent()),
                new Has<>("asObject", JsonElementAdapter::asObject, absent()),
                new Has<>("asString", JsonElementAdapter::asString, absent()),
                new Has<>("asNumber", JsonElementAdapter::asNumber, absent()),
                new Has<>("asBoolean", JsonElementAdapter::asBoolean, present(true)),
                new Has<>("isNull", JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("true")
            ));
    }


    @Test
    void testNull()
    {
        assertThat(new ArrayElementAdapter(new JSONArray(asList("", 123, null)), 2),
            new AllOf<>(
                new Has<>("asArray", JsonElementAdapter::asArray, absent()),
                new Has<>("asObject", JsonElementAdapter::asObject, absent()),
                new Has<>("asString", JsonElementAdapter::asString, absent()),
                new Has<>("asNumber", JsonElementAdapter::asNumber, absent()),
                new Has<>("asBoolean", JsonElementAdapter::asBoolean, absent()),
                new Has<>("isNull", JsonElementAdapter::isNull, new Is<>(true)),
                new HasToString("null")
            ));
    }
}
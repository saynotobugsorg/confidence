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

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.quality.object.HasToString;

import static org.dmfs.jems2.confidence.Jems2.absent;
import static org.dmfs.jems2.confidence.Jems2.present;
import static org.saynotobugs.confidence.Assertion.assertThat;

class JsonStringElementAdapterTest
{
    @Test
    void testArray()
    {
        assertThat(new JsonStringElementAdapter("[]"),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, present(new Has<>(JsonArrayAdapter::length, new EqualTo<>(0)))),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>(JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("[]")
            ));
    }


    @Test
    void testObject()
    {
        assertThat(new JsonStringElementAdapter("{}"),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, present(new Has<>(JsonObjectAdapter::length, new EqualTo<>(0)))),
                new Has<>(JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("{}")
            ));
    }


    @Test
    void testString()
    {
        assertThat(new JsonStringElementAdapter("\"abc\""),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>(JsonElementAdapter::asString, present("abc")),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("\"abc\"")
            ));
    }


    @Test
    void testNumberString()
    {
        assertThat(new JsonStringElementAdapter("\"123\""),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>(JsonElementAdapter::asString, present("123")),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("\"123\"")
            ));
    }


    @Test
    void testBooleanString()
    {
        assertThat(new JsonStringElementAdapter("\"true\""),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>(JsonElementAdapter::asString, present("true")),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("\"true\"")
            ));
    }


    @Test
    void testNumber()
    {
        assertThat(new JsonStringElementAdapter("123"),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>(JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, present(123)),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("123")
            ));
    }


    @Test
    void testBoolean()
    {
        assertThat(new JsonStringElementAdapter("true"),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>(JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, present(true)),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("true")
            ));
    }


    @Test
    void testNull()
    {
        assertThat(new JsonStringElementAdapter("null"),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>(JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(true)),
                new HasToString("null")
            ));
    }


    @Test
    void testIllegal1()
    {
        assertThat(new JsonStringElementAdapter("abcde"),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>("asString", JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("abcde")
            ));
    }

    @Test
    void testIllegal2()
    {
        assertThat(new JsonStringElementAdapter("abc[de"),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, absent()),
                new Has<>("asString", JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("abc[de")
            ));
    }
}
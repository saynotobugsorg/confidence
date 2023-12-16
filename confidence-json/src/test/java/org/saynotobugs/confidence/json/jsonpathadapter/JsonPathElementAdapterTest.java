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
import io.restassured.path.json.exception.JsonPathException;
import org.dmfs.jems2.Optional;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.quality.object.HasToString;
import org.saynotobugs.confidence.quality.object.Satisfies;

import static org.dmfs.jems2.confidence.Jems2.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

class JsonPathElementAdapterTest
{
    @Test
    void testArray()
    {
        assertThat(new JsonPathElementAdapter(JsonPath.from("[]"), ""),
            new AllOf<>(
                new Has<>("array", JsonElementAdapter::asArray, present(new Has<>(JsonArrayAdapter::length, new EqualTo<>(0)))),
                new Has<>("object", JsonElementAdapter::asObject, absent()),
                new Has<>("string", JsonElementAdapter::asString, absent()),
                new Has<>("number", JsonElementAdapter::asNumber, absent()),
                new Has<>("boolean", JsonElementAdapter::asBoolean, absent()),
                new Has<>("isNull", JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("[\n    \n]")
            ));
    }

    @Test
    void testObject()
    {
        assertThat(new JsonPathElementAdapter(JsonPath.from("{}"), ""),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, present(new Has<>(JsonObjectAdapter::length, new EqualTo<>(0)))),
                new Has<>(JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("{\n    \n}")
            ));
    }


    @Test
    void testNestedObject()
    {
        assertThat(new JsonPathElementAdapter(JsonPath.from("{\"a\":  {\"b\":  {\"c\": {}}}}"), "a.b.c"),
            new AllOf<>(
                new Has<>(JsonElementAdapter::asArray, absent()),
                new Has<>(JsonElementAdapter::asObject, present(new Has<>(JsonObjectAdapter::length, new EqualTo<>(0)))),
                new Has<>(JsonElementAdapter::asString, absent()),
                new Has<>(JsonElementAdapter::asNumber, absent()),
                new Has<>(JsonElementAdapter::asBoolean, absent()),
                new Has<>(JsonElementAdapter::isNull, new Is<>(false)),
                new HasToString("{\n    \"a\": {\n        \"b\": {\n            \"c\": {\n                \n            }\n        }\n    }\n}")
            ));
    }


    @Test
    void testString()
    {
        assertThat(new JsonPathElementAdapter(JsonPath.from("\"abc\""), ""),
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
        assertThat(new JsonPathElementAdapter(JsonPath.from("\"123\""), ""),
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
        assertThat(new JsonPathElementAdapter(JsonPath.from("\"true\""), ""),
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
        assertThat(new JsonPathElementAdapter(JsonPath.from("123"), ""),
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
        assertThat(new JsonPathElementAdapter(JsonPath.from("true"), ""),
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
        assertThat(new JsonPathElementAdapter(JsonPath.from("null"), ""),
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
        assertThat(new JsonPathElementAdapter(JsonPath.from("abcde"), ""),
            new AllOf<>(
                new Has<>(j -> j::asArray, throwing(JsonPathException.class)),
                new Has<>(j -> j::asObject, throwing(JsonPathException.class)),
                new Has<>("asString", j -> j::asString, throwing(JsonPathException.class)),
                new Has<>(j -> j::asNumber, throwing(JsonPathException.class)),
                new Has<>(j -> j::asBoolean, throwing(JsonPathException.class)),
                new Has<>(j -> j::isNull, throwing(JsonPathException.class)),
                new Has<>(j -> j::toString, throwing(JsonPathException.class))
            ));
    }

    @Test
    void testIllegal2()
    {
        assertThat(new JsonPathElementAdapter(JsonPath.from("abc[de"), ""),
            new AllOf<>(
                new Has<>(j -> j::asArray, throwing(JsonPathException.class)),
                new Has<>(j -> j::asObject, throwing(JsonPathException.class)),
                new Has<>("asString", j -> j::asString, throwing(JsonPathException.class)),
                new Has<>(j -> j::asNumber, throwing(JsonPathException.class)),
                new Has<>(j -> j::asBoolean, throwing(JsonPathException.class)),
                new Has<>(j -> j::isNull, throwing(JsonPathException.class)),
                new Has<>(j -> j::toString, throwing(JsonPathException.class))
            ));
    }
}
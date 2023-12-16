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
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.HasToString;
import org.saynotobugs.confidence.quality.trivial.Anything;

import static org.dmfs.jems2.confidence.Jems2.absent;
import static org.dmfs.jems2.confidence.Jems2.present;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ObjectAdapterTest
{
    @Test
    void testEmptyObject()
    {
        assertThat(new ObjectAdapter(JsonPath.from("{}"), ""),
            new AllOf<>(
                new Has<>(o -> o.member("any"), absent()),
                new Has<>(JsonObjectAdapter::length, new Is<>(0)),
                new HasToString("{\n    \n}")
            ));
    }


    @Test
    void testNestedObject1()
    {
        assertThat(new ObjectAdapter(JsonPath.from("[1,2, {\"a\": {} }]"), "[2].get('a')"),
            new AllOf<>(
                new Has<>(o -> o.member("any"), absent()),
                new Has<>(JsonObjectAdapter::length, new Is<>(0)),
                new HasToString("[\n    1,\n    2,\n    {\n        \"a\": {\n            \n        }\n    }\n]")
            ));
    }


    @Test
    void testNestedObject2()
    {
        assertThat(new ObjectAdapter(JsonPath.from("{\"a\": {\"b\": {\"c\":2} }}"), "get('a').get('b')"),
            new AllOf<>(
                new Has<>(o -> o.member("c"), present()),
                new Has<>(o -> o.member("any"), absent()),
                new Has<>(JsonObjectAdapter::length, new Is<>(1)),
                new HasToString("{\n    \"a\": {\n        \"b\": {\n            \"c\": 2\n        }\n    }\n}")
            ));
    }

    @Test
    void testNonEmptyObject()
    {
        assertThat(new ObjectAdapter(JsonPath.from("{\n  \"p1\": \"a\",\n  \"p2\": 123,\n  \"p3\": true,\n  \"p4\": {}, \"p5\": []\n}"), ""),
            new AllOf<>(
                new Has<>(o -> o.member("p6"), absent()),
                new Has<>(o -> o.member("p1"), present(new Has<>(JsonElementAdapter::asString, present("a")))),
                new Has<>(o -> o.member("p2"), present(new Has<>(JsonElementAdapter::asNumber, present(123)))),
                new Has<>(o -> o.member("p3"), present(new Has<>(JsonElementAdapter::asBoolean, present(true)))),
                new Has<>(o -> o.member("p4"), present(new Has<>(JsonElementAdapter::asObject, present(new Anything())))),
                new Has<>(o -> o.member("p5"), present(new Has<>(JsonElementAdapter::asArray, present(new Anything())))),
                new Has<>(JsonObjectAdapter::length, new Is<>(5)),
                new HasToString("{\n    \"p1\": \"a\",\n    \"p2\": 123,\n    \"p3\": true,\n    \"p4\": {\n        \n    },\n    \"p5\": [\n        \n    ]\n}")
            ));
    }
}
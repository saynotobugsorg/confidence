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

package org.saynotobugs.confidence.json.quality;

import org.dmfs.jems2.optional.Absent;
import org.dmfs.jems2.optional.Present;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.Nothing;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ObjectTest
{
    @Test
    void testAnything()
    {
        assertThat(new Object(new Anything()),
            new AllOf<>(
                new Passes<>(mock(JsonElementAdapter.class, with(JsonElementAdapter::asObject, returning(new Present<>(mock("JSON Object", JsonObjectAdapter.class))))), "{\n" +
                    "  <JSON Object>\n" +
                    "}"),
                new Fails<>(mock(JsonElementAdapter.class, with(JsonElementAdapter::asObject, returning(new Absent<>()))),
                    "not an object"),
                new HasDescription("{\n  <anything>\n}")
            ));
    }

    @Test
    void testNothing()
    {
        assertThat(new Object(new Nothing()),
            new AllOf<>(
                new Fails<>(mock(JsonElementAdapter.class, with(JsonElementAdapter::asObject, returning(new Present<>(mock("Object Adapter", JsonObjectAdapter.class))))),
                    "{\n  <Object Adapter>\n}"),
                new Fails<>(mock(JsonElementAdapter.class, with(JsonElementAdapter::asObject, returning(new Absent<>()))),
                    "not an object"),
                new HasDescription("{\n  <nothing>\n}")
            ));
    }


    @Test
    void testDelegates()
    {
        assertThat(new Object(new With("a", "b"), new With("c", 123)),
            new AllOf<>(
                new Passes<>(mock(JsonElementAdapter.class,
                    with(JsonElementAdapter::asObject, returning(new Present<>(
                        mock("Object Adapter", JsonObjectAdapter.class,
                            with(a -> a.member("a"), returning(new Present<>(mock(JsonElementAdapter.class,
                                with(JsonElementAdapter::asString, returning(new Present<>("b"))))))),
                            with(a -> a.member("c"), returning(new Present<>(mock(JsonElementAdapter.class,
                                with(JsonElementAdapter::asNumber, returning(new Present<>(123)))))))))))),
                    "{\n" +
                        "  \"a\": \"b\",\n" +
                        "  \"c\": 123\n" +
                        "}"),
                new Fails<>(mock(JsonElementAdapter.class,
                    with(JsonElementAdapter::asObject, returning(new Present<>(
                        mock("Object Adapter", JsonObjectAdapter.class,
                            with(a -> a.member("a"), returning(new Present<>(mock(JsonElementAdapter.class,
                                with(JsonElementAdapter::asString, returning(new Present<>("b"))))))),
                            with(a -> a.member("c"), returning(new Present<>(mock(JsonElementAdapter.class,
                                with(JsonElementAdapter::asNumber, returning(new Present<>(124)))))))))))),
                    "{\n  ...,\n  \"c\": 124\n}"),
                new Fails<>(mock(JsonElementAdapter.class, with(JsonElementAdapter::asObject, returning(new Absent<>()))),
                    "not an object"),
                new HasDescription("{\n  \"a\": \"b\",\n  \"c\": 123\n}")
            ));
    }
}
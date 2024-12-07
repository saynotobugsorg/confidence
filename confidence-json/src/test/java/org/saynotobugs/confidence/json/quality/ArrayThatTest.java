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

package org.saynotobugs.confidence.json.quality;

import org.dmfs.jems2.optional.Absent;
import org.dmfs.jems2.optional.Present;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ArrayThatTest
{
    @Test
    void test()
    {
        assertThat(new ArrayThat(new Iterates<>(new String("a"), new String("b"))),
            new AllOf<>(
                new Passes<>(mock("JSON Array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Present<>(mock(JsonArrayAdapter.class,
                        with(JsonArrayAdapter::length, returning(2)),
                        with(a -> a.elementAt(0), returning(new Present<>(
                            mock(JsonElementAdapter.class, with(JsonElementAdapter::asString, returning(new Present<>("a"))))))),
                        with(a -> a.elementAt(1), returning(new Present<>(
                            mock(JsonElementAdapter.class, with(JsonElementAdapter::asString, returning(new Present<>("b")))))))))))), "array that iterated [\n" +
                    "  0: \"a\"\n" +
                    "  1: \"b\"\n" +
                    "]"),
                new Fails<>(mock("JSON Array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Present<>(mock(JsonArrayAdapter.class,
                        with(JsonArrayAdapter::length, returning(1)),
                        with(a -> a.elementAt(0), returning(new Present<>(
                            mock(JsonElementAdapter.class, with(JsonElementAdapter::asString, returning(new Present<>("a")))))))))))),
                    "array that iterated [\n  ...\n  1: missing \"b\"\n]"),
                new Fails<>(mock("JSON Array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Present<>(mock(JsonArrayAdapter.class,
                        with(JsonArrayAdapter::length, returning(2)),
                        with(a -> a.elementAt(0), returning(new Present<>(
                            mock(JsonElementAdapter.class, with(JsonElementAdapter::asString, returning(new Present<>("x"))))))),
                        with(a -> a.elementAt(1), returning(new Present<>(
                            mock(JsonElementAdapter.class, with(JsonElementAdapter::asString, returning(new Present<>("b")))))))))))),
                    "array that iterated [\n  0: \"x\"\n  ...\n]"),
                new Fails<>(mock("JSON Array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Present<>(mock(JsonArrayAdapter.class,
                        with(JsonArrayAdapter::length, returning(3)),
                        with(a -> a.elementAt(0), returning(new Present<>(
                            mock(JsonElementAdapter.class, with(JsonElementAdapter::asString, returning(new Present<>("a"))))))),
                        with(a -> a.elementAt(1), returning(new Present<>(
                            mock(JsonElementAdapter.class, with(JsonElementAdapter::asString, returning(new Present<>("b"))))))),
                        with(a -> a.elementAt(2), returning(new Present<>(
                            mock("additional Element", JsonElementAdapter.class, with(JsonElementAdapter::asString, returning(new Present<>("c")))))))))))),
                    "array that iterated [\n  ...\n  2: additional <additional Element>\n]"),
                new Fails<>(mock("JSON Array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Absent<>()))),
                    "not an array"),
                new HasDescription("array that iterates [\n  0: \"a\"\n  1: \"b\"\n]")
            )
        );
    }
}
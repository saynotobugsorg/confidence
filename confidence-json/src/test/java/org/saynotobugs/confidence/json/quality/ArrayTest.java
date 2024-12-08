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
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.Nothing;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ArrayTest
{
    @Test
    void testAnyArray()
    {
        assertThat(new Array(new Anything()),
            new AllOf<>(
                new Passes<>(mock(JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Present<>(mock("JSON Array", JsonArrayAdapter.class))))), "array,\n" +
                    "  <JSON Array>"),
                new Fails<>(mock("not an array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Absent<>()))),
                    "not an array"),
                new HasDescription("array,\n  <anything>")
            ));
    }

    @Test
    void testNothing()
    {
        assertThat(new Array(new Nothing()),
            new AllOf<>(
                new Fails<>(mock("JSON Array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Present<>(mock("Array Adapter", JsonArrayAdapter.class))))),
                    "array,\n  <Array Adapter>"),
                new Fails<>(mock("not an array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Absent<>()))),
                    "not an array"),
                new HasDescription("array,\n  <nothing>")
            ));
    }

    @Test
    void testStringArray()
    {
        assertThat(new Array("a", "b"),
            new AllOf<>(
                new Passes<>(
                    mock("JSON Array", JsonElementAdapter.class,
                        with(JsonElementAdapter::asArray, returning(
                            new Present<>(mock("Array Adapter", JsonArrayAdapter.class,
                                with(JsonArrayAdapter::length, returning(2)),
                                with(adapter -> adapter.elementAt(0), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asString, returning(new Present<>("a"))))))),
                                with(adapter -> adapter.elementAt(1), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asString, returning(new Present<>("b")))))))))))),
                    "[\n" +
                        "  0: \"a\",\n" +
                        "  1: \"b\"\n" +
                        "]"),
                new Fails<>(
                    mock("JSON Array", JsonElementAdapter.class,
                        with(JsonElementAdapter::asArray, returning(
                            new Present<>(mock("Array Adapter", JsonArrayAdapter.class,
                                with(JsonArrayAdapter::length, returning(2)),
                                with(adapter -> adapter.elementAt(0), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asString, returning(new Absent<>())))))),
                                with(adapter -> adapter.elementAt(1), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asString, returning(new Present<>("b")))))))))))),
                    "[\n  0: not a string,\n  ...\n]"),
                new Fails<>(mock("not an array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Absent<>()))),
                    "not an array"),
                new HasDescription("[\n  0: \"a\",\n  1: \"b\"\n]")
            ));
    }

    @Test
    void testNumberArray()
    {
        assertThat(new Array(1.2, 5),
            new AllOf<>(
                new Passes<>(
                    mock("JSON Array", JsonElementAdapter.class,
                        with(JsonElementAdapter::asArray, returning(
                            new Present<>(mock("Array Adapter", JsonArrayAdapter.class,
                                with(JsonArrayAdapter::length, returning(2)),
                                with(adapter -> adapter.elementAt(0), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asNumber, returning(new Present<>(1.2))))))),
                                with(adapter -> adapter.elementAt(1), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asNumber, returning(new Present<>(5)))))))))))),
                    "[\n" +
                        "  0: 1.2d,\n" +
                        "  1: 5\n" +
                        "]"),
                new Fails<>(
                    mock("JSON Array", JsonElementAdapter.class,
                        with(JsonElementAdapter::asArray, returning(
                            new Present<>(mock("Array Adapter", JsonArrayAdapter.class,
                                with(JsonArrayAdapter::length, returning(2)),
                                with(adapter -> adapter.elementAt(0), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asNumber, returning(new Absent<>())))))),
                                with(adapter -> adapter.elementAt(1), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asNumber, returning(new Present<>(5)))))))))))),
                    "[\n  0: not a number,\n  ...\n]"),
                new Fails<>(mock("not an array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Absent<>()))),
                    "not an array"),
                new HasDescription("[\n  0: 1.2d,\n  1: 5\n]")
            ));
    }


    @Test
    void testQualityArray()
    {
        assertThat(new Array(new AllOf<>(new At(0, "a"), new At(1, "b"))),
            new AllOf<>(
                new Passes<>(
                    mock("JSON Array", JsonElementAdapter.class,
                        with(JsonElementAdapter::asArray, returning(
                            new Present<>(mock("Array Adapter", JsonArrayAdapter.class,
                                with(adapter -> adapter.elementAt(0), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asString, returning(new Present<>("a"))))))),
                                with(adapter -> adapter.elementAt(1), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asString, returning(new Present<>("b")))))))))))),
                    "array,\n" +
                        "  all of\n" +
                        "    0: 0: \"a\"\n" +
                        "    1: 1: \"b\""),
                new Fails<>(
                    mock("JSON Array", JsonElementAdapter.class,
                        with(JsonElementAdapter::asArray, returning(
                            new Present<>(mock("Array Adapter", JsonArrayAdapter.class,
                                with(adapter -> adapter.elementAt(0), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asString, returning(new Absent<>())))))),
                                with(adapter -> adapter.elementAt(1), returning(
                                    new Present<>(mock(JsonElementAdapter.class,
                                        with(JsonElementAdapter::asString, returning(new Present<>("b")))))))))))),
                    "array,\n  all of\n    0: 0: not a string\n    ..."),
                new Fails<>(mock("not an array", JsonElementAdapter.class,
                    with(JsonElementAdapter::asArray, returning(new Absent<>()))),
                    "not an array"),
                new HasDescription("array,\n  all of\n    0: 0: \"a\"\n    1: 1: \"b\"")
            ));
    }

}
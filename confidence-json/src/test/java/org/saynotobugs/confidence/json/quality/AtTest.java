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

class AtTest
{
    @Test
    void testAnything()
    {
        assertThat(new At(1, new Anything()),
            new AllOf<>(
                new Passes<>(mock(JsonArrayAdapter.class, with(a -> a.elementAt(1), returning(new Present<>(mock("JSON Element", JsonElementAdapter.class))))), "1: <JSON Element>"),
                new Fails<>(mock(JsonArrayAdapter.class, with(a -> a.elementAt(1), returning(new Absent<>()))),
                    "1: missing"),
                new HasDescription("1: <anything>")
            ));
    }

    @Test
    void testNothing()
    {
        assertThat(new At(1, new Nothing()),
            new AllOf<>(
                new Fails<>(mock(JsonArrayAdapter.class, with(a -> a.elementAt(1), returning(new Present<>(mock("JSON Element", JsonElementAdapter.class))))),
                    "1: <JSON Element>"),
                new Fails<>(mock(JsonArrayAdapter.class, with(a -> a.elementAt(1), returning(new Absent<>()))),
                    "1: missing"),
                new HasDescription("1: <nothing>")
            ));
    }

    @Test
    void testWithNumber()
    {
        assertThat(new At(1, 123),
            new AllOf<>(
                new Passes<>(mock(JsonArrayAdapter.class,
                    with(a -> a.elementAt(1), returning(new Present<>(
                        mock("JSON Element", JsonElementAdapter.class,
                            with(JsonElementAdapter::asNumber, returning(new Present<>(123)))))))), "1: 123"),
                new Fails<>(mock(JsonArrayAdapter.class,
                    with(a -> a.elementAt(1), returning(new Present<>(
                        mock("JSON Element", JsonElementAdapter.class,
                            with(JsonElementAdapter::asNumber, returning(new Present<>(124)))))))),
                    "1: 124"),
                new Fails<>(mock(JsonArrayAdapter.class, with(a -> a.elementAt(1), returning(new Absent<>()))),
                    "1: missing"),
                new HasDescription("1: 123")
            ));
    }


    @Test
    void testWithString()
    {
        assertThat(new At(1, "abc"),
            new AllOf<>(
                new Passes<>(mock(JsonArrayAdapter.class,
                    with(a -> a.elementAt(1), returning(new Present<>(
                        mock("JSON Element", JsonElementAdapter.class,
                            with(JsonElementAdapter::asString, returning(new Present<>("abc")))))))), "1: \"abc\""),
                new Fails<>(mock(JsonArrayAdapter.class,
                    with(a -> a.elementAt(1), returning(new Present<>(
                        mock("JSON Element", JsonElementAdapter.class,
                            with(JsonElementAdapter::asString, returning(new Present<>("xyz")))))))),
                    "1: \"xyz\""),
                new Fails<>(mock(JsonArrayAdapter.class, with(a -> a.elementAt(1), returning(new Absent<>()))),
                    "1: missing"),
                new HasDescription("1: \"abc\"")
            ));
    }
}

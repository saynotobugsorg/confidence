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
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

class NumberTest
{
    @Test
    void testWithQuality()
    {
        assertThat(new Number(new EqualTo<>(3)),
            new AllOf<>(
                new Passes<>(mock("matching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(3))))),
                new Fails<>(mock("mismatching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(4)))),
                    "<4>"),
                new Fails<>(mock("no number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Absent<>()))),
                    "not a number"),
                new HasDescription("<3>")
            ));
    }

    @Test
    void testWithDouble()
    {
        assertThat(new Number(3d),
            new AllOf<>(
                new Passes<>(mock("matching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(3))))),
                new Fails<>(mock("mismatching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(4)))),
                    "<4> differs from <3.0> by <1.0>, which exceeds the ε of <4.440892098500626E-16>"),
                new Fails<>(mock("no number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Absent<>()))),
                    "not a number"),
                new HasDescription("differs from <3.0> by less than <4.440892098500626E-16>")
            ));
    }


    @Test
    void testWithFloat()
    {
        assertThat(new Number(3f),
            new AllOf<>(
                new Passes<>(mock("matching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(3))))),
                new Fails<>(mock("mismatching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(4)))),
                    "<4> differs from <3.0> by <1.0>, which exceeds the ε of <2.3841858E-7>"),
                new Fails<>(mock("no number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Absent<>()))),
                    "not a number"),
                new HasDescription("differs from <3.0> by less than <2.3841858E-7>")
            ));
    }


    @Test
    void testWithInteger()
    {
        assertThat(new Number(3),
            new AllOf<>(
                new Passes<>(mock("matching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(3))))),
                new Fails<>(mock("mismatching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(4)))),
                    "<4>"),
                new Fails<>(mock("no number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Absent<>()))),
                    "not a number"),
                new HasDescription("<3>")
            ));
    }


    @Test
    void testWithLong()
    {
        assertThat(new Number(3L),
            new AllOf<>(
                new Passes<>(mock("matching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(3L))))),
                new Fails<>(mock("mismatching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(4L)))),
                    "<4>"),
                new Fails<>(mock("no number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Absent<>()))),
                    "not a number"),
                new HasDescription("<3>")
            ));
    }


    @Test
    void testWithNumber()
    {
        assertThat(new Number((java.lang.Number) 3),
            new AllOf<>(
                new Passes<>(mock("matching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(3))))),
                new Fails<>(mock("mismatching number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Present<>(4)))),
                    "<4>"),
                new Fails<>(mock("no number", JsonElementAdapter.class,
                    with(JsonElementAdapter::asNumber, returning(new Absent<>()))),
                    "not a number"),
                new HasDescription("<3>")
            ));
    }


}
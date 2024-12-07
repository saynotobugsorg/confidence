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
import org.saynotobugs.confidence.json.JsonObjectAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

class WithTest
{
    @Test
    void testAnyValue()
    {
        assertThat(new With("abc"),
            new AllOf<>(
                new Passes<>(mock(JsonObjectAdapter.class, with(o -> o.member("abc"), returning(new Present<>(mock("JSON Element", JsonElementAdapter.class))))), "\"abc\": <JSON Element>"),
                new Fails<>(mock(JsonObjectAdapter.class, with(o -> o.member("abc"), returning(new Absent<>()))), "\"abc\": not a member"),
                new HasDescription("\"abc\": <anything>")
            ));
    }

    @Test
    void testStringValue()
    {
        assertThat(new With("abc", "123"),
            new AllOf<>(
                new Passes<>(mock(JsonObjectAdapter.class, with(o -> o.member("abc"),
                    returning(new Present<>(mock(JsonElementAdapter.class,
                        with(JsonElementAdapter::asString, returning(new Present<>("123")))))))), "\"abc\": \"123\""),
                new Fails<>(mock(JsonObjectAdapter.class, with(o -> o.member("abc"),
                    returning(new Present<>(mock(JsonElementAdapter.class,
                        with(JsonElementAdapter::asString, returning(new Absent<>()))))))), "\"abc\": not a string"),
                new Fails<>(mock(JsonObjectAdapter.class, with(o -> o.member("abc"),
                    returning(new Absent<>()))), "\"abc\": not a member"),
                new HasDescription("\"abc\": \"123\"")
            ));
    }


    @Test
    void testNumberValue()
    {
        assertThat(new With("abc", 123),
            new AllOf<>(
                new Passes<>(mock(JsonObjectAdapter.class, with(o -> o.member("abc"),
                    returning(new Present<>(mock(JsonElementAdapter.class,
                        with(JsonElementAdapter::asNumber, returning(new Present<>(123)))))))), "\"abc\": 123"),
                new Fails<>(mock(JsonObjectAdapter.class, with(o -> o.member("abc"),
                    returning(new Present<>(mock(JsonElementAdapter.class,
                        with(JsonElementAdapter::asNumber, returning(new Absent<>()))))))), "\"abc\": not a number"),
                new Fails<>(mock(JsonObjectAdapter.class, with(o -> o.member("abc"),
                    returning(new Absent<>()))), "\"abc\": not a member"),
                new HasDescription("\"abc\": 123")
            ));
    }
}

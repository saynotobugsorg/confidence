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
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

class BoolTest
{
    @Test
    void testTrue()
    {
        assertThat(new Bool(true),
            new AllOf<>(
                new Passes<>(mock("matching boolean", JsonElementAdapter.class,
                    with(JsonElementAdapter::asBoolean, returning(new Present<>(true))))),
                new Fails<>(mock("mismatching boolean", JsonElementAdapter.class,
                    with(JsonElementAdapter::asBoolean, returning(new Present<>(false)))),
                    "<false>"),
                new Fails<>(mock("no boolean", JsonElementAdapter.class,
                    with(JsonElementAdapter::asBoolean, returning(new Absent<>()))),
                    "not a boolean"),
                new HasDescription("<true>")
            ));
    }

    @Test
    void testFalse()
    {
        assertThat(new Bool(false),
            new AllOf<>(
                new Passes<>(mock("matching boolean", JsonElementAdapter.class,
                    with(JsonElementAdapter::asBoolean, returning(new Present<>(false))))),
                new Fails<>(mock("mismatching boolean", JsonElementAdapter.class,
                    with(JsonElementAdapter::asBoolean, returning(new Present<>(true)))),
                    "<true>"),
                new Fails<>(mock("no boolean", JsonElementAdapter.class,
                    with(JsonElementAdapter::asBoolean, returning(new Absent<>()))),
                    "not a boolean"),
                new HasDescription("<false>")
            ));
    }
}
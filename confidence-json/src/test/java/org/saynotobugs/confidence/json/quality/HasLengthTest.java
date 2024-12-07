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

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

class HasLengthTest
{
    @Test
    void testWithInteger()
    {
        assertThat(new HasLength(4),
            new AllOf<>(
                new Passes<>(mock(JsonArrayAdapter.class, with(JsonArrayAdapter::length, returning(4))), "had length 4"),
                new Fails<>(mock(JsonArrayAdapter.class, with(JsonArrayAdapter::length, returning(3))),
                    "had length 3"),
                new HasDescription("has length 4")
            ));
    }

    @Test
    void testWithQuality()
    {
        assertThat(new HasLength(new GreaterThan<>(3)),
            new AllOf<>(
                new Passes<>(mock(JsonArrayAdapter.class, with(JsonArrayAdapter::length, returning(4))), "had length 4"),
                new Passes<>(mock(JsonArrayAdapter.class, with(JsonArrayAdapter::length, returning(100))), "had length 100"),
                new Fails<>(mock(JsonArrayAdapter.class, with(JsonArrayAdapter::length, returning(3))),
                    "had length 3"),
                new Fails<>(mock(JsonArrayAdapter.class, with(JsonArrayAdapter::length, returning(0))),
                    "had length 0"),
                new HasDescription("has length greater than 3")
            ));
    }

}
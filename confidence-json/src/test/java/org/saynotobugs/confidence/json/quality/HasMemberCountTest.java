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

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.json.JsonObjectAdapter;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

class HasMemberCountTest
{
    @Test
    void testWithInteger()
    {
        assertThat(new HasMemberCount(4),
            new AllOf<>(
                new Passes<>(mock(JsonObjectAdapter.class, with(JsonObjectAdapter::length, returning(4))), "had member count 4"),
                new Fails<>(mock(JsonObjectAdapter.class, with(JsonObjectAdapter::length, returning(3))),
                    "had member count 3"),
                new HasDescription("has member count 4")
            ));
    }

    @Test
    void testWithQuality()
    {
        assertThat(new HasMemberCount(new GreaterThan<>(3)),
            new AllOf<>(
                new Passes<>(mock(JsonObjectAdapter.class, with(JsonObjectAdapter::length, returning(4))), "had member count 4"),
                new Passes<>(mock(JsonObjectAdapter.class, with(JsonObjectAdapter::length, returning(100))), "had member count 100"),
                new Fails<>(mock(JsonObjectAdapter.class, with(JsonObjectAdapter::length, returning(3))),
                    "had member count 3"),
                new Fails<>(mock(JsonObjectAdapter.class, with(JsonObjectAdapter::length, returning(0))),
                    "had member count 0"),
                new HasDescription("has member count greater than 3")
            ));
    }

}
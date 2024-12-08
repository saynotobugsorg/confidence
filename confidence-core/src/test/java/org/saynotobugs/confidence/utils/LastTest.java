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

package org.saynotobugs.confidence.utils;

import org.dmfs.jems2.Optional;
import org.dmfs.jems2.iterable.EmptyIterable;
import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.Not;
import org.saynotobugs.confidence.quality.object.Satisfies;

import static org.saynotobugs.confidence.Assertion.assertThat;

class LastTest
{
    @Test
    void testEmpty()
    {
        assertThat(new Last<>(new EmptyIterable<>()),
            new Not<>(new Satisfies<>(Optional::isPresent)));
    }

    @Test
    void testSingleElement()
    {
        assertThat(new Last<>(new Seq<>("a")),
            new AllOf<>(
                new Satisfies<>(Optional::isPresent),
                new Has<>(Optional::value, "a")));
    }


    @Test
    void testSeveralElementa()
    {
        assertThat(new Last<>(new Seq<>("a", "b", "c")),
            new AllOf<>(
                new Satisfies<>(Optional::isPresent),
                new Has<>(Optional::value, "c")));
    }

}
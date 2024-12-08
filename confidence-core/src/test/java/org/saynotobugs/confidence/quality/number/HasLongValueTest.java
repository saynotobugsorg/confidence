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

package org.saynotobugs.confidence.quality.number;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class HasLongValueTest
{
    @Test
    void testWithPrimitive()
    {
        assertThat(new HasLongValue(10L),
            new AllOf<>(
                new Passes<Number>(10, "had long value 10l"),
                new Passes<>(10L, "had long value 10l"),
                new Passes<>(10f, "had long value 10l"),
                new Passes<>(10d, "had long value 10l"),
                new Fails<>(11, "had long value 11l"),
                new HasDescription("has long value 10l")
            ));
    }

    @Test
    void testWithQuality()
    {
        assertThat(new HasLongValue(new LessThan<>(11L)),
            new AllOf<>(
                new Passes<Number>(10, "had long value 10l"),
                new Passes<>(10L, "had long value 10l"),
                new Passes<>(10f, "had long value 10l"),
                new Passes<>(10d, "had long value 10l"),
                new Fails<>(11, "had long value 11l"),
                new HasDescription("has long value less than 11l")
            ));
    }

}
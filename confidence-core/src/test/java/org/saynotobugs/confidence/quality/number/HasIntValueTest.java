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

class HasIntValueTest {
    @Test
    void testWithPrimitive()
    {
        assertThat(new HasIntValue(10),
                new AllOf<>(
                        new Passes<Number>(10, "had int value 10"),
                        new Passes<>(10L, "had int value 10"),
                        new Passes<>(10f, "had int value 10"),
                        new Passes<>(10, "had int value 10"),
                        new Passes<>(10d, "had int value 10"),
                        new Fails<>(11, "had int value 11"),
                        new HasDescription("has int value 10")
                ));
    }

    @Test
    void testWithQuality()
    {
        assertThat(new HasIntValue(new LessThan<>(11)),
                new AllOf<>(
                        new Passes<Number>(10, "had int value 10"),
                        new Passes<>(10L, "had int value 10"),
                        new Passes<>(10f, "had int value 10"),
                        new Passes<>(10, "had int value 10"),
                        new Passes<>(10d, "had int value 10"),
                        new Fails<>(11, "had int value 11"),
                        new HasDescription("has int value less than 11")
                ));
    }
}
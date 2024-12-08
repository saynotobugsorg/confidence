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

package org.saynotobugs.confidence.quality.comparator;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.Comparator;

import static java.util.Comparator.naturalOrder;
import static org.saynotobugs.confidence.Assertion.assertThat;


class ImposesOrderOfTest
{

    @Test
    void test()
    {
        assertThat(new ImposesOrderOf<>(1, 2, 3, 4, 5),
            new AllOf<>(
                new Passes<Comparator<Integer>>(naturalOrder(), "imposed the following order: \n" +
                    "  1\n" +
                    "  2\n" +
                    "  3\n" +
                    "  4\n" +
                    "  5"),
                // construct broken comparators
                new Fails<Comparator<Integer>>((l, r) -> l.equals(2) && r.equals(3) ? 1 : l - r,
                    "Comparator\n  ...\n  compared elements 2 at index 1 and 3 at index 2 incorrectly to 1\n  ..."),
                new Fails<Comparator<Integer>>((l, r) -> l.equals(3) && r.equals(2) ? -1 : l - r,
                    "Comparator\n  ...\n  compared elements 3 at index 2 and 2 at index 1 incorrectly to -1\n  ..."),
                new Fails<Comparator<Integer>>((l, r) -> l.equals(2) && r.equals(2) ? 1 : l - r,
                    "Comparator\n  ...\n  compared elements 2 at index 1 and 2 at index 1 incorrectly to 1\n  ..."),
                new Fails<Comparator<Integer>>((l, r) -> l < 2 ? 1 : l - r,
                    "Comparator\n  compared elements 1 at index 0 and 1 at index 0 incorrectly to 1\n  compared elements 1 at index 0 and 2 at index 1 incorrectly to 1\n  compared elements 1 at index 0 and 3 at index 2 incorrectly to 1\n  compared elements 1 at index 0 and 4 at index 3 incorrectly to 1\n  compared elements 1 at index 0 and 5 at index 4 incorrectly to 1\n  ..."),
                new HasDescription("imposes the following order: \n  1\n  2\n  3\n  4\n  5")));
    }

}
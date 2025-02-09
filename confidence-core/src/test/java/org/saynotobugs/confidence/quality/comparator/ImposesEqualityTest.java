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

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.naturalOrder;
import static org.saynotobugs.confidence.Assertion.assertThat;


class ImposesEqualityTest
{
    @Test
    void test()
    {
        assertThat(new ImposesEquality<>("123", "abc", "xyz"),
            new AllOf<>(
                new Passes<>(comparingInt(String::length), "imposed equality on \"123\",\n" +
                    "  \"abc\",\n" +
                    "  \"xyz\""),
                new Fails<Comparator<String>>(naturalOrder(),
                    "Comparator\n" +
                        "  ...\n" +
                        "  compared elements \"123\" at index 0 and \"abc\" at index 1 incorrectly to -48\n" +
                        "  compared elements \"123\" at index 0 and \"xyz\" at index 2 incorrectly to -71\n" +
                        "  compared elements \"abc\" at index 1 and \"123\" at index 0 incorrectly to 48\n" +
                        "  ...\n" +
                        "  compared elements \"abc\" at index 1 and \"xyz\" at index 2 incorrectly to -23\n" +
                        "  compared elements \"xyz\" at index 2 and \"123\" at index 0 incorrectly to 71\n" +
                        "  compared elements \"xyz\" at index 2 and \"abc\" at index 1 incorrectly to 23\n" +
                        "  ..."),
                new HasDescription("imposes equality on \"123\",\n  \"abc\",\n  \"xyz\"")
            ));
    }
}
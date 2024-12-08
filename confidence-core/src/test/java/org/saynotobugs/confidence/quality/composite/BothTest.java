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

package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class BothTest
{
    @Test
    void test()
    {
        assertThat(new Both<>(new LessThan<>(10), new GreaterThan<>(5)),
            new AllOf<>(
                new Passes<>(6, "both,\n" +
                    "  6 and\n" +
                    "  6"),
                new Passes<>(9, "both,\n" +
                    "  9 and\n" +
                    "  9"),
                new Fails<>(10, "both,\n  10 and\n  ..."),
                new Fails<>(3, "both,\n  ... and\n  3"),
                new HasDescription("both,\n  less than 10 and\n  greater than 5")
            ));
    }
}
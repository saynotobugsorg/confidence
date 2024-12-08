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
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class NoneOfTest
{
    @Test
    void test()
    {
        assertThat(new NoneOf<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(0, "was\n" +
                    "  0\n" +
                    "  0\n" +
                    "  0"),
                new Passes<>(4, "was\n" +
                    "  4\n" +
                    "  4\n" +
                    "  4"),
                new Fails<>(1, "was\n  1\n  ..."),
                new Fails<>(2, "was\n  ...\n  2\n  ..."),
                new Fails<>(3, "was\n  ...\n  3"),
                new HasDescription("none of\n  1\n  2\n  3")));
    }


    @Test
    void testMatchers()
    {
        assertThat(new NoneOf<>(new EqualTo<>(1), new EqualTo<>(2), new EqualTo<>(3)),
            new AllOf<>(
                new Passes<>(0, "was\n" +
                    "  0\n" +
                    "  0\n" +
                    "  0"),
                new Passes<>(4, "was\n" +
                    "  4\n" +
                    "  4\n" +
                    "  4"),
                new Fails<>(1, "was\n  1\n  ..."),
                new Fails<>(2, "was\n  ...\n  2\n  ..."),
                new Fails<>(3, "was\n  ...\n  3"),
                new HasDescription("none of\n  1\n  2\n  3")));
    }
}
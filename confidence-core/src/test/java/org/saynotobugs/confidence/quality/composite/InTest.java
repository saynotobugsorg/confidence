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

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static java.util.Arrays.asList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class InTest
{

    @Test
    void testValues()
    {
        assertThat(new In<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(1, "1 in \n" +
                    "  0: 1\n" +
                    "  ..."),
                new Passes<>(2, "2 in \n" +
                    "  ...\n" +
                    "  1: 2\n" +
                    "  ..."),
                new Passes<>(3, "3 in \n" +
                    "  ...\n" +
                    "  2: 3"),
                new Fails<>(4, "4 not in \n  0: 1\n  1: 2\n  2: 3"),
                new Fails<>(0, "0 not in \n  0: 1\n  1: 2\n  2: 3"),
                new HasDescription("in\n  0: 1\n  1: 2\n  2: 3")));
    }


    @Test
    void testCollection()
    {
        assertThat(new In<>(asList(1, 2, 3)),
            new AllOf<>(
                new Passes<>(1, "1 in \n" +
                    "  0: 1\n" +
                    "  ..."),
                new Passes<>(2, "2 in \n" +
                    "  ...\n" +
                    "  1: 2\n" +
                    "  ..."),
                new Passes<>(3, "3 in \n" +
                    "  ...\n" +
                    "  2: 3"),
                new Fails<>(4, "4 not in \n  0: 1\n  1: 2\n  2: 3"),
                new Fails<>(0, "0 not in \n  0: 1\n  1: 2\n  2: 3"),
                new HasDescription("in\n  0: 1\n  1: 2\n  2: 3")));
    }


    @Test
    void testMatchers()
    {
        assertThat(new In<>(new EqualTo<>(1), new EqualTo<>(2), new EqualTo<>(3)),
            new AllOf<>(
                new Passes<>(1, "1 in \n" +
                    "  0: 1\n" +
                    "  ..."),
                new Passes<>(2, "2 in \n" +
                    "  ...\n" +
                    "  1: 2\n" +
                    "  ..."),
                new Passes<>(3, "3 in \n" +
                    "  ...\n" +
                    "  2: 3"),
                new Fails<>(4, "4 not in \n  0: 1\n  1: 2\n  2: 3"),
                new Fails<>(0, "0 not in \n  0: 1\n  1: 2\n  2: 3"),
                new HasDescription("in\n  0: 1\n  1: 2\n  2: 3")));
    }


    @Test
    void testMatcherIterable()
    {
        assertThat(new In<>(new Seq<>(new EqualTo<>(1), new EqualTo<>(2), new EqualTo<>(3))),
            new AllOf<>(
                new Passes<>(1, "1 in \n" +
                    "  0: 1\n" +
                    "  ..."),
                new Passes<>(2, "2 in \n" +
                    "  ...\n" +
                    "  1: 2\n" +
                    "  ..."),
                new Passes<>(3, "3 in \n" +
                    "  ...\n" +
                    "  2: 3"),
                new Fails<>(4, "4 not in \n  0: 1\n  1: 2\n  2: 3"),
                new Fails<>(0, "0 not in \n  0: 1\n  1: 2\n  2: 3"),
                new HasDescription("in\n  0: 1\n  1: 2\n  2: 3")));
    }
}
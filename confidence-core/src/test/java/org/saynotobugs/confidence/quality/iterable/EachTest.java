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

package org.saynotobugs.confidence.quality.iterable;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;
import org.saynotobugs.confidence.test.quality.PassesPostMutation;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class EachTest
{
    @Test
    void testSimpleCtor()
    {
        assertThat(new Each<>(new LessThan<>(3)),
            new AllOf<>(
                new Passes<>(asList(0, 1, 2), "elements [\n" +
                    "  0: 0\n" +
                    "  1: 1\n" +
                    "  2: 2\n" +
                    "]"),
                new Passes<>(asList(0, 0, 0), "elements [\n" +
                    "  0: 0\n" +
                    "  1: 0\n" +
                    "  2: 0\n" +
                    "]"),
                new Passes<List<Integer>>(emptyList(), "elements []"),
                new Fails<>(asList(1, 4, 2), "elements [\n  ...\n  1: 4\n  ...\n]"),
                new HasDescription("each element less than 3")
            ));
    }


    @Test
    void testMultipleCtor()
    {
        assertThat(new Each<>(new LessThan<>(3), new GreaterThan<>(0)),
            new AllOf<>(
                new Passes<Iterable<Integer>>(asList(1, 1, 2), "elements [\n" +
                    "  0: all of\n" +
                    "    0: 1\n" +
                    "    1: 1\n" +
                    "  1: all of\n" +
                    "    0: 1\n" +
                    "    1: 1\n" +
                    "  2: all of\n" +
                    "    0: 2\n" +
                    "    1: 2\n" +
                    "]"),
                new Passes<>(asList(2, 2, 2), "elements [\n" +
                    "  0: all of\n" +
                    "    0: 2\n" +
                    "    1: 2\n" +
                    "  1: all of\n" +
                    "    0: 2\n" +
                    "    1: 2\n" +
                    "  2: all of\n" +
                    "    0: 2\n" +
                    "    1: 2\n" +
                    "]"),
                new Passes<List<Integer>>(emptyList(), "elements []"),
                new Fails<Iterable<Integer>>(asList(0, 4, 2), "elements [\n  0: all of\n    ...\n    1: 0\n  1: all of\n    0: 4\n    ...\n  ...\n]"),
                new Fails<Iterable<Integer>>(asList(1, 4, 2), "elements [\n  ...\n  1: all of\n    0: 4\n    ...\n  ...\n]"),
                new HasDescription("each element all of\n  0: less than 3\n  1: greater than 0")
            ));
    }


    @Test
    void testOneValueWithPostMutation()
    {
        assertThat(new Each<>(new EqualTo<>(1)),
            new PassesPostMutation<>(() -> new ArrayList<>(singletonList(1)), new Text("adding value 2"), list -> list.add(2)));
    }
}
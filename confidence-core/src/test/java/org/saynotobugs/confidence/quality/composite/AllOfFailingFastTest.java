/*
 * Copyright 2022 dmfs GmbH
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;
import org.saynotobugs.confidence.test.quality.PassesPostMutation;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.description.LiteralDescription.COMMA;


class AllOfFailingFastTest
{

    @Test
    void testVarArgs()
    {
        assertThat(new AllOfFailingFast<>(new LessThan<>(10), new LessThan<>(5), new LessThan<>(3)),
            new AllOf<>(
                new Passes<>(0, ""),
                new Passes<>(1, ""),
                new Passes<>(2, ""),
                new Fails<>(3, "all of\n  ...\n  2: 3"),
                new Fails<>(5, "all of\n  ...\n  1: 5"),
                new Fails<>(10, "all of\n  0: 10"),
                new HasDescription("all of\n  0: less than 10\n  1: less than 5\n  2: less than 3")
            ));
    }


    @Test
    void testVarArgsWithDelimiter()
    {
        assertThat(new AllOfFailingFast<>(COMMA, new LessThan<>(10), new LessThan<>(5), new LessThan<>(3)),
            new AllOf<>(
                new Passes<>(0, ""),
                new Passes<>(1, ""),
                new Passes<>(2, ""),
                new Fails<>(3, "all of\n  ...,\n  2: 3"),
                new Fails<>(5, "all of\n  ...,\n  1: 5"),
                new Fails<>(10, "all of\n  0: 10"),
                new HasDescription("all of\n  0: less than 10,\n  1: less than 5,\n  2: less than 3")
            ));
    }


    @Test
    void testPostMutation()
    {
        assertThat(new AllOfFailingFast<>(new Contains<>(1), new Contains<>(2)),
            new PassesPostMutation<>(() -> new ArrayList<>(asList(1, 2)), new Text("clear list"), List::clear));
    }
}
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
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.Nothing;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.Passes;
import org.saynotobugs.confidence.test.quality.PassesPostMutation;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class AllOfTest
{
    @Test
    void testMatch()
    {
        // TODO, the "empty" case should probably be impossible
        assertThat(new AllOf<>(), new Passes<>(123, "all of"));
        assertThat(new AllOf<>(new Anything()), new Passes<>(123, "all of\n  0: 123"));
        assertThat(new AllOf<>(new Anything(), new Anything()), new Passes<>(123, "all of\n  0: 123\n  1: 123"));
    }


    @Test
    void testMismatch()
    {
        assertThat(new AllOf<>(new Nothing()), new Fails<>(123, "all of\n  0: 123"));
        assertThat(new AllOf<>(new Anything(), new Nothing()), new Fails<>(123, "all of\n  ...\n  1: 123"));
        assertThat(new AllOf<>(new Nothing(), new Anything(), new Anything()), new Fails<>(123, "all of\n  0: 123\n  ..."));
    }


    @Test
    void testPostMutation()
    {
        assertThat(new AllOf<>(new Contains<>(1), new Contains<>(2)),
            new PassesPostMutation<>(() -> new ArrayList<>(asList(1, 2)), new Text("clear list"), List::clear));
    }
}
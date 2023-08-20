/*
 * Copyright 2023 dmfs GmbH
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

package org.saynotobugs.confidence.quality.function;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.grammar.SoIt;
import org.saynotobugs.confidence.quality.grammar.To;
import org.saynotobugs.confidence.quality.grammar.When;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.ArrayList;

import static org.saynotobugs.confidence.Assertion.assertThat;

class MutatesArgumentTest
{
    @Test
    void test()
    {
        assertThat(new MutatesArgument<>(
                ArrayList::new,
                new SoIt<>(new Contains<>("a")),
                new When<>(new Maps<>("a", new To<>(true)))),
            new AllOf<>(
                new Passes<>(list -> list::add),
                new Fails<>(list -> list::remove, "but [  ] did not contain { \"a\" } when mapped \"a\" to <false>"),
                new HasDescription("mutates argument [  ] so it contains { \"a\" } when maps \"a\" to <true>")
            ));
    }

}
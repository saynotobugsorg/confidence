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

package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.consumer.ConsumerThatAffects;
import org.saynotobugs.confidence.quality.consumer.ConsumerThatAccepts;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AffectsTest
{

    @Test
    void test()
    {
        assertThat(new Affects<>(
                ArrayList::new,
                new ConsumerThatAccepts<>("a"),
                new Contains<>("a")),
            new AllOf<>(
                new Passes<Function<List<String>, Consumer<String>>>(l -> l::add),
                new Fails<Function<List<String>, Consumer<String>>>(l -> l::remove,
                    "...\n  [  ] did not contain { \"a\" }"),
                new HasDescription("Consumer that accepts \"a\" affects [  ] so it contains { \"a\" }")
            ));
    }


    @Test
    void testDelegateFails()
    {
        assertThat(new Affects<>(
                ArrayList::new,
                new ConsumerThatAffects<>(new TextDescription("accepts a"), () -> "a", new Is<>(new EqualTo<>("b"))),
                new Contains<>("a")),
            new AllOf<>(
                new Fails<Function<List<String>, Consumer<String>>>(l -> l::add,
                    "Consumer that accepts a was \"a\"\n  ..."),
                new HasDescription("Consumer that accepts a is \"b\" affects [  ] so it contains { \"a\" }")
            ));
    }


    @Test
    void testBothFail()
    {
        assertThat(new Affects<>(
                ArrayList::new,
                new ConsumerThatAccepts<>("a"),
                new Contains<>("a")),
            new AllOf<>(
                new Fails<Function<List<String>, Consumer<String>>>(l -> x -> {throw new RuntimeException();},
                    "Consumer that accepts \"a\" threw <java.lang.RuntimeException> and\n  [  ] did not contain { \"a\" }"),
                new HasDescription("Consumer that accepts \"a\" affects [  ] so it contains { \"a\" }")
            ));
    }

}
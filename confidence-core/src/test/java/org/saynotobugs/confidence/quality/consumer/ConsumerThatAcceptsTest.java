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

package org.saynotobugs.confidence.quality.consumer;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.grammar.SoIt;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.ArrayList;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ConsumerThatAcceptsTest
{

    @Test
    void testDirectValue()
    {
        assertThat(new ConsumerThatAccepts<>("a", new SoIt<>(new Affects<>(ArrayList::new, new SoIt<>(new Contains<>("a"))))),
            new AllOf<>(
                new Passes<>(x -> (ArrayList<String> list) -> list.add(x)),
                new HasDescription("Consumer that accepts \"a\" so it affects [  ] so it contains { \"a\" }")
            ));
    }
}
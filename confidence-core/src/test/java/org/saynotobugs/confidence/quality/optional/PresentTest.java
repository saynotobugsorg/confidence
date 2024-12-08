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

package org.saynotobugs.confidence.quality.optional;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.Optional;

import static org.saynotobugs.confidence.Assertion.assertThat;


class PresentTest
{

    @Test
    void testDefault()
    {
        assertThat(new Present<>(),
            new AllOf<>(
                new Passes<Optional<Object>>(Optional.of(123), "<present 123>"),
                new Passes<Optional<Object>>(Optional.of(1234), "<present 1234>"),
                new Passes<Optional<Object>>(Optional.of("abc"), "<present \"abc\">"),
                new Fails<>(Optional.empty(), "<empty>"),
                new HasDescription("<present <anything>>")));
    }


    @Test
    void testWithValue()
    {
        assertThat(new Present<>(123),
            new AllOf<>(
                new Passes<>(Optional.of(123), "<present 123>"),
                new Fails<>(Optional.of(1234), "<present 1234>"),
                new Fails<Optional<Integer>>(Optional.empty(), "<empty>"),
                new HasDescription("<present 123>")));
    }


    @Test
    void testWithQuality()
    {
        assertThat(new Present<>(new EqualTo<>(123)),
            new AllOf<>(
                new Passes<>(Optional.of(123), "<present 123>"),
                new Fails<>(Optional.of(1234), "<present 1234>"),
                new Fails<Optional<Integer>>(Optional.empty(), "<empty>"),
                new HasDescription("<present 123>")));
    }

}
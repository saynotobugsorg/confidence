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

package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.NoSuchElementException;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ThrowingTest
{

    @Test
    void test()
    {
        assertThat(new Throwing(new Anything()),
            new AllOf<>(
                new Passes<>((Throwing.Breakable) () -> {
                    throw new NoSuchElementException();
                }, "throwing <java.util.NoSuchElementException>"),
                new Fails<>(() -> {}, "not throwing <anything>"),
                new HasDescription("throwing <anything>")
            ));
    }


    @Test
    void testNothing()
    {
        assertThat(new Throwing(new Nothing()),
            new AllOf<>(
                new Fails<>(() -> {throw new NoSuchElementException();}, "throwing <java.util.NoSuchElementException>"),
                new Fails<>(() -> {}, "not throwing <nothing>"),
                new HasDescription("throwing <nothing>")
            ));
    }


    @Test
    void testClass()
    {
        assertThat(new Throwing(NoSuchElementException.class),
            new AllOf<>(
                new Passes<>((Throwing.Breakable) () -> {
                    throw new NoSuchElementException();
                }, "throwing instance of <class java.util.NoSuchElementException>"),
                new Fails<>(() -> {}, "not throwing instance of <class java.util.NoSuchElementException>"),
                new HasDescription("throwing instance of <class java.util.NoSuchElementException>")
            ));
    }
}
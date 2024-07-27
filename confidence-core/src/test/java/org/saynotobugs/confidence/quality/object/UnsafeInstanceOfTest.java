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

package org.saynotobugs.confidence.quality.object;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.That;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class UnsafeInstanceOfTest
{

    @Test
    void testDelegate()
    {
        assertThat(new UnsafeInstanceOf<>(Number.class, new That<>(new Has<>("intValue", Number::intValue, new EqualTo<>(1)))),
            new AllOf<>(
                new Passes<>(1, 1.001, 1L, 1f),
                new Fails<>(0.999, "(1) that had intValue <0>"),
                new Fails<>(2, "(1) that had intValue <2>"),
                new Fails<>("string", "(0) instance of <class java.lang.String>"),
                new Fails<>(new Object(), "(0) instance of <class java.lang.Object>"),
                new HasDescription("(0) instance of <class java.lang.Number>\n  (1) that has intValue <1>")
            ));
    }

    @Test
    void testSubClassDelegate()
    {
        assertThat(new UnsafeInstanceOf<>(Iterable.class, new That<>(new Iterates<>(1, "abc", true))),
            new AllOf<>(
                new Passes<>(new Seq<Object>(1, "abc", true), new Seq(1, "abc", true)),
                new Fails<>(new Seq<Object>(1.1, "abc", true), "(1) that iterated [ 0: <1.1d>\n  ... ]"),
                new Fails<>(2, "(0) instance of <class java.lang.Integer>"),
                new Fails<>("string", "(0) instance of <class java.lang.String>"),
                new Fails<>(new Object(), "(0) instance of <class java.lang.Object>"),
                new HasDescription("(0) instance of <interface java.lang.Iterable>\n  (1) that iterates [ 0: <1>,\n    1: \"abc\",\n    2: <true> ]")
            ));
    }
}
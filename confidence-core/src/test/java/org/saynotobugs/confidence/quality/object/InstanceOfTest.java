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
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.That;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class InstanceOfTest
{
    @Test
    void testNoDelegate()
    {
        assertThat(new InstanceOf<>(Number.class),
            new AllOf<>(
                new Passes<>(1, "instance of <class java.lang.Integer>"),
                new Passes<>(1L, "instance of <class java.lang.Long>"),
                new Passes<>(1f, "instance of <class java.lang.Float>"),
                new Fails<>("string", "instance of <class java.lang.String>"),
                new Fails<>(new Object(), "instance of <class java.lang.Object>"),
                new HasDescription("instance of <class java.lang.Number>")
            ));
    }

    @Test
    void testDelegate()
    {
        assertThat(new InstanceOf<>(Number.class, new That<>(new Has<>("intValue", Number::intValue, new EqualTo<>(1)))),
            new AllOf<>(
                new Passes<>(1, "all of\n" +
                    "  0: instance of <class java.lang.Integer>\n" +
                    "  1: that had intValue 1"),
                new Passes<>(1.001, "all of\n" +
                    "  0: instance of <class java.lang.Double>\n" +
                    "  1: that had intValue 1"),
                new Passes<>(1L, "all of\n" +
                    "  0: instance of <class java.lang.Long>\n" +
                    "  1: that had intValue 1"),
                new Passes<>(1f, "all of\n" +
                    "  0: instance of <class java.lang.Float>\n" +
                    "  1: that had intValue 1"),
                new Fails<>(0.999, "all of\n  ...\n  1: that had intValue 0"),
                new Fails<>(2, "all of\n  ...\n  1: that had intValue 2"),
                new Fails<>("string", "all of\n  0: instance of <class java.lang.String>"),
                new Fails<>(new Object(), "all of\n  0: instance of <class java.lang.Object>"),
                new HasDescription("all of\n  0: instance of <class java.lang.Number>\n  1: that has intValue 1")
            ));
    }
}
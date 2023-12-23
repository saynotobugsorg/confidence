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

package org.saynotobugs.confidence.asm.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.asm.quality.testclasses.AnnotatedTestClass;
import org.saynotobugs.confidence.asm.quality.testclasses.TestAnnotation;
import org.saynotobugs.confidence.quality.annotation.Annotation;
import org.saynotobugs.confidence.quality.array.ArrayThat;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.quality.iterable.Iterates;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.asm.quality.testclasses.TestEnum.*;

class ExampleTests
{
    @Test
    void test()
    {
        assertThat(AnnotatedTestClass.class,
            new Is<>(
                new ClassThat(new HasDeclaredAnnotations(
                    new Contains<>(
                        new Annotation(TestAnnotation.class,
                            new Has<>(TestAnnotation::defaultString, "def"),
                            new Has<>(TestAnnotation::stringValue, "stringvalue"),
                            new Has<>(TestAnnotation::defaultStringChanged, "changed"),
                            new Has<>(TestAnnotation::stringArray, new String[] { "223" }),
                            new Has<>(TestAnnotation::defaultInt, 1234),
                            new Has<>(TestAnnotation::intValue, 12),
                            new Has<>(TestAnnotation::intArray, new ArrayThat(new Iterates<>(1, 2))),
                            new Has<>(TestAnnotation::boolValue, true),
                            new Has<>(TestAnnotation::boolArray, new ArrayThat(new Iterates<>(true, false, true))),
                            new Has<>(TestAnnotation::classValue, String.class),
                            new Has<>(TestAnnotation::classArray, new ArrayThat(new Iterates<>(String.class, Number.class, Object.class))),
                            new Has<>(TestAnnotation::enumValue, E2),
                            new Has<>(TestAnnotation::enumArray, new ArrayThat(new Iterates<>(E1, E3))),
                            new Has<>(TestAnnotation::enumDefault, E3),
                            new Has<>(TestAnnotation::annotationValue, new Annotation(Deprecated.class,
                                new Has<>(Deprecated::since, "123"))),
                            new Has<>(TestAnnotation::annotationArray, new ArrayThat(new Iterates<>(
                                new Annotation(FunctionalInterface.class), new Annotation(FunctionalInterface.class)
                            )))))))));
    }
}
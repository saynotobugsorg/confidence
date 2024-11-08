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
import org.saynotobugs.confidence.asm.quality.testclasses.FunctionalTestClass;
import org.saynotobugs.confidence.asm.quality.testclasses.TestAnnotation;
import org.saynotobugs.confidence.asm.quality.testclasses.TestClassWithoutAnnotation;
import org.saynotobugs.confidence.quality.annotation.Annotation;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.quality.object.HasToString;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.lang.Class;

import static org.saynotobugs.confidence.Assertion.assertThat;

class HasDeclaredAnnotationsTest
{
    @Test
    void test()
    {
        assertThat(new ClassThat(new HasDeclaredAnnotations(new Contains<>(new Annotation(TestAnnotation.class)))),
            new AllOf<>(
                new Passes<Class<?>>(AnnotatedTestClass.class),
                new Fails<>(TestClassWithoutAnnotation.class, "Class that had annotations [] did not contain instance of <interface org.saynotobugs.confidence.asm.quality.testclasses.TestAnnotation>"),
                new HasDescription("Class that has annotations contains instance of <interface org.saynotobugs.confidence.asm.quality.testclasses.TestAnnotation>")
            ));
    }

    @Test
    void testWithDelegate()
    {
        assertThat(new ClassThat(new HasDeclaredAnnotations(new Contains<>(new Annotation(TestAnnotation.class,
                new Has<>(TestAnnotation::defaultString, "def"))))),
            new AllOf<>(
                new Passes<Class<?>>(AnnotatedTestClass.class),
                new Fails<>(TestClassWithoutAnnotation.class, "Class that had annotations [] did not contain (0) instance of <interface org.saynotobugs.confidence.asm.quality.testclasses.TestAnnotation>\n  (1) \"def\""),
                new HasDescription("Class that has annotations contains (0) instance of <interface org.saynotobugs.confidence.asm.quality.testclasses.TestAnnotation>\n  (1) \"def\"")
            ));
    }


    @Test
    void testToString()
    {
        assertThat(new ClassThat(new HasDeclaredAnnotations(new Contains<>(new Annotation(FunctionalInterface.class,
                new HasToString(FunctionalInterface.class.getCanonicalName()))))),
            new AllOf<>(
                new Passes<Class<?>>(FunctionalTestClass.class),
                new Fails<>(TestClassWithoutAnnotation.class, "Class that had annotations [] did not contain (0) instance of <interface java.lang.FunctionalInterface>\n  (1) has toString() \"java.lang.FunctionalInterface\""),
                new HasDescription("Class that has annotations contains (0) instance of <interface java.lang.FunctionalInterface>\n  (1) has toString() \"java.lang.FunctionalInterface\"")
            ));
    }
}
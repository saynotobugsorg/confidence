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
import org.saynotobugs.confidence.asm.ClassAdapter;
import org.saynotobugs.confidence.asm.quality.testclasses.AnnotatedTestClass;
import org.saynotobugs.confidence.asm.quality.testclasses.TestClassWithoutAnnotation;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.quality.object.InstanceOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.lang.Class;
import java.lang.annotation.Annotation;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ClassThatTest
{
    @Test
    void test()
    {
        assertThat(new ClassThat(new Has<>("annotations", ClassAdapter::declaredAnnotations, new Iterates<>(new InstanceOf<>(Annotation.class)))),
            new AllOf<>(
                new Passes<Class<?>>(AnnotatedTestClass.class),
                new Fails<>(TestClassWithoutAnnotation.class, "Class that had annotations iterated [ 0: missing instance of <interface java.lang.annotation.Annotation> ]"),
                new HasDescription("Class that has annotations iterates [ 0: instance of <interface java.lang.annotation.Annotation> ]")
            ));
    }

}
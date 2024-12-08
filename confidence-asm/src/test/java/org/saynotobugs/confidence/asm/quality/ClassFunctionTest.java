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

package org.saynotobugs.confidence.asm.quality;

import org.dmfs.jems2.confidence.Jems2;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.Type;
import org.saynotobugs.confidence.asm.quality.testclasses.AnnotatedTestClass;
import org.saynotobugs.confidence.asm.quality.testclasses.TestClassWithoutAnnotation;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.grammar.To;
import org.saynotobugs.confidence.quality.object.InstanceOf;

import java.lang.Class;

import static org.dmfs.jems2.confidence.Jems2.maps;
import static org.dmfs.jems2.confidence.Jems2.throwing;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ClassFunctionTest
{
    @Test
    void test()
    {
        assertThat(new ClassFunction(),
            new AllOf<>(
                Jems2.<Type, Class<?>>maps(Type.getType(AnnotatedTestClass.class),
                    new To<>(AnnotatedTestClass.class)),
                maps(Type.getType(TestClassWithoutAnnotation.class),
                    new To<>(TestClassWithoutAnnotation.class)),
                new Has<>(f -> () -> f.value(Type.getType("Lfake;")), throwing(
                    new InstanceOf<>(RuntimeException.class, new Has<>(Exception::getMessage, new Is<>("Can't load class fake")))))
            ));
    }

}
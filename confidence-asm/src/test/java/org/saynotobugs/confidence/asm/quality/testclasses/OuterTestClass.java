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

package org.saynotobugs.confidence.asm.quality.testclasses;

public final class OuterTestClass
{
    String value()
    {
        return "foo";
    }


    @TestAnnotation(stringValue = "stringvalue",
        defaultStringChanged = "changed",
        stringArray = "223",
        intValue = 12,
        intArray = { 1, 2 },
        boolValue = true,
        boolArray = { true, false, true },
        annotationValue = @Deprecated(since = "123"),
        annotationArray = { @FunctionalInterface, @FunctionalInterface },
        classValue = String.class,
        classArray = { String.class, Number.class, Object.class },
        enumValue = TestEnum.E2,
        enumArray = { TestEnum.E1, TestEnum.E3 })
    public static class AnnotatedInnerTestClass
    {
        String value() {return "bar";}
    }
}

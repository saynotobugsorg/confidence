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

package org.saynotobugs.confidence.quality.annotation;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class AnnotationTest
{
    @interface TestAnnotation
    {
        String value1();

        String value2();
    }


    @Test
    void testNoDelegate()
    {
        assertThat(new Annotation(TestAnnotation.class),
            new AllOf<>(
                new Passes<java.lang.annotation.Annotation>(new TestAnnotation()
                {
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType()
                    {
                        return TestAnnotation.class;
                    }

                    @Override
                    public String value1()
                    {
                        return "value";
                    }

                    @Override
                    public String value2()
                    {
                        return "123";
                    }
                }, "instance of <class org.saynotobugs.confidence.quality.annotation.AnnotationTest$1>"),
                new Fails<>(new FunctionalInterface()
                {
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType()
                    {
                        return FunctionalInterface.class;
                    }
                },
                    "instance of <class org.saynotobugs.confidence.quality.annotation.AnnotationTest$2>"),
                new HasDescription("instance of <interface org.saynotobugs.confidence.quality.annotation.AnnotationTest$TestAnnotation>")));
    }


    @Test
    void testWithDelegate()
    {
        assertThat(new Annotation(TestAnnotation.class, new Has<>(TestAnnotation::value1, "abc")),
            new AllOf<>(
                new Passes<java.lang.annotation.Annotation>(new TestAnnotation()
                {
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType()
                    {
                        return TestAnnotation.class;
                    }

                    @Override
                    public String value1()
                    {
                        return "abc";
                    }

                    @Override
                    public String value2()
                    {
                        return "123";
                    }
                }, "all of\n" +
                    "  0: instance of <class org.saynotobugs.confidence.quality.annotation.AnnotationTest$3>\n" +
                    "  1: \"abc\""),
                new Fails<>(new TestAnnotation()
                {
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType()
                    {
                        return TestAnnotation.class;
                    }

                    @Override
                    public String value1()
                    {
                        return "value";
                    }


                    @Override
                    public String value2()
                    {
                        return "123";
                    }
                },
                    "all of\n  ...\n  1: \"value\""),
                new Fails<>(new FunctionalInterface()
                {
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType()
                    {
                        return FunctionalInterface.class;
                    }
                },
                    "all of\n  0: instance of <class org.saynotobugs.confidence.quality.annotation.AnnotationTest$5>"),
                new HasDescription("all of\n  0: instance of <interface org.saynotobugs.confidence.quality.annotation.AnnotationTest$TestAnnotation>\n  1: \"abc\"")));
    }


    @Test
    void testWithMultipleDelegates()
    {
        assertThat(new Annotation(TestAnnotation.class,
                new Has<>(TestAnnotation::value1, "abc"),
                new Has<>(TestAnnotation::value2, "123")),
            new AllOf<>(
                new Passes<java.lang.annotation.Annotation>(new TestAnnotation()
                {
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType()
                    {
                        return TestAnnotation.class;
                    }

                    @Override
                    public String value1()
                    {
                        return "abc";
                    }

                    @Override
                    public String value2()
                    {
                        return "123";
                    }
                }, "all of\n" +
                    "  0: instance of <class org.saynotobugs.confidence.quality.annotation.AnnotationTest$6>\n" +
                    "  1: all of\n" +
                    "    0: \"abc\"\n" +
                    "    1: \"123\""),
                new Fails<>(new TestAnnotation()
                {
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType()
                    {
                        return TestAnnotation.class;
                    }

                    @Override
                    public String value1()
                    {
                        return "value";
                    }

                    @Override
                    public String value2()
                    {
                        return "123";
                    }
                },
                    "all of\n  ...\n  1: all of\n    0: \"value\"\n    ..."),
                new Fails<>(new TestAnnotation()
                {
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType()
                    {
                        return TestAnnotation.class;
                    }

                    @Override
                    public String value1()
                    {
                        return "abc";
                    }

                    @Override
                    public String value2()
                    {
                        return "xyz";
                    }
                },
                    "all of\n  ...\n  1: all of\n    ...\n    1: \"xyz\""),
                new Fails<>(new FunctionalInterface()
                {
                    @Override
                    public Class<? extends java.lang.annotation.Annotation> annotationType()
                    {
                        return FunctionalInterface.class;
                    }
                },
                    "all of\n  0: instance of <class org.saynotobugs.confidence.quality.annotation.AnnotationTest$9>"),
                new HasDescription("all of\n  0: instance of <interface org.saynotobugs.confidence.quality.annotation.AnnotationTest$TestAnnotation>\n  1: all of\n    0: \"abc\"\n    1: \"123\"")));
    }
}
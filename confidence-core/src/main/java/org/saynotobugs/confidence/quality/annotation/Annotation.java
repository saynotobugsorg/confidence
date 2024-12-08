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

package org.saynotobugs.confidence.quality.annotation;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.InstanceOf;

/**
 * Quality of an {@link java.lang.annotation.Annotation}.
 */
@StaticFactories(
    value = "Annotation",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class Annotation extends QualityComposition<java.lang.annotation.Annotation>
{
    @SafeVarargs
    public <T extends java.lang.annotation.Annotation> Annotation(Class<T> annotationClass,
        Quality<? super T>... delegates)
    {
        this(annotationClass, new AllOf<>(delegates));
    }

    public <T extends java.lang.annotation.Annotation> Annotation(Class<T> annotationClass,
        Quality<? super T> delegate)
    {
        super(new InstanceOf<>(annotationClass, delegate));
    }

    public Annotation(Class<? extends java.lang.annotation.Annotation> annotationClass)
    {
        super(new InstanceOf<>(annotationClass));
    }
}

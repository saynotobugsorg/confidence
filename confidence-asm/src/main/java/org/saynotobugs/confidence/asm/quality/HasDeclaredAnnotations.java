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

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.asm.ClassAdapter;
import org.saynotobugs.confidence.quality.annotation.Annotation;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import java.lang.annotation.RetentionPolicy;

/**
 * {@link Quality} of a {@link java.lang.Class} that's annotated with a specific {@link RetentionPolicy#CLASS} or
 * {@link RetentionPolicy#RUNTIME} scoped {@link Annotation}.
 */
@StaticFactories(value = "Class",
    packageName = "org.saynotobugs.confidence.asm.quality",
    deprecates = @DeprecatedFactories(value = "Asm", packageName = "org.saynotobugs.confidence.asm"))
public final class HasDeclaredAnnotations extends QualityComposition<ClassAdapter>
{
    public HasDeclaredAnnotations(Quality<? super Iterable<java.lang.annotation.Annotation>> delegate)
    {
        super(new Has<>("annotations", ClassAdapter::declaredAnnotations, delegate));
    }
}

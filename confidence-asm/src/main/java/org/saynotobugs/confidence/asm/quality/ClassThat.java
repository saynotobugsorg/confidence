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

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.objectweb.asm.ClassReader;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.asm.AnnotationAdapter;
import org.saynotobugs.confidence.asm.ClassAdapter;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import java.lang.Class;
import java.util.ArrayList;
import java.util.Collection;

/**
 * {@link Quality} of a class read from a class file.
 */
@StaticFactories(value = "Class",
    packageName = "org.saynotobugs.confidence.asm.quality",
    deprecates = @DeprecatedFactories(value = "Asm", packageName = "org.saynotobugs.confidence.asm"))
public final class ClassThat extends QualityComposition<Class<?>>
{
    public ClassThat(Quality<? super ClassAdapter> delegate)
    {
        super(new Has<>(
            new Text("Class that"),
            new Text("Class that"),
            candidate -> {
                ClassReader classReader = new ClassReader(candidate.getCanonicalName());
                return (ClassAdapter) () -> {
                    Collection<AnnotationAdapter> adapters = new ArrayList<>();
                    classReader.accept(new AnnotationClassVisitor(adapters), 0);
                    return new Mapped<>(AnnotationAdapter::annotation, adapters);
                };
            }, delegate));
    }
}

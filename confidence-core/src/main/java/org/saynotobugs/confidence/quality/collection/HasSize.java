/*
 * Copyright 2022 dmfs GmbH
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

package org.saynotobugs.confidence.quality.collection;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.Collection;


@StaticFactories(
    value = "Collection",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class HasSize extends QualityComposition<Collection<?>>
{
    /**
     * Creates a {@link Quality} that matches if size of the {@link Collection} under test is equal to the given value.
     * <pre>
     *     assertThat(asList(1,2,3), hasSize(3));
     * </pre>
     */
    public HasSize(int size)
    {
        this(new EqualTo<>(size));
    }


    /**
     * Creates a {@link Quality} that matches if the size {@link Collection} under test matches the given {@link Quality}.
     * <pre>
     *     assertThat(asList(1,2,3), hasSize(lessThan(4)));
     * </pre>
     */
    public HasSize(Quality<? super Integer> delegate)
    {
        super(new Has<>("size", Collection::size, delegate));
    }
}

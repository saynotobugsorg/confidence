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

package org.saynotobugs.confidence.quality.charsequence;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class HasLength extends QualityComposition<CharSequence>
{
    /**
     * Creates a {@link Quality} that matches if length of the {@link CharSequence} under test is equal to the given value.
     * <pre>
     *     assertThat("123", hasLength(3));
     * </pre>
     */
    public HasLength(int size)
    {
        this(new EqualTo<>(size));
    }


    /**
     * Creates a {@link Quality} that matches if the length {@link CharSequence} under test matches the given {@link Quality}.
     * <pre>
     *     assertThat("123", hasLength(lessThan(4)));
     * </pre>
     */
    public HasLength(Quality<? super Integer> delegate)
    {
        super(new Has<>("length", CharSequence::length, delegate));
    }
}

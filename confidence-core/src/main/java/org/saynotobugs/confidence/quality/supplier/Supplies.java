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

package org.saynotobugs.confidence.quality.supplier;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.function.Supplier;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class Supplies<T> extends QualityComposition<Supplier<T>>
{
    /**
     * Creates a {@link Quality} that passes if the {@link Supplier} under test supplies a value equal to the given value.
     */
    public Supplies(T delegate)
    {
        this(new EqualTo<>(delegate));
    }


    /**
     * Creates a {@link Quality} that passes if the {@link Supplier} under test supplies a value that matches the given {@link Quality}.
     */
    public Supplies(Quality<? super T> delegate)
    {
        super(new Has<>(new TextDescription("supplies"), new TextDescription("supplied"), Supplier::get, delegate));
    }
}

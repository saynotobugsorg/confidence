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

package org.saynotobugs.confidence.quality.array;

import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.Guarded;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Satisfies;
import org.saynotobugs.confidence.utils.ArrayIterable;


@StaticFactories(
    value = "Array",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class ArrayThat extends QualityComposition<Object>
{
    /**
     * A {@link Quality} of an array that, when iterated, has the given {@link Iterable} {@link Quality}.
     */
    @SuppressWarnings("unchecked")
    public <T> ArrayThat(Quality<? super Iterable<T>> delegate)
    {
        super(
            new Guarded<>(
                new Seq<>(new Satisfies<>(a -> a.getClass().isArray(), actual -> new Spaced(new Value(actual), new Text("was not an array")), new Text("an array"))),
                new Has<>(new Text("array that"), new Text("array"), a -> (Iterable<T>) new ArrayIterable(a), delegate)));
    }
}

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

package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.jems2.single.Reduced;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;


@StaticFactories(
    value = "Iterable",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class HasNumberOfElements extends QualityComposition<Iterable<?>>
{
    /**
     * Creates a {@link Quality} that matches if the {@link Iterable} under test iterates a number of elements equal to the given value.
     * <pre>
     *     assertThat(asList(1,2,3), hasNumberOfElements(3));
     * </pre>
     */
    public HasNumberOfElements(int size)
    {
        this(new EqualTo<>(size));
    }


    /**
     * Creates a {@link Quality} that matches if the {@link Iterable} under test iterates a number of elements that matches the given {@link Quality}.
     * <pre>
     *     assertThat(asList(1,2,3), hasNumberOfElements(lessThan(4)));
     * </pre>
     */
    public HasNumberOfElements(Quality<? super Integer> delegate)
    {
        super(new Has<>(
            (Description d) -> new Spaced(new Text("has"), d, new Text("elements")),
            d -> new Spaced(new Text("had"), d, new Text("elements")),
            actual -> new Reduced<>(() -> 0, (current, i) -> current + 1, actual).value(), delegate));
    }
}

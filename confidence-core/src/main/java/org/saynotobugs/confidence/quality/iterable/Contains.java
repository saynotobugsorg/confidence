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

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.single.Backed;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactory;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.Just;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.utils.Last;
import org.saynotobugs.confidence.utils.Until;


@StaticFactories(
    value = "Iterable",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class Contains<T> extends QualityComposition<Iterable<T>>
{
    /**
     * A {@link Quality} that checks if the {@link Iterable} under test contains the given element.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bar", "baz"), contains("foo"));
     * </pre>
     */
    public Contains(T value)
    {
        this(new EqualTo<>(value));
    }

    /**
     * A {@link Quality} that checks if the {@link Iterable} under test contains an element that satisfies
     * the given {@link Quality}.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bar", "baz"), contains(equalTo("foo")));
     * </pre>
     */
    public Contains(Quality<? super T> quality)
    {
        super(actual ->
                new Backed<>(new Last<>(new Until<>(Assessment::isSuccess, new Mapped<>(element -> new DescriptionUpdated(
                    new TextAndOriginal<>("contained"),
                    new Just<>(new Spaced(new Value(actual), new Text("did not contain"), quality.description())),
                    quality.assessmentOf(element)), actual))),
                    () -> new Fail(new Text("did not contain any elements"))).value(),
            new Spaced(new Text("contains"), quality.description()));
    }

    /**
     * Creates a {@link Quality} that, for each given values, checks if the {@link Iterable} under test contains at
     * least one element that equals that value.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bar", "baz"), contains("foo", "bar"));
     * </pre>
     *
     * @deprecated use {@link org.saynotobugs.confidence.quality.Core#containsAllOf(Object[])}
     */
    @StaticFactory(value = "Core", packageName = "org.saynotobugs.confidence.quality")
    @Deprecated
    @SafeVarargs
    public Contains(T... values)
    {
        super(new ContainsAllOf<>(values));
    }


    /**
     * @deprecated use {@link org.saynotobugs.confidence.quality.Core#containsAllOf(Quality[])}
     */
    @StaticFactory(value = "Core", packageName = "org.saynotobugs.confidence.quality")
    @Deprecated
    @SafeVarargs
    public Contains(Quality<? super T>... delegates)
    {
        super(new ContainsAllOf<>(delegates));
    }


    /**
     * @deprecated use {@link org.saynotobugs.confidence.quality.Core#containsAllOf(Iterable)}
     */
    @StaticFactory(value = "Core", packageName = "org.saynotobugs.confidence.quality")
    @Deprecated
    public Contains(Iterable<? extends Quality<? super T>> delegates)
    {
        super(new ContainsAllOf<>(delegates));
    }
}

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

package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.quality.composite.NoneOf;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;


@StaticFactories(
    value = "Iterable",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class ContainsNoneOf<T> extends QualityComposition<Iterable<T>>
{
    /**
     * A {@link Quality} that, for each given value, checks if the {@link Iterable} under test contains no element that
     * equals that value.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bar", "baz"), containsNoneOf("bazz", "foobar"));
     * </pre>
     * <p>
     * This is merely a shortcut for {@code new Each<>(new NoneOf<T>(new Seq<>(new Mapped<>(EqualTo::new, new Seq<>(values)))))}.
     */
    @SafeVarargs
    public ContainsNoneOf(T... values)
    {
        this(new Mapped<>(EqualTo::new, new Seq<>(values)));
    }


    /**
     * A {@link Quality} that, for each given {@link Quality}, checks if the {@link Iterable} under test
     * contains at least one element that satisfies that {@link Quality}.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bar", "foobar"), containsNoneOf(equalTo("bazz"), hasLength(5)));
     * </pre>
     * This is merely a shortcut for {@code new Each<>(new NoneOf<T>(new Seq<>(delegates)))}.
     */
    @SafeVarargs
    public ContainsNoneOf(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    /**
     * A {@link Quality} of an {@link Iterable} that contains no element that satisfies any of the given delegates.
     * <p>
     * This is merely a shortcut for {@code new Each<>(new NoneOf<T>(delegates))}.
     */
    public ContainsNoneOf(Iterable<? extends Quality<? super T>> delegates)
    {
        super(new Each<>(new NoneOf<T>(delegates)));
    }
}

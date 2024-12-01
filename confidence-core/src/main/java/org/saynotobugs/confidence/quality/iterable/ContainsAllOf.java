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
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.single.Collected;
import org.dmfs.jems2.single.Reduced;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Structured;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.ArrayList;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA_NEW_LINE;


@StaticFactories(
    value = "Iterable",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class ContainsAllOf<T> extends QualityComposition<Iterable<T>>
{
    /**
     * A {@link Quality} that, for each given value, checks if the {@link Iterable} under test contains at
     * least one element that equals that value.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bar", "baz"), containsAllOf("foo", "bar"));
     * </pre>
     */
    @SafeVarargs
    public ContainsAllOf(T... values)
    {
        this(new Mapped<>(EqualTo::new, new Seq<>(values)));
    }


    /**
     * A {@link Quality} that, for each given {@link Quality}, checks if the {@link Iterable} under test
     * contains at least one element that satisfies that {@link Quality}.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bazz", "foobar"), containsAllOf(equalTo("foo"), hasLength(3)));
     * </pre>
     */
    @SafeVarargs
    public ContainsAllOf(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public ContainsAllOf(Iterable<? extends Quality<? super T>> delegates)
    {
        super(actual -> assess(actual, delegates),
            new Spaced(
                new Text("contains all of {"),
                new Structured(COMMA_NEW_LINE, new Mapped<>(Quality::description, delegates)),
                new Text("}")));
    }


    private static <T> Assessment assess(Iterable<T> actual, Iterable<? extends Quality<? super T>> delegates)
    {
        Iterable<? extends Quality<? super T>> missing = missing(actual, delegates);
        if (missing.iterator().hasNext())
        {
            return new Fail(
                new Spaced(
                    new Value(actual),
                    new Text("did not contain {"),
                    new Structured(COMMA_NEW_LINE, new Mapped<>(Quality::description, missing)),
                    new Text("}")));
        }
        return new Pass(new Spaced(
            new Text("contains all of {"),
            new Structured(COMMA_NEW_LINE, new Mapped<>(Quality::description, delegates)),
            new Text("}")));
    }


    private static <T> Iterable<? extends Quality<? super T>> missing(Iterable<T> actual, Iterable<? extends Quality<? super T>> delegates)
    {
        return new Reduced<T, Iterable<? extends Quality<? super T>>>(
            () -> delegates,
            (missing, value) -> new Collected<>(
                ArrayList::new,
                new Sieved<>(delegate -> !delegate.assessmentOf(value).isSuccess(), missing)).value(),
            actual)
            .value();
    }

}

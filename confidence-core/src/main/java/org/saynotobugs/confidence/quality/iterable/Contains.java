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
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.StructuredDescription;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.ArrayList;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA_NEW_LINE;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class Contains<T> extends QualityComposition<Iterable<T>>
{
    /**
     * Creates a {@link Quality} that, for each given values, checks if the {@link Iterable} under test contains at least one element that equals that value.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bar", "baz"), contains("foo", "bar"));
     * </pre>
     */
    @SafeVarargs
    public Contains(T... values)
    {
        this(new Mapped<>(EqualTo::new, new Seq<>(values)));
    }


    @SafeVarargs
    public Contains(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public Contains(Iterable<? extends Quality<? super T>> delegates)
    {
        super(actual -> assess(actual, delegates),
            new Delimited(
                new TextDescription("contains {"),
                new StructuredDescription(COMMA_NEW_LINE, new Mapped<>(Quality::description, delegates)),
                new TextDescription("}")));
    }


    private static <T> Assessment assess(Iterable<T> actual, Iterable<? extends Quality<? super T>> delegates)
    {
        Iterable<? extends Quality<? super T>> missing = missing(actual, delegates);
        if (missing.iterator().hasNext())
        {
            return new Fail(
                new Delimited(
                    new ValueDescription(actual),
                    new TextDescription("did not contain {"),
                    new StructuredDescription(COMMA_NEW_LINE, new Mapped<>(Quality::description, missing)),
                    new TextDescription("}")));
        }
        return new Pass();
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

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

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Numbered;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.predicate.Not;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Structured;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA_NEW_LINE;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class ContainsNoneOf<T> extends QualityComposition<Iterable<T>>
{
    /**
     * A {@link Quality} that, for each given value, checks if the {@link Iterable} under test contains at
     * least one element that equals that value.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bar", "baz"), containsNoneOf("bazz", "foobar"));
     * </pre>
     */
    @SafeVarargs
    public ContainsNoneOf(T... values)
    {
        this(new Mapped<>(EqualTo::new, new Seq<>(values)), (valueDescription, missmatchesDescription) -> valueDescription);
    }


    /**
     * A {@link Quality} that, for each given {@link Quality}, checks if the {@link Iterable} under test
     * contains at least one element that satisfies that {@link Quality}.
     * <p>
     * Example
     * <pre>
     * assertThat(asList("foo", "bar", "foobar"), containsNoneOf(equalTo("bazz"), hasLength(5)));
     * </pre>
     */
    @SafeVarargs
    public ContainsNoneOf(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates), Spaced::new);
    }


    public ContainsNoneOf(Iterable<? extends Quality<? super T>> delegates)
    {
        this(delegates, Spaced::new);
    }


    private ContainsNoneOf(Iterable<? extends Quality<? super T>> delegates,
        BiFunction<? super Description, ? super Description, ? extends Description> descriptionFunction)
    {
        super(actual -> new AllPassed(new Text("contained { "), COMMA_NEW_LINE, new Text(" }"), assess(actual, delegates, descriptionFunction)),
            new Spaced(
                new Text("contains none of {"),
                new Structured(COMMA_NEW_LINE, new Mapped<>(Quality::description, delegates)),
                new Text("}")));
    }

    private static <T> Iterable<Assessment> assess(
        Iterable<T> actual,
        Iterable<? extends Quality<? super T>> delegates,
        BiFunction<? super Description, ? super Description, ? extends Description> descriptionFunction)
    {
        return new Mapped<>(
            numberedElement -> new FailUpdated(
                description -> descriptionFunction.value(new Spaced(new Text(numberedElement.left() + ":"), new Value(numberedElement.right())), description),
                new AllPassed(
                    new Text("{ "), COMMA_NEW_LINE, new Text(" }"),
                    new Sieved<>(new Not<>(Assessment::isSuccess),
                        new Mapped<>(
                            delegate -> delegate.assessmentOf(numberedElement.right()).isSuccess()
                                ? new Fail(delegate.description())
                                : new Pass(),
                            delegates)))),
            new Numbered<>(actual)
        );
    }

}

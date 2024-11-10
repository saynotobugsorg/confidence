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

package org.saynotobugs.confidence.quality.comparator;

import org.dmfs.jems2.Pair;
import org.dmfs.jems2.Predicate;
import org.dmfs.jems2.iterable.Expanded;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Numbered;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.pair.ValuePair;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.PassIf;
import org.saynotobugs.confidence.description.Block;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.valuedescription.Value;
import org.saynotobugs.confidence.utils.UnPaired;

import java.util.Comparator;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


@StaticFactories(
    value = "Comparator",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class ImposesOrderOf<T> implements Quality<Comparator<T>>
{

    private final Iterable<? extends T> mOrderedElements;


    /**
     * Creates a {@link Quality} that matches if the {@link Comparator} under test orders the given elements in the same
     * ascending order (left to right).
     */
    @SafeVarargs
    public ImposesOrderOf(T... orderedElements)
    {
        this(new Seq<>(orderedElements));
    }


    public ImposesOrderOf(Iterable<? extends T> orderedElements)
    {
        mOrderedElements = orderedElements;
    }


    @Override
    public Assessment assessmentOf(Comparator<T> candidate)
    {
        return new AllPassed(new Text("Comparator "), EMPTY,
            new Mapped<>(
                new UnPaired<Pair<Integer, ? extends T>, Pair<Integer, ? extends T>, Assessment>(
                    (left, right) -> verdict(candidate, left, right)),
                new Expanded<>(
                    element -> new Mapped<>(e -> new ValuePair<>(element, e), new Numbered<>(mOrderedElements)),
                    new Numbered<>(mOrderedElements))));
    }


    @Override
    public Description description()
    {
        return new Block(
            new Text("imposes the following order: "),
            EMPTY,
            EMPTY,
            new Mapped<>(Value::new, mOrderedElements));
    }


    private Assessment verdict(Comparator<? super T> actual, Pair<Integer, ? extends T> left, Pair<Integer, ? extends T> right)
    {
        return new org.dmfs.jems2.optional.Mapped<>(
            predicate -> testPair(actual, predicate, left, right),
            new org.dmfs.jems2.optional.First<>(predicate -> predicate.satisfiedBy(left.left().compareTo(right.left())),
                new Seq<Predicate<Integer>>(x -> x == 0, x -> x < 0, x -> x > 0))).value();
    }


    private Assessment testPair(Comparator<? super T> actual, Predicate<Integer> comparator, Pair<Integer, ? extends T> left, Pair<Integer, ? extends T> right)
    {
        int result = actual.compare(left.right(), right.right());
        return new PassIf(comparator.satisfiedBy(result), new Spaced(
            new Text("compared elements"),
            new Value(left.right()),
            new Text("at index " + left.left() + " and"),
            new Value(right.right()),
            new Text("at index " + right.left() + " incorrectly to"),
            new Value(result)));
    }
}

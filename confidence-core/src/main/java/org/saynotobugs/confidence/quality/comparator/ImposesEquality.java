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
import org.dmfs.jems2.iterable.Expanded;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Numbered;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.pair.ValuePair;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.PassIf;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.StructuredDescription;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
import org.saynotobugs.confidence.utils.UnPaired;

import java.util.Comparator;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA_NEW_LINE;
import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class ImposesEquality<T> implements Quality<Comparator<T>>
{

    private final Iterable<? extends T> mEqualElements;


    /**
     * Creates a {@link Quality} that matches if the {@link Comparator} under test orders the given elements in the same
     * ascending order (left to right).
     */
    @SafeVarargs
    public ImposesEquality(T... equalElements)
    {
        this(new Seq<>(equalElements));
    }


    public ImposesEquality(Iterable<? extends T> equalElements)
    {
        mEqualElements = equalElements;
    }


    @Override
    public Assessment assessmentOf(Comparator<T> candidate)
    {
        return new AllPassed(new TextDescription("Comparator "), COMMA_NEW_LINE,
            new Mapped<>(
                new UnPaired<Pair<Integer, ? extends T>, Pair<Integer, ? extends T>, Assessment>(
                    (left, right) -> testPair(candidate, left, right)),
                new Expanded<>(
                    element -> new Mapped<>(e -> new ValuePair<>(element, e), new Numbered<>(mEqualElements)),
                    new Numbered<>(mEqualElements))));
    }


    @Override
    public Description description()
    {
        return new StructuredDescription(
            new TextDescription("imposes equality on "),
            COMMA_NEW_LINE,
            EMPTY,
            new Mapped<>(ValueDescription::new, mEqualElements));
    }


    private Assessment testPair(Comparator<? super T> actual, Pair<Integer, ? extends T> left, Pair<Integer, ? extends T> right)
    {
        int result = actual.compare(left.right(), right.right());
        return new PassIf(result == 0, new Delimited(
            new TextDescription("compared elements"),
            new ValueDescription(left.right()),
            new TextDescription("at index " + left.left() + " and"),
            new ValueDescription(right.right()),
            new TextDescription("at index " + right.left() + " incorrectly to"),
            new ValueDescription(result)));
    }
}

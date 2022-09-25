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

package org.saynotobugs.confidence.quality.object;

import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;

import java.util.function.Function;
import java.util.function.Supplier;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


public final class Affects<V, T> implements Quality<Function<V, T>>
{
    private final Description mValueDescription;
    private final Supplier<V> mValueSupplier;
    private final Quality<T> mDelegate;
    private final Quality<? super V> mValueQuality;


    public Affects(Description valueDescription, Supplier<V> valueSupplier, Quality<T> delegate, Quality<? super V> valueQuality)
    {
        mValueDescription = valueDescription;
        mValueSupplier = valueSupplier;
        mDelegate = delegate;
        mValueQuality = valueQuality;
    }


    public Affects(Supplier<V> valueSupplier, Quality<T> delegate, Quality<? super V> valueQuality)
    {
        this(new ValueDescription(valueSupplier.get()), valueSupplier, delegate, valueQuality);
    }


    @Override
    public Assessment assessmentOf(Function<V, T> candidate)
    {
        V value = mValueSupplier.get();
        return new AllPassed(EMPTY,
            new Composite(new TextDescription(" and"), NEW_LINE),
            mDelegate.assessmentOf(candidate.apply(value)),
            mValueQuality.assessmentOf(value));
    }


    @Override
    public Description description()
    {
        return new Delimited(mDelegate.description(), new TextDescription("affects"), mValueDescription, new TextDescription("so it"),
            mValueQuality.description());
    }
}

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

package org.saynotobugs.confidence.rxjava3.rxexpectation.internal;

import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.NumberDescription;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;

import java.util.Collection;


public final class Emits<T> implements Quality<RxTestAdapter<T>>
{

    private final int mEmissionCount;
    private final Quality<? super Iterable<T>> mEmissionQualities;


    public Emits(int emissionCount, Quality<? super Iterable<T>> emissionQualities)
    {
        mEmissionCount = emissionCount;
        mEmissionQualities = emissionQualities;
    }


    @Override
    public Assessment assessmentOf(RxTestAdapter<T> candidate)
    {
        candidate.awaitNext(mEmissionCount);
        Collection<T> values = candidate.newValues(mEmissionCount);
        Assessment result = mEmissionQualities.assessmentOf(values);
        if (result.isSuccess())
        {
            candidate.ack(values.size());
        }
        return new FailPrepended(
            new Delimited(
                new TextDescription("emitted"),
                new NumberDescription(values.size()),
                new TextDescription("items that")),
            result);
    }


    @Override
    public Description description()
    {
        return new Delimited(
            new TextDescription("emits"),
            new NumberDescription(mEmissionCount),
            new TextDescription("items that"),
            mEmissionQualities.description());
    }
}

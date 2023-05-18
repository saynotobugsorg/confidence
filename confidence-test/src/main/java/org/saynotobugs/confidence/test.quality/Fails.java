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

package org.saynotobugs.confidence.test.quality;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.trivial.Anything;


@StaticFactories("Test")
public final class Fails<T> implements Quality<Quality<? super T>>
{
    private final T mMismatchingValue;
    private final Quality<? super Description> mDiffQuality;


    public Fails(T mismatchingValue)
    {
        this(mismatchingValue, new Anything());
    }


    public Fails(T mismatchingValue, String description)
    {
        this(mismatchingValue, new DescribesAs(description));
    }


    public Fails(T mismatchingValue, Quality<? super Description> diffQuality)
    {
        mMismatchingValue = mismatchingValue;
        mDiffQuality = diffQuality;
    }


    @Override
    public Assessment assessmentOf(Quality<? super T> candidate)
    {
        Assessment matcherAssessment = candidate.assessmentOf(mMismatchingValue);
        return matcherAssessment.isSuccess()
            ? new Fail(
            new Spaced(
                new Value(mMismatchingValue),
                new Text("matched"),
                candidate.description()))
            : new FailUpdated(
                orig -> new Spaced(
                    new Value(mMismatchingValue),
                    new Text("mismatched with diff"),
                    orig),
                mDiffQuality.assessmentOf(matcherAssessment.description()));
    }


    @Override
    public Description description()
    {
        return new Spaced(
            new Text("mismatches"),
            new Value(mMismatchingValue),
            new Text("with diff"),
            mDiffQuality.description());
    }
}

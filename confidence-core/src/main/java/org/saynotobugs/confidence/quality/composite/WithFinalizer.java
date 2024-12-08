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

package org.saynotobugs.confidence.quality.composite;

import org.dmfs.jems2.FragileProcedure;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.object.Successfully;


/**
 * A {@link Quality} decorator that runs a {@link FragileProcedure} on the test candidate after the assessment. Use cases
 * include running post test operations like closing the test candidate or cleaning up resources.
 */
@StaticFactories(
    value = "Composite",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class WithFinalizer<T> implements Quality<T>
{
    private final Description mDescription;
    private final Quality<? super T> mDelegate;
    private final Quality<? super T> mFinalQuality;


    public WithFinalizer(
        FragileProcedure<? super T, Exception> finalizer,
        Description description,
        Quality<? super T> delegate)
    {
        this(new Successfully<>(new Text("was finalized"), new Text("threw"), new Text("finalizes"), finalizer),
            description,
            delegate);
    }


    public WithFinalizer(
        Quality<? super T> finalizerQuality,
        Description description,
        Quality<? super T> delegate)
    {
        mDescription = description;
        mDelegate = delegate;
        mFinalQuality = finalizerQuality;
    }


    @Override
    public Assessment assessmentOf(T candidate)
    {
        Assessment assessment = new Fail(new Text("Did not complete"));
        try
        {
            assessment = mDelegate.assessmentOf(candidate);
        }
        finally
        {
            assessment = new AllPassed(
                mDescription,
                new Text(" and"),
                assessment,
                mFinalQuality.assessmentOf(candidate));
        }
        return assessment;
    }


    @Override
    public Description description()
    {
        return new Spaced(mDescription, mDelegate.description(), new Text("and"), mFinalQuality.description());
    }
}

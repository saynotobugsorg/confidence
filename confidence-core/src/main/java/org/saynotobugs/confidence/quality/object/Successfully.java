/*
 * Copyright 2024 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.saynotobugs.confidence.quality.object;

import org.dmfs.jems2.FragileProcedure;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Value;


/**
 * A {@link Quality} decorator that calls a {@link FragileProcedure} with the test candidate.
 * This primarily useful for composing {@link Quality}s mutable types, e.g. to trigger certain operations.
 * <p>
 * This doesn't say anything about what the procedure actually did. It merely means it didn't throw an exception.
 */
@StaticFactories(
    value = "Object",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class Successfully<T> implements Quality<T>
{
    private final Description mPassDescription;
    private final Description mFailDescription;
    private final Description mExpectationDescription;
    private final FragileProcedure<? super T, Exception> mCall;


    public Successfully(
        Description passDescription,
        Description failDescription,
        Description expectationDescription,
        FragileProcedure<? super T, Exception> call)
    {
        mPassDescription = passDescription;
        mFailDescription = failDescription;
        mExpectationDescription = expectationDescription;
        mCall = call;
    }


    @Override
    public Assessment assessmentOf(T candidate)
    {
        try
        {
            mCall.process(candidate);
            return new Pass(mPassDescription);
        }
        catch (Exception e)
        {
            return new Fail(new Spaced(mFailDescription, new Value(e)));
        }
    }


    @Override
    public Description description()
    {
        return mExpectationDescription;
    }
}

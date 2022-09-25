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

import org.dmfs.jems2.Fragile;
import org.dmfs.jems2.Function;
import org.dmfs.jems2.Procedure;
import org.dmfs.jems2.Single;
import org.dmfs.jems2.single.Frozen;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Delimited;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class Given<Env, T> implements Quality<T>
{
    private final Fragile<Env, Exception> mEnvGenerator;
    private final Function<? super Env, ? extends Description> mDescriptionFunction;
    private final Function<? super Env, ? extends Quality<T>> mDelegateFunction;
    private final Procedure<? super Env> mCleanup;
    private Single<Description> mExpectation;


    public Given(Fragile<Env, Exception> envGenerator,
        Function<? super Env, ? extends Description> descriptionFunction,
        Function<? super Env, ? extends Quality<T>> delegateFunction,
        Procedure<? super Env> cleanup)
    {
        mEnvGenerator = envGenerator;
        mDescriptionFunction = descriptionFunction;
        mDelegateFunction = delegateFunction;
        mCleanup = cleanup;
        mExpectation = new Frozen<>(() -> withEnv(env -> new Delimited(mDescriptionFunction.value(env), mDelegateFunction.value(env).description())));
    }


    @Override
    public Assessment assessmentOf(T candidate)
    {
        return withEnv(
            env -> {
                // "cache" the expectation using the current environment.
                // for this case it might actually be useful if an assessment would know its expected value
                mExpectation = new Frozen<>(() -> new Delimited(mDescriptionFunction.value(env), mDelegateFunction.value(env).description()));
                return mDelegateFunction.value(env).assessmentOf(candidate);
            }
        );
    }


    @Override
    public Description description()
    {
        return mExpectation.value();
    }


    private <R> R withEnv(Function<Env, R> function)
    {
        Env env;
        try
        {
            env = mEnvGenerator.value();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to create test environment", e);
        }
        try
        {
            return function.value(env);
        }
        finally
        {
            mCleanup.process(env);
        }
    }
}

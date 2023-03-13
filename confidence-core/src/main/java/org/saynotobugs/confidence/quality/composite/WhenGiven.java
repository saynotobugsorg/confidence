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
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.utils.DescriptionCharSequence;


/**
 * <b>Experimental!</b> use with care.
 * <p>
 * A {@link Quality} to provide an environment to execute the test in.
 */
@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class WhenGiven<Env extends AutoCloseable, T> implements Quality<T>
{
    private final Fragile<? extends Env, Exception> mEnvGenerator;
    private final Function<? super Env, ? extends Description> mDescriptionFunction;
    private final Function<? super Env, ? extends Quality<T>> mDelegateFunction;


    public WhenGiven(
        Function<? super Env, ? extends Description> descriptionFunction,
        Fragile<? extends Env, Exception> envGenerator,
        Function<? super Env, ? extends Quality<T>> delegateFunction)
    {
        mEnvGenerator = envGenerator;
        mDescriptionFunction = descriptionFunction;
        mDelegateFunction = delegateFunction;
    }


    @Override
    public Assessment assessmentOf(T candidate)
    {
        return withEnv(env -> new FailPrepended(mDescriptionFunction.value(env), mDelegateFunction.value(env).assessmentOf(candidate)));
    }


    @Override
    public Description description()
    {
        return withEnv(env -> new Spaced(mDescriptionFunction.value(env), mDelegateFunction.value(env).description()));
    }


    private <R> R withEnv(Function<Env, R> function)
    {
        try (Env env = mEnvGenerator.value())
        {
            return function.value(env);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Enable to verify " + new DescriptionCharSequence(description()), e);
        }
    }
}
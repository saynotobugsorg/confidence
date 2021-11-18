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

import org.dmfs.jems2.generatable.Sequence;
import org.dmfs.jems2.iterable.First;
import org.dmfs.jems2.procedure.ForEach;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA_NEW_LINE;
import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class Parallel<T> implements Quality<T>
{
    private final int mThreadCount;
    private final Quality<T> mDelegate;


    public Parallel(Quality<T> delegate)
    {
        this(1000, delegate);
    }


    public Parallel(int threadCount, Quality<T> delegate)
    {
        mThreadCount = threadCount;
        mDelegate = delegate;
    }


    @Override
    public Assessment assessmentOf(T candidate)
    {
        ExecutorService executor = Executors.newFixedThreadPool(mThreadCount);
        List<Assessment> results = Collections.synchronizedList(new ArrayList<>(mThreadCount));
        new ForEach<>(new First<>(mThreadCount, new Sequence<>(0, i -> i + 1)))
            .process(
                i -> {
                    results.add(i, new Fail(new TextDescription("missing result " + i)));
                    executor.execute(() -> {
                        try
                        {
                            results.set(i, new FailPrepended(new TextDescription("#" + i + " in thread " + Thread.currentThread().getName()),
                                mDelegate.assessmentOf(candidate)));
                        }
                        catch (Exception e)
                        {
                            results.set(i, new Fail(new Delimited(new TextDescription("#" + i + " in thread " + Thread.currentThread().getName()),
                                new ValueDescription(e))));
                        }
                    });
                }
            );
        executor.shutdown();
        try
        {
            if (!executor.awaitTermination(1, TimeUnit.DAYS))
            {
                return new Fail(new TextDescription("did not finish within one day"));
            }
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            return new Fail(new TextDescription("interrupted"));
        }
        return new AllPassed(new TextDescription("executions: "), COMMA_NEW_LINE, EMPTY, results);
    }


    @Override
    public Description description()
    {
        return new Delimited(new TextDescription("running " + mThreadCount + " parallel execution, each"), mDelegate.description());
    }
}

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

package org.saynotobugs.confidence.rxjava3.rxexpectation;

import io.reactivex.rxjava3.schedulers.TestScheduler;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;

import java.time.Duration;
import java.util.concurrent.TimeUnit;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Within<T> implements RxExpectation<T>
{
    private final Description mDescription;
    private final Duration mDuration;
    private final RxExpectation<T> mDelegate;


    /**
     * Creates an {@link RxExpectation} that expects the given {@link RxExpectation} to match within the given {@link Duration}.
     * <p>
     * It does so by forwarding the {@link TestScheduler} by the given {@link Duration} and delegating to the given {@link RxExpectation}.
     */
    public Within(Duration duration, RxExpectation<T> delegate)
    {
        this(new Spaced(new Text("after"), new Text(duration.toString())), duration, delegate);
    }

    /**
     * Creates an {@link RxExpectation} that expects the given {@link RxExpectation} to match within the given {@link Duration}.
     * <p>
     * It does so by forwarding the {@link TestScheduler} by the given {@link Duration} and delegating to the given {@link RxExpectation}.
     */
    Within(Description description, Duration duration, RxExpectation<T> delegate)
    {
        if (duration.isNegative())
        {
            throw new IllegalArgumentException("Unsupported negative duration: " + duration);
        }
        mDescription = description;
        mDuration = duration;
        mDelegate = delegate;
    }


    @Override
    public Quality<RxTestAdapter<T>> quality(TestScheduler scheduler)
    {
        Quality<RxTestAdapter<T>> delegate = mDelegate.quality(scheduler);
        return new Quality<RxTestAdapter<T>>()
        {
            @Override
            public Assessment assessmentOf(RxTestAdapter<T> candidate)
            {
                scheduler.advanceTimeBy(mDuration.toMillis(), TimeUnit.MILLISECONDS);
                scheduler.triggerActions();
                return new DescriptionUpdated(
                    new TextAndOriginal<>(mDescription),
                    delegate.assessmentOf(candidate));
            }


            @Override
            public Description description()
            {
                return new Spaced(mDescription, delegate.description());
            }
        };
    }
}

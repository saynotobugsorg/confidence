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

package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.schedulers.TestScheduler;
import org.dmfs.jems2.Function;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.quality.composite.AllOfFailingFast;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;
import org.saynotobugs.confidence.rxjava3.rxexpectation.ActionTriggering;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class RxWithSchedulerThat<T, RxType> implements Quality<Function<? super TestScheduler, ? extends RxType>>
{
    private final Function<? super RxType, ? extends RxTestAdapter<T>> mTestAdapterFunction;
    private final Function<? super TestScheduler, ? extends Quality<? super RxTestAdapter<T>>> mDelegate;


    public RxWithSchedulerThat(Description description, Function<? super RxType, ? extends RxTestAdapter<T>> testAdapterFunction,
        RxExpectation<T> event)
    {
        mTestAdapterFunction = testAdapterFunction;
        mDelegate = scheduler -> new DescribedAs<>(
            orig -> new Spaced(description, orig),
            new ActionTriggering<>(event).quality(scheduler));
    }


    public RxWithSchedulerThat(Description description, Function<? super RxType, ? extends RxTestAdapter<T>> testAdapterFunction,
        Iterable<? extends RxExpectation<T>> events)
    {
        mTestAdapterFunction = testAdapterFunction;
        mDelegate = scheduler -> new DescribedAs<>(
            orig -> new Spaced(description, orig),
            new AllOfFailingFast<>(new Mapped<>(event -> new ActionTriggering<>(event).quality(scheduler), events)));
    }


    @Override
    public Assessment assessmentOf(Function<? super TestScheduler, ? extends RxType> candidate)
    {
        TestScheduler scheduler = new TestScheduler();
        return mDelegate.value(scheduler).assessmentOf(mTestAdapterFunction.value(candidate.value(scheduler)));
    }


    @Override
    public Description description()
    {
        return mDelegate.value(new TestScheduler()).description();
    }
}

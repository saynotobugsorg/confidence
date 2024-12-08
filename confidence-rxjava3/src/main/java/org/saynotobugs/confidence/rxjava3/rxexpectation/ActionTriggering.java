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
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;


/**
 * A decorator to an {@link RxExpectation} that triggers the actions of the {@link TestScheduler} before delegating to the {@link Quality}
 * returned by the decorated {@link RxExpectation}.
 */
public final class ActionTriggering<T> implements RxExpectation<T>
{
    private final RxExpectation<T> mDelegate;


    public ActionTriggering(RxExpectation<T> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Quality<RxTestAdapter<T>> quality(TestScheduler testScheduler)
    {
        return new Quality<RxTestAdapter<T>>()
        {
            @Override
            public Assessment assessmentOf(RxTestAdapter<T> candidate)
            {
                testScheduler.triggerActions();
                return mDelegate.quality(testScheduler).assessmentOf(candidate);
            }


            @Override
            public Description description()
            {
                return mDelegate.quality(testScheduler).description();
            }
        };
    }
}

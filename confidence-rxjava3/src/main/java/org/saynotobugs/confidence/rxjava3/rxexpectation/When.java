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

package org.saynotobugs.confidence.rxjava3.rxexpectation;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.RxExpectationComposition;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class When<T> extends RxExpectationComposition<T>
{

    /**
     * Creates an {@link RxExpectation} that runs the given {@link Runnable} before delegating to the given {@link RxExpectation}.
     */
    public When(Runnable trigger, RxExpectation<T> delegate)
    {
        this("triggered", trigger, delegate);
    }


    /**
     * Creates an {@link RxExpectation} that runs the given {@link Runnable} before delegating to the given {@link RxExpectation}.
     */
    public When(String triggerDescription, Runnable trigger, RxExpectation<T> delegate)
    {
        this(new TextDescription(triggerDescription), trigger, delegate);
    }


    /**
     * Creates an {@link RxExpectation} that runs the given {@link Runnable} before delegating to the given {@link RxExpectation}.
     */
    public When(Description triggerDescription, Runnable trigger, RxExpectation<T> delegate)
    {
        super(testScheduler -> new Quality<RxTestAdapter<T>>()
        {
            @Override
            public Assessment assessmentOf(RxTestAdapter<T> candidate)
            {
                trigger.run();
                return new FailPrepended(
                    new Spaced(new TextDescription("when"), triggerDescription),
                    new ActionTriggering<>(delegate).quality(testScheduler).assessmentOf(candidate));
            }


            @Override
            public Description description()
            {
                return new Spaced(new TextDescription("when"), triggerDescription, delegate.quality(testScheduler).description());
            }
        });
    }
}

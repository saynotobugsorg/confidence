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

import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.description.bifunction.Just;
import org.saynotobugs.confidence.description.bifunction.Original;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.Implied;
import org.saynotobugs.confidence.quality.composite.Not;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.RxExpectationComposition;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;
import org.saynotobugs.confidence.rxjava3.rxexpectation.internal.IsCancelled;
import org.saynotobugs.confidence.rxjava3.rxexpectation.internal.IsComplete;
import org.saynotobugs.confidence.rxjava3.rxexpectation.internal.NoErrors;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class IsAlive<T> extends RxExpectationComposition<T>
{
    /**
     * Returns an {@link RxExpectation} that expects a {@link RxTestAdapter} that's still alive,
     * i.e. did not complete, did not error nor has been cancelled.
     */
    public IsAlive()
    {
        super(testScheduler -> new DescribedAs<>(
            new Just<>("is alive"),
            new Original<>(),
            new Just<>("is alive"),
            new Implied<>(
                new Seq<>(
                    new NoErrors<>(),
                    new Not<>(new IsComplete()),
                    new Not<>(new IsCancelled<>())),
                new Anything() // this will never be shown, we either pass or fail on one of the above
            )));
    }
}

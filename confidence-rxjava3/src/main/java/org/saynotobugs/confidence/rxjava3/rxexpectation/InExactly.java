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
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.Implied;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;

import java.time.Duration;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class InExactly<T> implements RxExpectation<T>
{
    private final Duration mDuration;
    private final RxExpectation<T> mDelegate;


    /**
     * Creates an {@link RxExpectation} that expects the given {@link RxExpectation} in exactly the given {@link Duration}.
     * <p>
     * This is equivalent to the sequence
     * <pre>{@code
     * within(duration.minusMillis(1), allOf(emitsNothing(), isAlive())),
     * within(ofMillis(1), delegate)
     * }</pre>
     */
    public InExactly(Duration duration, RxExpectation<T> delegate)
    {
        if (duration.isNegative())
        {
            throw new IllegalArgumentException("Unsupported negative duration: " + duration);
        }
        mDuration = duration;
        mDelegate = delegate;
    }


    @Override
    public Quality<RxTestAdapter<T>> quality(TestScheduler scheduler)
    {
        return new Implied<>(
            new Seq<>(
                new Within<>(new Spaced(new Text("in less than"), new Text(mDuration.toString())),
                    mDuration.minusMillis(1), new EmitsNothing<T>()).quality(scheduler),
                new Immediately<>(new IsAlive<T>()).quality(scheduler)),
            new Within<>(
                new Spaced(new Text("in exactly"), new Text(mDuration.toString())),
                Duration.ofMillis(1),
                mDelegate).quality(scheduler));
    }
}

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

package org.saynotobugs.confidence.rxjava3.function;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import org.dmfs.jems2.bifunction.DelegatingBiFunction;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;
import org.saynotobugs.confidence.rxjava3.adapters.EmitterAdapter;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class After<T> extends DelegatingBiFunction<Scheduler, Flowable<T>, Flowable<T>>
{
    /**
     * Schedules an event with the given {@link Duration} after all the previous events have been executed.
     */
    public After(Duration duration, Quality<? super RxSubjectAdapter<T>> delegate)
    {
        super((scheduler, flowable) ->
            flowable.concatWith(Completable.timer(duration.toMillis(), TimeUnit.MILLISECONDS, scheduler)
                .andThen(Flowable.create(emitter -> {
                        delegate.assessmentOf(new EmitterAdapter<>(emitter));
                        emitter.onComplete();
                    },
                    BackpressureStrategy.BUFFER))));
    }

}

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

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.Function;
import org.dmfs.jems2.function.DelegatingFunction;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;

/**
 * A {@link Function} that takes a {@link Scheduler} and returns a {@link Flowable} that emits elements at certain
 * times.
 */
@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Scheduled<T> extends DelegatingFunction<Scheduler, Flowable<T>>
{

    @SafeVarargs
    public Scheduled(BiFunction<Scheduler, Flowable<T>, Flowable<T>>... emissions)
    {
        this(new Seq<>(emissions));
    }

    public Scheduled(Iterable<BiFunction<Scheduler, Flowable<T>, Flowable<T>>> emissions)
    {
        super(scheduler ->
            Flowable.fromIterable(emissions)
                .reduce(Flowable.<T>empty(),
                    (flowable, emissionFunction) -> emissionFunction.value(scheduler, flowable))
                .flatMapPublisher(x -> x));
    }

}

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

package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import org.dmfs.jems2.Function;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.adapters.RxTestObserver;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class SingleThat<T> extends QualityComposition<Function<? super TestScheduler, ? extends SingleSource<? extends T>>>
{
    @SafeVarargs
    public SingleThat(RxExpectation<T>... events)
    {
        this(new Seq<>(events));
    }


    public SingleThat(Iterable<? extends RxExpectation<T>> events)
    {
        super(new RxWithSchedulerThat<>(
            new Text("Single that"),
            singleSource -> {
                RxTestObserver<T> testObserver = new RxTestObserver<>();
                singleSource.subscribe(testObserver);
                return testObserver;
            },
            events));
    }
}

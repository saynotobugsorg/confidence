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

import org.dmfs.jems2.Function;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Within;

import io.reactivex.rxjava3.schedulers.TestScheduler;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Unscheduled<RxType> extends QualityComposition<RxType>
{
    /**
     * Creates a {@link Quality} to test RX type implementations that don't require a scheduler.
     * <p>
     * Scheduler events like {@link Within} will be ignored.
     */
    public Unscheduled(Quality<? super Function<? super TestScheduler, ? extends RxType>> delegate)
    {
        super(new Has<>(
            new TextDescription("unscheduled"),
            new TextDescription("unscheduled"),
            matcher -> (Function<? super TestScheduler, ? extends RxType>) ignoredScheduler -> matcher,
            delegate));
    }
}

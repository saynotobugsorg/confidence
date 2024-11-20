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

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.quality.composite.Guarded;
import org.saynotobugs.confidence.quality.composite.Not;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.RxExpectationComposition;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;
import org.saynotobugs.confidence.rxjava3.rxexpectation.internal.Errors;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Emits<T> extends RxExpectationComposition<T>
{

    /**
     * Creates an {@link RxExpectation} that verifies whether the {@link RxTestAdapter} contains the given emissions in the given order.
     */
    @SafeVarargs
    public Emits(T... emissions)
    {
        this(emissions.length, new Mapped<>(EqualTo::new, new Seq<>(emissions)));
    }


    @SafeVarargs
    public Emits(Quality<? super T>... emissionQualities)
    {
        this(emissionQualities.length, new Seq<>(emissionQualities));
    }


    public Emits(int emissionCount, Iterable<? extends Quality<? super T>> emissionsQualities)
    {
        this(emissionCount, new Iterates<>(emissionsQualities));
    }


    public Emits(int emissionCount, Quality<? super Iterable<T>> emissionsQuality)
    {
        super(testScheduler ->
            new Guarded<>(
                new Seq<>(new Not<>(new Errors<>(new Anything()))),
                new org.saynotobugs.confidence.rxjava3.rxexpectation.internal.Emits<>(emissionCount, emissionsQuality)));
    }
}

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

package org.saynotobugs.confidence.quality.composite;

import org.dmfs.jems2.iterable.Joined;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.optional.First;
import org.dmfs.jems2.predicate.Not;
import org.dmfs.jems2.single.Backed;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Pass;


@StaticFactories(
    value = "Composite",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class Guarded<T> extends QualityComposition<T>
{
    /**
     * A {@link Quality} of an object that is described by the delegate but expects the guarding {@link Quality}s
     * to be satisfied before the delegate is even evaluated.
     */
    public Guarded(Iterable<? extends Quality<? super T>> guards, Quality<? super T> delegate)
    {
        super(actual ->
                new Backed<>(
                    new First<>(new Not<>(Assessment::isSuccess),
                        new Mapped<>(d -> d.assessmentOf(actual), new Joined<Quality<? super T>>(guards, delegate))),
                    new Pass()).value(),
            delegate.description());
    }
}

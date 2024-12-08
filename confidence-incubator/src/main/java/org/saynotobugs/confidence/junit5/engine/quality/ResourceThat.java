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

package org.saynotobugs.confidence.junit5.engine.quality;

import org.dmfs.jems2.Generator;
import org.dmfs.jems2.function.Unchecked;
import org.dmfs.jems2.generatable.Sequence;
import org.dmfs.jems2.iterable.First;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.single.Collected;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.junit5.engine.Resource;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import java.util.ArrayList;

import static org.dmfs.jems2.confidence.Jems2.hasValue;
import static org.saynotobugs.confidence.core.quality.AutoClosable.autoClosableThat;
import static org.saynotobugs.confidence.core.quality.Composite.*;
import static org.saynotobugs.confidence.core.quality.Iterable.each;
import static org.saynotobugs.confidence.core.quality.Object.anything;

@StaticFactories(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class ResourceThat<T> extends QualityComposition<Generator<? extends Resource<? extends T>>>
{
    public ResourceThat(int parallelFactor, Quality<? super T> delegate, Quality<? super T> cleanUpDelegate)
    {
        super(
            allOf(
                has("single use",
                    resourceGenerator -> resourceGenerator.next().value(),
                    allOf(
                        autoClosableThat(hasValue(delegate)),
                        hasValue(cleanUpDelegate)
                    )),
                has("parallel use",
                    resourceGenerator -> new Collected<>(ArrayList::new, new Mapped<>(new Unchecked<>(i -> resourceGenerator.next().value()), new First<>(parallelFactor, new Sequence<>(1, i -> i + 1)))).value(),
                    allOf(
                        parallel(parallelFactor, each(hasValue(anything()))),// don't test the value yet, as the test might fail next a second time
                        each(autoClosableThat(hasValue(delegate))), // the delegate quality should match until the last resource has been closed
                        each(hasValue(cleanUpDelegate))
                    ))));
    }
}

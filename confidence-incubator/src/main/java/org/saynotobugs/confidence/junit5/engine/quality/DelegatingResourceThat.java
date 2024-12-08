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
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.junit5.engine.Resource;
import org.saynotobugs.confidence.junit5.engine.ResourceHandle;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.dmfs.jems2.confidence.Jems2.hasValue;
import static org.saynotobugs.confidence.core.quality.AutoClosable.autoClosableThat;
import static org.saynotobugs.confidence.core.quality.Composite.allOf;
import static org.saynotobugs.confidence.core.quality.Composite.has;
import static org.saynotobugs.confidence.core.quality.Function.mutatesArgument;
import static org.saynotobugs.confidence.core.quality.Grammar.soIt;
import static org.saynotobugs.confidence.core.quality.Grammar.when;
import static org.saynotobugs.confidence.core.quality.Number.hasLongValue;

@StaticFactories(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class DelegatingResourceThat<Original, T> extends QualityComposition<Function<Resource<Original>, Resource<T>>>
{
    public DelegatingResourceThat(Generator<Original> originalGenerator, Quality<? super T> delegate)
    {
        this(autoClosableThat(hasValue(delegate)), originalGenerator);
    }

    public DelegatingResourceThat(Generator<Original> originalGenerator, Quality<? super T> delegate, Quality<? super T> cleanUpDelegate)
    {
        this(
            allOf(
                autoClosableThat(hasValue(delegate)),
                hasValue(cleanUpDelegate)
            ),
            originalGenerator);
    }

    private DelegatingResourceThat(Quality<? super ResourceHandle<T>> delegate, Generator<Original> originalGenerator)
    {
        super(mutatesArgument(
            () -> new TestResource<>(originalGenerator.next()),
            soIt(allOf(
                has("times open", r -> ((TestResource<T>) r).mTimesOpen, hasLongValue(1)),
                has("times closed", r -> ((TestResource<T>) r).mTimesClosed, hasLongValue(1)))),
            when(
                has("single use",
                    Resource::value,
                    delegate
                ))));
    }

    private static final class TestResource<T> implements Resource<T>
    {
        private final T mResource;
        private final AtomicInteger mTimesOpen = new AtomicInteger();
        private final AtomicInteger mTimesClosed = new AtomicInteger();

        private TestResource(T resource) {this.mResource = resource;}

        @Override
        public ResourceHandle<T> value()
        {
            if (mTimesClosed.get() > 0)
            {
                throw new AssertionError("resource value called after closing");
            }
            mTimesOpen.incrementAndGet();
            return new ResourceHandle<T>()
            {
                @Override
                public void close()
                {
                    mTimesClosed.incrementAndGet();
                }

                @Override
                public T value()
                {
                    if (mTimesClosed.get() > 0)
                    {
                        throw new AssertionError("resource handle value called after closing");
                    }
                    return mResource;
                }
            };
        }
    }
}

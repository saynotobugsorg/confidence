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

package org.saynotobugs.confidence.quality.consumer;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
import org.saynotobugs.confidence.quality.Core;
import org.saynotobugs.confidence.quality.grammar.SoIt;

import java.util.function.Consumer;
import java.util.function.Supplier;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class ConsumerThatAffects<T> implements Quality<Consumer<T>>
{
    private final Description mValueDescription;
    private final Supplier<T> mValueSupplier;
    private final Quality<? super T> mValueQuality;


    /**
     * A {@link Quality} that describes the side effect of a {@link Consumer} on its argument.
     * <p>
     * The argument is provided by the given {@link Supplier} and tested by the given {@link Quality} after passing it to the consumer.
     * <p>
     * For best results, decorate the value{@link Quality} with {@link SoIt} or {@link Core#soIt(Quality)}.
     */
    public ConsumerThatAffects(Description valueDescription, Supplier<T> valueSupplier, Quality<? super T> valueQuality)
    {
        mValueDescription = valueDescription;
        mValueSupplier = valueSupplier;
        mValueQuality = valueQuality;
    }


    /**
     * A {@link Quality} that describes the side effect of a {@link Consumer} on its argument.
     * <p>
     * The argument is provided by the given {@link Supplier} and tested by the given {@link Quality} after passing it to the consumer.
     * <p>
     * For best results, decorate the value{@link Quality} with {@link SoIt} or {@link Core#soIt(Quality)}.
     */
    public ConsumerThatAffects(Supplier<T> valueSupplier, Quality<? super T> valueQuality)
    {
        this(new Delimited(new TextDescription("affects"), new ValueDescription(valueSupplier.get())), valueSupplier, valueQuality);
    }


    @Override
    public Assessment assessmentOf(Consumer<T> candidate)
    {
        T value = mValueSupplier.get();
        candidate.accept(value);
        return new FailPrepended(new Delimited(new TextDescription("Consumer that"), mValueDescription),
            mValueQuality.assessmentOf(value));
    }


    @Override
    public Description description()
    {
        return new Delimited(new TextDescription("Consumer that"), mValueDescription, mValueQuality.description());
    }
}

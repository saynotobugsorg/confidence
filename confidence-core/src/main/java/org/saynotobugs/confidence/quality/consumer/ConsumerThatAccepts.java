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

import org.dmfs.jems2.FragileFunction;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import java.util.function.Consumer;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class ConsumerThatAccepts<T, C> extends QualityComposition<FragileFunction<T, Consumer<C>, ?>>
{
    public ConsumerThatAccepts(T value, Quality<Consumer<C>> delegate)
    {
        super(new Has<>(
            new Spaced(new TextDescription("Consumer that accepts"), new ValueDescription(value)),
            new Spaced(new TextDescription("Consumer that accepted"), new ValueDescription(value)),
            f -> f.value(value),
            delegate));
    }

}

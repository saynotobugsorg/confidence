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
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Successfully;

import java.util.function.Consumer;
import java.util.function.Supplier;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class ConsumerThatAccepts<T> extends QualityComposition<Consumer<T>>
{
    public ConsumerThatAccepts(Description successdescription, Description faildescription, Supplier<T> valueSupplier)
    {
        super(new Successfully<>(successdescription, faildescription, consumer -> consumer.accept(valueSupplier.get())));
    }


    public ConsumerThatAccepts(Supplier<T> valueSupplier)
    {
        this(new Spaced(new TextDescription("Consumer that accepts"), new ValueDescription(valueSupplier.get())),
            new Spaced(new TextDescription("Consumer that accepts"), new ValueDescription(valueSupplier.get()), new TextDescription("threw")),
            valueSupplier);
    }


    public ConsumerThatAccepts(T value)
    {
        this(new Spaced(new TextDescription("Consumer that accepts"), new ValueDescription(value)),
            new Spaced(new TextDescription("Consumer that accepts"), new ValueDescription(value), new TextDescription("threw")),
            () -> value);
    }
}

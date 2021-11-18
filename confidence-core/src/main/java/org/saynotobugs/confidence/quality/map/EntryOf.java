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

package org.saynotobugs.confidence.quality.map;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.quality.trivial.Anything;

import java.util.Map;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class EntryOf<K, V> extends QualityComposition<Map.Entry<K, V>>
{
    public EntryOf(K key)
    {
        this(new EqualTo<>(key), new Anything());
    }


    public EntryOf(Quality<? super K> key)
    {
        this(new EqualTo<>(key), new Anything());
    }


    public EntryOf(K key, V value)
    {
        this(new EqualTo<>(key), new EqualTo<>(value));
    }


    public EntryOf(K key, Quality<? super V> valueQuality)
    {
        this(new EqualTo<>(key), valueQuality);
    }


    public EntryOf(Quality<? super K> key, Quality<? super V> value)
    {
        super(new DescribedAs<>(description -> new Composite(
            new TextDescription("Entry { "),
            key.description(),
            new TextDescription(": "),
            value.description(),
            new TextDescription(" }")),
            new AllOf<>(
                new Has<>(Map.Entry::getKey, key),
                new Has<>(Map.Entry::getValue, value)
            )));
    }
}

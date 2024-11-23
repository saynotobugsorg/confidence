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

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.entry.EntryOf;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.Map;


@StaticFactories(
    value = "Map",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class ContainsEntry<K, V> extends QualityComposition<Map<K, V>>
{
    public ContainsEntry(K key)
    {
        this(new EqualTo<>(key));
    }


    public ContainsEntry(Quality<? super K> key)
    {
        this(key, new Anything());
    }


    public ContainsEntry(K key, V value)
    {
        this(new EqualTo<>(key), new EqualTo<>(value));
    }


    public ContainsEntry(K key, Quality<? super V> valueQuality)
    {
        this(new EqualTo<>(key), valueQuality);
    }


    public ContainsEntry(Quality<? super K> keyQuality, Quality<? super V> valueQuality)
    {
        super(new Has<>(Map::entrySet, new Contains<>(new EntryOf<>(keyQuality, valueQuality))));
    }
}

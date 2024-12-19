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

package org.saynotobugs.confidence.quality.map;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.*;
import org.saynotobugs.confidence.description.bifunction.Just;
import org.saynotobugs.confidence.description.bifunction.Original;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.description.bifunction.ValueAndText;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.Implied;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.entry.EntryOf;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.quality.object.Satisfies;
import org.saynotobugs.confidence.quality.optional.Present;

import java.util.Map;
import java.util.Optional;


@StaticFactories(
    value = "Map",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class ContainsEntry<K, V> extends QualityComposition<Map<K, V>>
{
    public ContainsEntry(K key)
    {
        this(key, new Anything());
    }


    public ContainsEntry(Quality<? super K> key)
    {
        this(key, new Anything());
    }


    public ContainsEntry(K key, V value)
    {
        this(key, new EqualTo<>(value));
    }


    public ContainsEntry(K key, Quality<? super V> valueQuality)
    {
        super(
            new Implied<>(
                new Satisfies<>(map -> map.containsKey(key),
                    map -> new Spaced(new Text("contained key"), new Value(key)),
                    new ValueAndText<>(new Spaced(new Text("did not contain key"), new Value(key))),
                    new Spaced(new Text("contains key"), new Value(key))),
                new DescribedAs<>(
                    (value, original) -> new Spaced(new Text("contained entry"), new Enclosed("(", new Delimited(": ", new Value(key), original), ")")),
                    (value, original) -> new Spaced(new Value(value), new Text("contained entry"), new Enclosed("(", new Delimited(": ", new Value(key), original), ")")),
                    new Just<>(new Spaced(new Text("contains entry"), new Enclosed("(", new Delimited(": ", new Value(key), valueQuality.description()), ")"))),
                    new Has<>(map -> Optional.ofNullable(map.get(key)), new Present<>(new Original<>(), new Original<>(), valueQuality)))));
    }


    public ContainsEntry(Quality<? super K> keyQuality, Quality<? super V> valueQuality)
    {
        super(
            new Implied<>(
                new DescribedAs<>(
                    new TextAndOriginal<>(new Text("contained key")),
                    new ValueAndText<>(new Spaced(new Text("did not contain key"), keyQuality.description())),
                    new Just<>(new Spaced(new Text("contains key"), keyQuality.description())),
                    new Has<Map<K, V>, Iterable<K>>(Map::keySet,
                        new Contains<>(keyQuality))),
                new Has<Map<K, V>, Iterable<Map.Entry<K, V>>>(Map::entrySet,
                    new Contains<>(new EntryOf<>(keyQuality, valueQuality)))));
    }
}

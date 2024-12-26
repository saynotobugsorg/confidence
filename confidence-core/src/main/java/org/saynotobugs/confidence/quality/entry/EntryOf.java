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

package org.saynotobugs.confidence.quality.entry;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.Enclosed;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.Just;
import org.saynotobugs.confidence.description.bifunction.TextAndValue;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.Map;


@StaticFactories(
    value = "Entry",
    packageName = "org.saynotobugs.confidence.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class EntryOf<K, V> extends QualityComposition<Map.Entry<K, V>>
{
    public EntryOf(K key)
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
        super(new DescribedAs<>(
            new TextAndValue<>("entry", entry -> new Enclosed("(", new Value(entry), ")")),
            new TextAndValue<>("entry", entry -> new Enclosed("(", new Value(entry), ")")),
            new Just<>(new Composite(new Text("entry ("), key.description(), new Text(": "), value.description(), new Text(")"))),
            new AllOf<>(
                new Has<>("key", Map.Entry::getKey, key),
                new Has<>("value", Map.Entry::getValue, value)
            )));
    }
}

/*
 * Copyright 2023 dmfs GmbH
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

package org.saynotobugs.confidence.json.quality;

import org.dmfs.jems2.generatable.Sequence;
import org.dmfs.jems2.iterable.First;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.PresentValues;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

/**
 * Adapter to use the {@link Array} {@link Quality} with normal {@link Iterable} {@link Quality}s.
 * <p>
 * This makes it possible to create something like
 * <pre>
 * array(that(contains(string(xyz))))
 * </pre>
 *
 * @deprecated this is too easy to confuse with {@link org.saynotobugs.confidence.quality.grammar.That} and
 * inconsistent with {@link org.saynotobugs.confidence.quality.array.ArrayThat}. Use {@link ArrayThat} instead.
 */
@StaticFactories(value = "Json", packageName = "org.saynotobugs.confidence.json")
@Deprecated
public final class That extends QualityComposition<JsonArrayAdapter>
{
    public That(Quality<? super Iterable<JsonElementAdapter>> delegate)
    {
        super(new Has<>(
            new Text("that"),
            new Text("that"),
            array -> new PresentValues<>(
                new Mapped<>(array::elementAt,
                    new First<>(array.length(),
                        new Sequence<>(0, i -> i + 1)))), delegate));
    }
}

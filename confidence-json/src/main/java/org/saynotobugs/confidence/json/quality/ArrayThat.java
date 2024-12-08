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

package org.saynotobugs.confidence.json.quality;

import org.dmfs.jems2.Function;
import org.dmfs.jems2.generatable.Sequence;
import org.dmfs.jems2.iterable.First;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.PresentValues;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import static org.dmfs.jems2.confidence.Jems2.present;

/**
 * {@link Quality} of a JSON array that delegates to a normal {@link Iterable} {@link Quality}.
 * <h2>Example</h2>
 * <pre>
 * arrayThat(contains(string(xyz)))
 * </pre>
 */
@StaticFactories(
    value = "Json",
    packageName = "org.saynotobugs.confidence.json.quality",
    deprecates = @DeprecatedFactories(value = "Json", packageName = "org.saynotobugs.confidence.json"))
public final class ArrayThat extends QualityComposition<JsonElementAdapter>
{
    public ArrayThat(Quality<? super Iterable<JsonElementAdapter>> delegate)
    {
        super(new Has<>(
            (Function<Description, Description>) orig -> orig,
            orig -> orig,
            JsonElementAdapter::asArray,
            present(d -> d,
                d -> d,
                new Text("not an array"),
                new Has<>(
                    new Text("array that"),
                    new Text("array that"),
                    array -> new PresentValues<>(
                        new Mapped<>(array::elementAt,
                            new First<>(array.length(),
                                new Sequence<>(0, i -> i + 1)))), delegate))));
    }
}

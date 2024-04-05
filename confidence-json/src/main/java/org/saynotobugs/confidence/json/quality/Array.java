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

import org.dmfs.jems2.Function;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Numbered;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import java.lang.Number;
import java.lang.String;

import static org.dmfs.jems2.confidence.Jems2.present;

/**
 * {@link Quality} of a JSON array.
 */
@StaticFactories(
    value = "Json",
    packageName = "org.saynotobugs.confidence.json.quality",
    deprecates = @DeprecatedFactories(value = "Json", packageName = "org.saynotobugs.confidence.json"))
public final class Array extends QualityComposition<JsonElementAdapter>
{
    public Array(String... strings)
    {
        this(strings.length, new Mapped<>(org.saynotobugs.confidence.json.quality.String::new, new Seq<>(strings)));
    }


    public Array(Number... numbers)
    {
        this(numbers.length, new Mapped<>(org.saynotobugs.confidence.json.quality.Number::new, new Seq<>(numbers)));
    }


    @SafeVarargs
    public Array(Quality<? super JsonElementAdapter>... qualities)
    {
        this(qualities.length, new Seq<>(qualities));
    }


    private Array(int count, Iterable<? extends Quality<? super JsonElementAdapter>> qualities)
    {
        this(new AllOf<>(new HasLength(count), new AllOf<>(new Mapped<>(numbered -> new At(numbered.left(), numbered.right()), new Numbered<>(qualities)))));
    }

    public Array(Quality<? super JsonArrayAdapter> delegate)
    {
        super(new Has<>(
            (Function<Description, Description>) orig -> new Spaced(new Text("array"), orig),
            orig -> new Spaced(new Text("array"), orig),
            JsonElementAdapter::asArray,
            present(d -> d, d -> d, new Text("not an array"), delegate)));
    }
}

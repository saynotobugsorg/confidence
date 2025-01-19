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
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Numbered;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactory;
import org.json.JSONArray;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.bifunction.Original;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.jsonstringadapter.ArrayAdapter;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.Implied;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA;

/**
 * {@link Quality} of a {@link JSONArray}.
 */
public final class JsonOrgArray extends QualityComposition<JSONArray>
{


    @StaticFactory(
        value = "JsonOrg",
        methodName = "jsonArray",
        packageName = "org.saynotobugs.confidence.json.quality"
    )
    public JsonOrgArray(java.lang.String... strings)
    {
        this(strings.length, new Mapped<>(org.saynotobugs.confidence.json.quality.String::new, new Seq<>(strings)));
    }


    @StaticFactory(
        value = "JsonOrg",
        methodName = "jsonArray",
        packageName = "org.saynotobugs.confidence.json.quality"
    )
    public JsonOrgArray(java.lang.Number... numbers)
    {
        this(numbers.length, new Mapped<>(org.saynotobugs.confidence.json.quality.Number::new, new Seq<>(numbers)));
    }


    @StaticFactory(
        value = "JsonOrg",
        methodName = "jsonArray",
        packageName = "org.saynotobugs.confidence.json.quality"
    )
    @SafeVarargs
    public JsonOrgArray(Quality<? super JsonElementAdapter>... qualities)
    {
        this(qualities.length, new Seq<>(qualities));
    }


    @StaticFactory(
        value = "JsonOrg",
        methodName = "jsonArray",
        packageName = "org.saynotobugs.confidence.json.quality"
    )
    private JsonOrgArray(int count, Iterable<? extends Quality<? super JsonElementAdapter>> qualities)
    {
        super(
            new Has<>(
                (Function<Description, Description>) orig -> orig,
                orig -> orig,
                ArrayAdapter::new,

                new Implied<>(
                    new HasLength(count),
                    new Conjunction<>(
                        new Text("["),
                        COMMA,
                        new Text("]"),
                        new Mapped<>(numbered -> new At(numbered.left(), numbered.right()), new Numbered<>(qualities)),
                        new Text("[]")))));
    }

    @StaticFactory(
        value = "JsonOrg",
        methodName = "jsonArray",
        packageName = "org.saynotobugs.confidence.json.quality"
    )
    public JsonOrgArray(Quality<? super JsonArrayAdapter> delegate)
    {
        this(new Seq<>(delegate));
    }

    @StaticFactory(
        value = "JsonOrg",
        methodName = "jsonArray",
        packageName = "org.saynotobugs.confidence.json.quality"
    )
    public JsonOrgArray(Iterable<? extends Quality<? super JsonArrayAdapter>> delegates)
    {
        super(new Has<>(
            new Original<>(),
            orig -> orig,
            ArrayAdapter::new,
            new Conjunction<>(new Text("{"), COMMA, new Text("}"), delegates, new Text("{}"))));
    }

}

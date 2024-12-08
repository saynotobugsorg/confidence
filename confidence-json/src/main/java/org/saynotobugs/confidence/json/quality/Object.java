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
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.JsonObjectAdapter;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import static org.dmfs.jems2.confidence.Jems2.present;
import static org.saynotobugs.confidence.description.LiteralDescription.COMMA;

@StaticFactories(
    value = "Json",
    packageName = "org.saynotobugs.confidence.json.quality",
    deprecates = @DeprecatedFactories(value = "Json", packageName = "org.saynotobugs.confidence.json"))
public final class Object extends QualityComposition<JsonElementAdapter>
{
    @SafeVarargs
    public Object(Quality<? super JsonObjectAdapter>... delegates)
    {
        this(new Seq<>(delegates));
    }

    public Object(Iterable<? extends Quality<? super JsonObjectAdapter>> delegates)
    {
        super(new Has<>(
            (Function<Description, Description>) orig -> orig,
            orig -> orig,
            JsonElementAdapter::asObject,
            present(d -> d, d -> d, new Text("not an object"), new Conjunction<>(new Text("{"), COMMA, new Text("}"), delegates, new Text("{}")))));
    }
}

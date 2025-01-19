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

import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactory;
import org.json.JSONObject;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.bifunction.Original;
import org.saynotobugs.confidence.json.JsonObjectAdapter;
import org.saynotobugs.confidence.json.jsonstringadapter.ObjectAdapter;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA;

/**
 * {@link Quality} of a {@link JSONObject}.
 */
public final class JsonOrgObject extends QualityComposition<JSONObject>
{
    @StaticFactory(
        value = "JsonOrg",
        methodName = "jsonObject",
        packageName = "org.saynotobugs.confidence.json.quality"
    )
    @SafeVarargs
    public JsonOrgObject(Quality<? super JsonObjectAdapter>... delegates)
    {
        this(new Seq<>(delegates));
    }

    @StaticFactory(
        value = "JsonOrg",
        methodName = "jsonObject",
        packageName = "org.saynotobugs.confidence.json.quality"
    )
    public JsonOrgObject(Iterable<? extends Quality<? super JsonObjectAdapter>> delegates)
    {
        super(new Has<>(
            new Original<>(),
            orig -> orig,
            ObjectAdapter::new,
            new Conjunction<>(new Text("{"), COMMA, new Text("}"), delegates, new Text("{}"))));
    }

}

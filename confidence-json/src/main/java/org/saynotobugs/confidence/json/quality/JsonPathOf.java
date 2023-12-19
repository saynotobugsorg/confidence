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

import io.restassured.path.json.JsonPath;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.json.jsonstringadapter.JsonStringElementAdapter;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

/**
 * {@link Quality} of a {@link JsonPath}.
 */
@StaticFactories(value = "Json", packageName = "org.saynotobugs.confidence.json")
public final class JsonPathOf extends QualityComposition<JsonPath>
{
    public JsonPathOf(Quality<? super JsonElementAdapter> delegate)
    {
        /*
         * We just convert the JSON to a String and use the JsonStringElementAdapter.
         * Repeatedly invoking the JsonPath getters is way too slow.
         */
        super(new Has<>(new Text("JsonPath"), new Text("JsonPath"), jsonPath -> new JsonStringElementAdapter(jsonPath.prettify()), delegate));
    }

}

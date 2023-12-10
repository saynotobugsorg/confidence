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

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import static org.dmfs.jems2.confidence.Jems2.present;

/**
 * {@link Quality} of a JSON string.
 */
@StaticFactories(value = "Json", packageName = "org.saynotobugs.confidence.json")
public final class String extends QualityComposition<JsonElementAdapter>
{
    public String(java.lang.String value)
    {
        this(new EqualTo<>(value));
    }

    public String(Quality<? super java.lang.String> delegate)
    {
        super(new Has<>(orig -> orig,
            orig -> orig,
            JsonElementAdapter::asString,
            present(d -> d, d -> d, new Text("not a string"), delegate)));
    }
}

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

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactory;
import org.dmfs.srcless.annotations.staticfactory.StaticFactory;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.ToStringDescription;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Satisfies;

/**
 * {@link Quality} of a JSON {@code null} value.
 */
public final class Null extends QualityComposition<JsonElementAdapter>
{
    @StaticFactory(
        value = "Json",
        packageName = "org.saynotobugs.confidence.json.quality",
        methodName = "nullValue",
        deprecates = @DeprecatedFactory(value = "Json", packageName = "org.saynotobugs.confidence.json"))
    public Null()
    {
        super(new Satisfies<>(JsonElementAdapter::isNull, ToStringDescription::new, new Text("<null>")));
    }
}

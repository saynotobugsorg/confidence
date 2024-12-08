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

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.number.CloseTo;
import org.saynotobugs.confidence.quality.object.EqualTo;

import static org.dmfs.jems2.confidence.Jems2.present;

@StaticFactories(
    value = "Json",
    packageName = "org.saynotobugs.confidence.json.quality",
    deprecates = @DeprecatedFactories(value = "Json", packageName = "org.saynotobugs.confidence.json"))
public final class Number extends QualityComposition<JsonElementAdapter>
{
    public Number(double value)
    {
        this(new CloseTo(value));
    }

    public Number(float value)
    {
        this(new CloseTo(value));
    }

    public Number(int value)
    {
        this(new EqualTo<>(value));
    }

    public Number(long value)
    {
        this(new EqualTo<>(value));
    }

    public Number(java.lang.Number value)
    {
        this(new EqualTo<>(value));
    }

    public Number(Quality<? super java.lang.Number> delegate)
    {
        super(new Has<>(orig -> orig,
            orig -> orig,
            JsonElementAdapter::asNumber,
            present(d -> d, d -> d, new Text("not a number"), delegate)));
    }
}

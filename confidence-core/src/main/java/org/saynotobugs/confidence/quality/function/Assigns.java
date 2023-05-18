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

package org.saynotobugs.confidence.quality.function;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.function.Function;


/**
 * @deprecated in favour of {@link Maps}. This Quality has it reversed, in terms of what a function assigns to what.
 */
@Deprecated
@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class Assigns<Argument, Result> extends QualityComposition<Function<Argument, Result>>
{
    public Assigns(Argument argument, Result result)
    {
        this(argument, new EqualTo<>(result));
    }


    public Assigns(Argument argument, Quality<? super Result> resultQuality)
    {
        super(new Has<>(
            new Spaced(new Text("assigns"), new Value(argument)),
            new Spaced(new Text("assigned"), new Value(argument)),
            candidate -> candidate.apply(argument),
            resultQuality
        ));
    }
}

/*
 * Copyright 2022 dmfs GmbH
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

package org.saynotobugs.confidence.mockito4.quality;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.mockito.ArgumentMatcher;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Satisfies;


@StaticFactories(value = "Mockito4", packageName = "org.saynotobugs.confidence.mockito4")
public final class Matches<T> extends QualityComposition<ArgumentMatcher<T>>
{
    /**
     * {@link Quality} of an {@link ArgumentMatcher} that matches the given argument.
     */
    public Matches(T argument)
    {
        super(new Satisfies<>(argumentMatcher -> argumentMatcher.matches(argument),
            new Spaced(new Text("matches"), new Value(argument))));
    }
}

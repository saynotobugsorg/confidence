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

package org.saynotobugs.confidence.quality.comparable;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Satisfies;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class LessThanOrEqualTo<T extends Comparable<? super T>> extends QualityComposition<T>
{

    /**
     * Creates {@link Quality} that matches if the {@link Comparable} value under test is less than or equal to the given value.
     * <p>
     * Example
     * <pre>
     *     assertThat(0, is(lessThanOrEqualTo(1)));
     * </pre>
     */
    public LessThanOrEqualTo(T expected)
    {
        super(new Satisfies<>(
            actual -> expected.compareTo(actual) >= 0,
            new Spaced(new TextDescription("less than or equal to"), new ValueDescription(expected))));
    }
}
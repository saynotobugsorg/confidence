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

package org.saynotobugs.confidence.quality.object;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.valuedescription.Value;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.grammar.Is;


@StaticFactories(
    value = "Object",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class StrictlyEqualTo<T> extends QualityComposition<T>
{
    /**
     * A {@link Quality} that is satisfied if the value under test is strictly equal to the given value.
     * <p>
     * "Strictly equal" means the object satisfies the equals contract as described in {@link Object#equals(Object)}.
     * This is intended to test implementation of the equals contract, not for general testing for equality and may result
     * in unexpected outcomes, especially when used with negation.
     * <p>
     * Note, this {@link Quality} does not support arrays.
     */
    public StrictlyEqualTo(T expected)
    {
        super(new AllOf<>(
            new Is<>(new Satisfies<>(actual -> actual.equals(actual), any -> new Text("not reflexive"), new Text("reflexive"))),
            new Is<>(new Satisfies<>(actual -> !actual.equals(null), any -> new Text("equal to null"), new Text("not equal to null"))),
            new Is<>(new Satisfies<>(expected::equals, any -> new Spaced(new Text("not equal to"), new Value(expected)), new Spaced(new Text("equal to"), new Value(expected)))),
            new Is<>(new Satisfies<>(actual -> actual.equals(expected), any -> new Text("not symmetric"), new Text("symmetric"))),
            new HashCodeEquals(expected)));
    }
}

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

package org.saynotobugs.confidence.quality.object;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.Just;
import org.saynotobugs.confidence.description.bifunction.TextAndValue;
import org.saynotobugs.confidence.quality.composite.AllOfFailingFast;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.QualityComposition;


@StaticFactories(
    value = "Object",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class InstanceOf<T> extends QualityComposition<T>
{
    /**
     * A {@link Quality} that is satisfied when the object under test is an instance of the given class and satisfies
     * the given {@link Quality}.
     * <p>
     * This provides a type-safe way to downcast and apply a {@link Quality} of a subtype.
     *
     * <h4>Example</h4>
     * <pre>{@code
     * assertThat(someObject,
     *     is(instanceOf(Number.class, that(has(Number::intValue, equalTo(1))))));
     * }</pre>
     *
     * @see UnsafeInstanceOf#UnsafeInstanceOf(Class, Quality) for testing generic classes.
     */
    public <V extends T> InstanceOf(Class<? extends V> expectation, Quality<? super V> delegate)
    {
        super((Quality<T>) new AllOfFailingFast<>(new InstanceOf<>(expectation), delegate));
    }

    /**
     * A {@link Quality} that matches when the object under test is an instance of the given class.
     */
    public <V extends T> InstanceOf(Class<? extends V> expectation)
    {
        super(
            new DescribedAs<>(
                new TextAndValue<>("instance of", v -> new Value(v.getClass())),
                new TextAndValue<>("instance of", v -> new Value(v.getClass())),
                new Just<>(new Spaced(new Text("instance of"), new Value(expectation))),
                new Satisfies<>(expectation::isInstance)));
    }
}

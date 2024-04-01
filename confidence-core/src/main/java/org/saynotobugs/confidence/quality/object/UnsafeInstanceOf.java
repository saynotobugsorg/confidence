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
import org.saynotobugs.confidence.quality.composite.AllOfFailingFast;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


@StaticFactories(
    value = "Object",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class UnsafeInstanceOf<T> extends QualityComposition<T>
{
    /**
     * A {@link Quality} that matches when the object under test is an instance of any subclass of the given class and
     * satisfies the given {@link Quality}.
     * <p>
     * This works like {@link InstanceOf} but provides fewer type-safety guarantees allowing you to pass
     * {@link Quality} of subtypes of {@code V}. This may be required when testing generic classes, because you'll
     * essentially be forced to work with raw types in such case.
     *
     * <h4>Example</h4>
     * <pre>{@code
     * Map actual = ...;
     *
     * assertThat(actual, unsafeInstanceOf(Map.class, Core.<Map<String, Object>>allOf(
     *     containsEntry("k1", unsafeInstanceOf(Map.class, allOf(
     *         containsEntry("k11", "v11"),
     *         containsEntry("k12", "v12")))),
     *     containsEntry("k2", unsafeInstanceOf(Iterable.class, iterates("v21", "v22"))),
     *     containsEntry("k3", unsafeInstanceOf(String.class, equalTo("v3"))))));
     * }</pre>
     * <p>
     * <p>
     * Be aware that this also allows you to write nonsensical tests like this:
     * <pre>{@code
     *     static class C1 {}
     *
     *     static class C2 extends C1 {
     *         int bar() {return 2;}
     *     }
     *
     *     @Test
     *     void instanceOfTest() {
     *         Object o = new C1();
     *         assertThat(o, unsafeInstanceOf(C1.class, has(C2::bar, equalTo(2))));
     *     }
     * }</pre>
     * <p>
     * In such case, when the Quality is not compatible with the actual type, the test will fail, reporting a
     * {@link ClassCastException}.
     */
    public <V extends T, Q extends V> UnsafeInstanceOf(Class<V> expectation, Quality<? super Q> delegate)
    {
        super((Quality<T>) new AllOfFailingFast<>(NEW_LINE, new InstanceOf<>(expectation), delegate));
    }
}

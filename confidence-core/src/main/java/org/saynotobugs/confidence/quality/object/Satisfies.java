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

import org.dmfs.jems2.Function;
import org.dmfs.jems2.Predicate;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.PassIf;
import org.saynotobugs.confidence.description.valuedescription.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;


@StaticFactories(
    value = "Object",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class Satisfies<T> extends QualityComposition<T>
{
    public Satisfies(
        Predicate<? super T> predicate,
        Description matchDescription)
    {
        super(actual -> new PassIf(predicate.satisfiedBy(actual), () -> new Value(actual)),
            matchDescription);
    }


    /**
     * Creates a {@link Quality} of an instance that satisfies the given {@link Predicate}.
     * <p>
     * Example
     * <pre>
     * assertThat("",
     *     satisfies(
     *         String::isEmpty,
     *         s-&gt;new Delimited(new ValueDescription(s), new TextDescription("was not empty")),
     *         new TextDescription("is empty")));
     * </pre>
     */
    public Satisfies(
        Predicate<? super T> predicate,
        Function<? super T, ? extends Description> mismatchDescriptionFunction,
        Description matchDescription)
    {
        super(actual -> new PassIf(predicate.satisfiedBy(actual), () -> mismatchDescriptionFunction.value(actual)),
            matchDescription);
    }
}

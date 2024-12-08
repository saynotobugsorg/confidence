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

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.assessment.PassIf;
import org.saynotobugs.confidence.description.Block;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.utils.ArrayIterable;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


@StaticFactories(
    value = "Object",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class EqualTo<T> extends QualityComposition<T>
{
    /**
     * Creates a {@link Quality} that matches if the value under test is equal to the given value.
     */
    public EqualTo(T expected)
    {
        super(actual -> expected.getClass().isArray() && actual.getClass().isArray()
                ? new DescriptionUpdated(
                new TextAndOriginal<>("array that"),
                new Iterates<>(new Mapped<>(EqualTo::new, new ArrayIterable(expected))).assessmentOf(new ArrayIterable(actual)))
                : new PassIf(expected.equals(actual), new Value(actual), new Value(actual)),
            expected.getClass().isArray()
                ? new Block(new Text("["), EMPTY, new Text("]"), new Mapped<>(Value::new, new ArrayIterable(expected)))
                : new Value(expected));
    }
}

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

package org.saynotobugs.confidence.quality.number;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Satisfies;

import java.math.BigDecimal;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class CloseTo extends QualityComposition<Number>
{
    public CloseTo(Number expectation, Number epsilon)
    {
        this(new BigDecimal(expectation.toString()), new BigDecimal(epsilon.toString()));
    }


    public CloseTo(BigDecimal expectation, BigDecimal epsilon)
    {
        super(new Satisfies<>(
            actual -> expectation.subtract(new BigDecimal(actual.toString())).abs().compareTo(epsilon) <= 0,
            actual -> new Spaced(
                new Value(actual),
                new Text("differs from"),
                new Value(expectation),
                new Text("by"),
                new Composite(
                    new Value(expectation.subtract(new BigDecimal(actual.toString())).abs()),
                    new Text(", which is more than the allowed")),
                new Value(epsilon)),
            new Spaced(
                new Text("differs from"),
                new Value(expectation),
                new Text("by no more than"),
                new Value(epsilon))));
    }
}

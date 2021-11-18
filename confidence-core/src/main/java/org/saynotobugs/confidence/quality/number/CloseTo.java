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
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
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
            actual -> new Delimited(
                new ValueDescription(actual),
                new TextDescription("differs from"),
                new ValueDescription(expectation),
                new TextDescription("by"),
                new Composite(
                    new ValueDescription(expectation.subtract(new BigDecimal(actual.toString())).abs()),
                    new TextDescription(", which is more than the allowed")),
                new ValueDescription(epsilon)),
            new Delimited(
                new TextDescription("differs from"),
                new ValueDescription(expectation),
                new TextDescription("by no more than"),
                new ValueDescription(epsilon))));
    }
}

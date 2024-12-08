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

package org.saynotobugs.confidence.quality.number;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.Just;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Satisfies;

import java.math.BigDecimal;


@StaticFactories(
    value = "Number",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class CloseTo extends QualityComposition<Number>
{
    /**
     * A {@link Quality} of a {@link Number} in that's within the given {@code ε} of one ulp as determined by {@link Math#ulp(double)}.,
     * i.e. any number {@code x} with {@code expectation - ulp(expectation) < x < expectation + ulp(expectation)}
     */
    public CloseTo(double expectation)
    {
        this(expectation, 1);
    }


    /**
     * A {@link Quality} of a {@link Number} in that's within the given {@code ε} of {@code ulpCount} ulp as determined by {@link Math#ulp(double)}.,
     * i.e. any number {@code x} with {@code expectation - ulpCount * ulp(expectation) < x < expectation + ulpCount * ulp(expectation)}
     */
    public CloseTo(double expectation, int ulpCount)
    {
        this(expectation, ulpCount * Math.ulp(expectation));
    }

    /**
     * A {@link Quality} of a {@link Number} in that's within the given {@code ε} of one ulp as determined by {@link Math#ulp(float)}.,
     * i.e. any number {@code x} with {@code expectation - ulp(expectation) < x < expectation + ulp(expectation)}
     */
    public CloseTo(float expectation)
    {
        this(expectation, 1);
    }

    /**
     * A {@link Quality} of a {@link Number} in that's within the given {@code ε} of {@code ulpCount} ulp as determined by {@link Math#ulp(float)}.,
     * i.e. any number {@code x} with {@code expectation - ulpCount * ulp(expectation) < x < expectation + ulpCount * ulp(expectation)}
     */
    public CloseTo(float expectation, int ulpCount)
    {
        this(expectation, ulpCount * Math.ulp(expectation));
    }

    /**
     * A {@link Quality} of a {@link Number} in that's within the given {@code ε} of the given {@link Number}, i.e. any number {@code x}
     * with {@code expectation - ε < x < expectation + ε}
     */
    public CloseTo(Number expectation, Number ε)
    {
        this(new BigDecimal(expectation.toString()), new BigDecimal(ε.toString()));
    }


    public CloseTo(BigDecimal expectation, BigDecimal ε)
    {
        super(
            new DescribedAs<>(
                (actual, originalDescription) -> new Spaced(
                    new Value(actual),
                    new Text("differed from"),
                    new Value(expectation),
                    new Text("by"),
                    new Composite(
                        new Value(expectation.subtract(new BigDecimal(actual.toString())).abs()),
                        new Text(", which was less than")),
                    new Value(ε)),
                (actual, originalDescription) -> new Spaced(
                    new Value(actual),
                    new Text("differed from"),
                    new Value(expectation),
                    new Text("by"),
                    new Composite(
                        new Value(expectation.subtract(new BigDecimal(actual.toString())).abs()),
                        new Text(", which exceeded the ε of")),
                    new Value(ε)),
                new Just<>(new Spaced(
                    new Text("differs from"),
                    new Value(expectation),
                    new Text("by less than"),
                    new Value(ε))),
                new Satisfies<>(actual -> expectation.subtract(new BigDecimal(actual.toString())).abs().compareTo(ε) < 0)));
    }
}

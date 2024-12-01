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

package org.saynotobugs.confidence.quality.composite;

import org.dmfs.jems2.Function;
import org.dmfs.jems2.ThrowingFunction;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.Original;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.utils.FailSafe;


@StaticFactories(
    value = "Composite",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class Has<T, V> extends QualityComposition<T>
{
    public Has(ThrowingFunction<? super T, ? extends V> featureFunction, V value)
    {
        this(new Original<>(),
            new Original<>(),
            featureFunction,
            new EqualTo<>(value));
    }


    public Has(ThrowingFunction<? super T, ? extends V> featureFunction, Quality<? super V> delegate)
    {
        this(new Original<>(),
            new Original<>(),
            featureFunction,
            delegate);
    }


    public Has(String featureName, ThrowingFunction<? super T, ? extends V> featureFunction, V value)
    {
        this(new Spaced(new Text("has"), new Text(featureName)),
            new Spaced(new Text("had"), new Text(featureName)),
            featureFunction,
            new EqualTo<>(value));
    }


    public Has(String featureName, ThrowingFunction<? super T, ? extends V> featureFunction, Quality<? super V> delegate)
    {
        this(new Spaced(new Text("has"), new Text(featureName)),
            new Spaced(new Text("had"), new Text(featureName)),
            featureFunction,
            delegate);
    }


    public Has(Description simplePresentDescription,
        Description simplePastDescription,
        ThrowingFunction<? super T, ? extends V> featureFunction,
        Quality<? super V> delegate)
    {
        this(new TextAndOriginal<>(simplePresentDescription),
            new TextAndOriginal<>(simplePastDescription),
            featureFunction,
            delegate
        );
    }


    public Has(Function<Description, Description> simplePresentDescriptionFunction,
        Function<Description, Description> simplePastDescriptionFunction,
        ThrowingFunction<? super T, ? extends V> featureFunction,
        Quality<? super V> delegate)
    {
        this(simplePresentDescriptionFunction,
            simplePastDescriptionFunction,
            throwable -> new Spaced(new Text("threw"), new Value(throwable)),
            featureFunction,
            delegate);
    }


    public Has(Function<Description, Description> simplePresentDescriptionFunction,
        Function<Description, Description> simplePastDescriptionFunction,
        Function<? super Throwable, ? extends Description> throwsDescriptionFunction,
        ThrowingFunction<? super T, ? extends V> featureFunction,
        Quality<? super V> delegate)
    {
        super(new FailSafe<>(
                throwable -> new Fail(throwsDescriptionFunction.value(throwable)),
                actual -> new DescriptionUpdated(simplePastDescriptionFunction, simplePastDescriptionFunction, delegate.assessmentOf(featureFunction.value(actual)))),
            simplePresentDescriptionFunction.value(delegate.description()));
    }
}

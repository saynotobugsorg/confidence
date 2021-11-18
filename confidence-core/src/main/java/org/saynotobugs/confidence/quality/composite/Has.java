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
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.TextDescription;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class Has<T, V> extends QualityComposition<T>
{

    public Has(Function<? super T, ? extends V> featureFunction, Quality<? super V> delegate)
    {
        super(actual -> delegate.assessmentOf(featureFunction.value(actual)),
            delegate.description());
    }


    public Has(String featureName, Function<? super T, ? extends V> featureFunction, Quality<? super V> delegate)
    {
        this(new Delimited(new TextDescription("has"), new TextDescription(featureName)),
            new Delimited(new TextDescription("had"), new TextDescription(featureName)),
            featureFunction,
            delegate);
    }


    public Has(Description featureDescription,
        Description featureMismatchDescription,
        Function<? super T, ? extends V> featureFunction,
        Quality<? super V> delegate)
    {
        super(actual -> new FailPrepended(featureMismatchDescription, delegate.assessmentOf(featureFunction.value(actual))),
            new Delimited(featureDescription, delegate.description()));
    }


    public Has(Function<Description, Description> featureDescription,
        Function<Description, Description> featureMismatchDescription,
        Function<? super T, ? extends V> featureFunction,
        Quality<? super V> delegate)
    {
        super(actual -> new FailUpdated(featureMismatchDescription, delegate.assessmentOf(featureFunction.value(actual))),
            featureDescription.value(delegate.description()));
    }
}

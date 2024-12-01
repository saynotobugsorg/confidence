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

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.Function;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.assessment.FailUpdated;


@StaticFactories(
    value = "Composite",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class DescribedAs<T> extends QualityComposition<T>
{
    public DescribedAs(
        Function<Description, ? extends Description> descriptionUpdate,
        Quality<T> delegate)
    {
        this(descriptionUpdate, descriptionUpdate, delegate);
    }


    public DescribedAs(
        Function<Description, ? extends Description> mismatchUpdate,
        Function<Description, ? extends Description> expectationUpdate,
        Quality<T> delegate)
    {
        super(
            actual -> new FailUpdated(mismatchUpdate, delegate.assessmentOf(actual)),
            expectationUpdate.value(delegate.description()));
    }


    public DescribedAs(
        BiFunction<? super T, Description, ? extends Description> mismatchUpdate,
        Function<Description, ? extends Description> expectationUpdate,
        Quality<T> delegate)
    {
        super(
            actual -> new FailUpdated(description -> mismatchUpdate.value(actual, description), delegate.assessmentOf(actual)),
            expectationUpdate.value(delegate.description()));
    }


    public DescribedAs(
        BiFunction<? super T, Description, ? extends Description> matchUpdate,
        BiFunction<? super T, Description, ? extends Description> mismatchUpdate,
        Function<Description, ? extends Description> expectationUpdate,
        Quality<T> delegate)
    {
        super(
            actual -> new DescriptionUpdated(
                description -> matchUpdate.value(actual, description),
                description -> mismatchUpdate.value(actual, description),
                delegate.assessmentOf(actual)),
            expectationUpdate.value(delegate.description()));
    }
}

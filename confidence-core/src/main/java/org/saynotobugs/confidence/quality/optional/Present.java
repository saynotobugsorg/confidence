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

package org.saynotobugs.confidence.quality.optional;

import org.dmfs.jems2.Function;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.description.Enclosed;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.Optional;


@StaticFactories(
    value = "Optional",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class Present<T> extends QualityComposition<Optional<T>>
{
    /**
     * Matches present {@link Optional}s ith any value.
     */
    public Present()
    {
        this(new Anything());
    }


    /**
     * Matches present {@link Optional}s with a value that's equal to the given one.
     */
    public Present(T value)
    {
        this(new EqualTo<>(value));
    }


    /**
     * Matches present {@link Optional}s with a value that matches the given matcher.
     */
    public Present(Quality<? super T> delegate)
    {
        this(description -> new Enclosed("<", new Spaced(new Text("present"), description), ">"),
            description -> new Enclosed("<", new Spaced(new Text("present"), description), ">"),
            delegate);
    }


    /**
     * Matches present {@link Optional}s with a value that matches the given matcher.
     */
    public Present(Function<? super Description, ? extends Description> expectationDescription,
        Function<? super Description, ? extends Description> failDescription,
        Quality<? super T> delegate)
    {
        super(actual -> actual.isPresent()
                ? new FailUpdated(failDescription, delegate.assessmentOf(actual.get()))
                : new Fail(new Value(actual)),
            expectationDescription.value(delegate.description()));
    }
}

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

package org.saynotobugs.confidence.test.quality;

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.description.*;
import org.saynotobugs.confidence.description.valuedescription.DescriptionDescription;
import org.saynotobugs.confidence.description.valuedescription.Value;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


@StaticFactories(
    value = "Quality",
    deprecates = @DeprecatedFactories(value = "Test"))
public final class Passes<T> implements Quality<Quality<? super T>>
{
    private final Iterable<? extends T> mMatchingValues;


    @SafeVarargs
    public Passes(T... matchingValues)
    {
        this(new Seq<>(matchingValues));
    }


    public Passes(Iterable<? extends T> matchingValues)
    {
        mMatchingValues = matchingValues;
    }


    @Override
    public Assessment assessmentOf(Quality<? super T> candidate)
    {
        return new AllPassed(new Text("matched"), new Composite(NEW_LINE, new Text("and")), EMPTY,
            new Mapped<>(
                value -> new FailUpdated(
                    orig -> new Spaced(new Value(value), new Text("mismatched with"), new DescriptionDescription(orig)),
                    candidate.assessmentOf(value)),
                mMatchingValues
            ));
    }


    @Override
    public Description description()
    {
        return new Structured(new Text("matches "), new Composite(NEW_LINE, new Text("and"), NEW_LINE), EMPTY,
            new Mapped<>(Value::new, mMatchingValues));
    }
}

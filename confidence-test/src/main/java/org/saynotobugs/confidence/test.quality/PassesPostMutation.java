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

package org.saynotobugs.confidence.test.quality;

import org.dmfs.jems2.Generator;
import org.dmfs.jems2.Procedure;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.description.DescriptionDescription;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;

import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


@StaticFactories(
    value = "Quality",
    deprecates = @DeprecatedFactories(value = "Test"))
public final class PassesPostMutation<T> implements Quality<Quality<? super T>>
{
    private final Generator<T> mMatchingValue;
    private final Description mMutationDescription;
    private final Procedure<? super T> mMutation;


    public PassesPostMutation(Generator<T> matchingValue, Description mutationDescription, Procedure<? super T> mutation)
    {
        mMatchingValue = matchingValue;
        mMutationDescription = mutationDescription;
        mMutation = mutation;
    }


    @Override
    public Assessment assessmentOf(Quality<? super T> candidate)
    {
        T value = mMatchingValue.next();
        Assessment assessment = candidate.assessmentOf(value);
        mMutation.process(value);

        return new FailUpdated(
            orig -> new Spaced(
                new Value(mMatchingValue.next()),
                new Text("mismatched with"),
                new DescriptionDescription(orig),
                NEW_LINE,
                new Text("after"),
                mMutationDescription),
            assessment);
    }


    @Override
    public Description description()
    {
        return new Spaced(new Text("matches"), new Value(mMatchingValue.next()), new Text("after"), mMutationDescription);
    }
}

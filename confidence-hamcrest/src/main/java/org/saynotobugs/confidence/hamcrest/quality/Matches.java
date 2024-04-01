/*
 * Copyright 2024 dmfs GmbH
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

package org.saynotobugs.confidence.hamcrest.quality;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.hamcrest.StringDescription;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Text;


/**
 * A {@link Quality} of {@code T} that delegates to a Hamcrest {@link org.hamcrest.Matcher}.
 */
@StaticFactories(
    value = "Adapter",
    packageName = "org.saynotobugs.confidence.hamcrest.quality",
    deprecates = @DeprecatedFactories(value = "Hamcrest", packageName = "org.saynotobugs.confidence.hamcrest"))
public final class Matches<T> implements Quality<T>
{
    private final org.hamcrest.Matcher<? super T> mDelegate;


    public Matches(org.hamcrest.Matcher<? super T> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Assessment assessmentOf(T candidate)
    {
        if (mDelegate.matches(candidate))
        {
            return new Pass();
        }
        org.hamcrest.Description mismatch = new StringDescription();
        mDelegate.describeMismatch(candidate, mismatch);
        return new Fail(new Text(mismatch.toString()));
    }


    @Override
    public Description description()
    {
        org.hamcrest.Description expected = new StringDescription();
        mDelegate.describeTo(expected);
        return new Text(expected.toString());
    }

}

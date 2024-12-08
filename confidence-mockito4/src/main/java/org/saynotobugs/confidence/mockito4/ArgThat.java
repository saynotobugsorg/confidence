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

package org.saynotobugs.confidence.mockito4;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.mockito.ArgumentMatcher;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.scribe.StringBuilderScribe;


/**
 * An {@link ArgumentMatcher} that matches when the tested argument has the given {@link Quality}.
 */
@StaticFactories(value = "Mockito4", packageName = "org.saynotobugs.confidence.mockito4")
public final class ArgThat<T> implements ArgumentMatcher<T>
{
    private final Quality<? super T> mDelegate;


    public ArgThat(Quality<? super T> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public boolean matches(T argument)
    {
        return mDelegate.assessmentOf(argument).isSuccess();
    }


    @Override
    public String toString()
    {
        StringBuilderScribe sb = new StringBuilderScribe("  ");
        mDelegate.description().describeTo(sb);
        return "ArgThat " + sb;
    }
}

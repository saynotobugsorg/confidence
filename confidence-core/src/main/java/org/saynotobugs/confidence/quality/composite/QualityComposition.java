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
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;


/**
 * A {@link Quality} for easy composition.
 */
public abstract class QualityComposition<T> implements Quality<T>
{
    private final Quality<T> mDelegate;


    public QualityComposition(Function<? super T, ? extends Assessment> testFunction, Description expectation)
    {
        this(new Quality<T>()
        {
            @Override
            public Assessment assessmentOf(T candidate)
            {
                return testFunction.value(candidate);
            }


            @Override
            public Description description()
            {
                return expectation;
            }
        });
    }


    public QualityComposition(Quality<T> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public final Assessment assessmentOf(T candidate)
    {
        return mDelegate.assessmentOf(candidate);
    }


    @Override
    public final Description description()
    {
        return mDelegate.description();
    }
}

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

package org.saynotobugs.confidence.quality.function;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;

import java.util.function.Function;

/**
 * This quality can be used to isolate the logic of the outer function in a delegating function,
 * which enables testing only the actual functions logic, not the logic of the delegate function.
 *
 * <pre>
 * OuterFunction
 * +-----------------------------------------------------------------------------------+
 * |                      DelegateFunction                                             |
 * |                     +------------------------------------------+                  |
 * | OuterArgument ----&#62; |  DelegateArgument -----&#62; DelegateResult  | ---&#62; OuterResult |
 * |                     +------------------------------------------+                  |
 * +-----------------------------------------------------------------------------------+
 * </pre>
 *
 * <b>Example:</b>
 * <pre>
 * assertThat(delegate -&#62; x -&#62; delegate.apply(0) + 3,
 *     new ResultOf&#60;Integer, Integer, Integer&#62;(10, new Maps&#60;&#62;(0, 13))
 * );
 * </pre>
 */
@StaticFactories(
    value = "Function",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class ResultOf<In, DelegateIn, Out> implements Quality<Function<Function<DelegateIn, Out>, Function<In, Out>>>
{
    private final Out mDelegateResult;
    private final Quality<? super Function<In, Out>> mDelegate;

    /**
     * Creates a new instance, with <code>delegateResult</code> being the result of the delegate inside the outer function,
     * and a quality <code>delegate</code> for the result of the outer function.
     */
    public ResultOf(Out delegateResult, Quality<? super Function<In, Out>> delegate)
    {
        mDelegateResult = delegateResult;
        mDelegate = delegate;
    }

    @Override
    public Assessment assessmentOf(Function<Function<DelegateIn, Out>, Function<In, Out>> candidate)
    {
        return new FailUpdated(description -> new Spaced(
            new Text("delegate which outputs"),
            new Value(mDelegateResult),
            new Text("resulted in outer function"),
            description
        ), mDelegate.assessmentOf(candidate.apply(delegateIn -> mDelegateResult)));
    }

    @Override
    public Description description()
    {
        return new Spaced(
            new Text("delegate which outputs"),
            new Value(mDelegateResult),
            new Text("resulting in outer function that"),
            mDelegate.description()
        );
    }
}

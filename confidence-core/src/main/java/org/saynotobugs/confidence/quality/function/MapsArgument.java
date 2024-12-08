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
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;

import java.util.concurrent.atomic.AtomicReference;
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
 * assertThat(delegate -&#62; x -&#62; delegate.apply(x + 2),
 *     new MapsArgument&#60;Integer, Integer, Integer&#62;(1, equalTo(3))
 * );
 * </pre>
 */
@StaticFactories(
    value = "Function",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class MapsArgument<In, DelegateIn, Out> implements Quality<Function<Function<DelegateIn, Out>, Function<In, Out>>>
{
    private final In mOuterArgument;
    private final Quality<? super DelegateIn> mDelegateArgumentQuality;

    /**
     * Creates a new instance with <code>outerArgument</code> as the input to the outer function
     * and the <code>delegateArgumentQuality</code> as quality matching the delegate argument.
     */
    public MapsArgument(In outerArgument, Quality<? super DelegateIn> delegateArgumentQuality)
    {
        mOuterArgument = outerArgument;
        mDelegateArgumentQuality = delegateArgumentQuality;
    }

    @Override
    public Assessment assessmentOf(Function<Function<DelegateIn, Out>, Function<In, Out>> candidate)
    {
        final AtomicReference<DelegateIn> delegateValueReference = new AtomicReference<>();

        final Function<In, Out> actualFunction = candidate.apply(delegateValue -> {
            delegateValueReference.set(delegateValue);
            throw new MapsArgumentExpectedError();
        });

        try
        {
            actualFunction.apply(mOuterArgument);
        }
        catch (MapsArgumentExpectedError ignored)
        {
        }

        if (delegateValueReference.get() != null)
        {
            return new FailUpdated(description -> new Spaced(
                new Text("function passed"),
                description,
                new Text("to delegate function for outer argument"),
                new Value(mOuterArgument)
            ), mDelegateArgumentQuality.assessmentOf(delegateValueReference.get()));
        }

        return new Fail(new Text("function did not call delegate"));
    }

    @Override
    public Description description()
    {
        return new Spaced(
            new Text("function passing"),
            mDelegateArgumentQuality.description(),
            new Text("to delegate function for outer argument"),
            new Value(mOuterArgument)
        );
    }

    private static final class MapsArgumentExpectedError extends Error
    {
        // error used to interrupt execution before delegate is called
    }
}

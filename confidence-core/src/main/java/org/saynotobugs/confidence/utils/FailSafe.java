/*
 * Copyright 2023 dmfs GmbH
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

package org.saynotobugs.confidence.utils;

import org.dmfs.jems2.Function;
import org.dmfs.jems2.ThrowingFunction;


/**
 * A {@link ThrowingFunction} to {@link Function} adapter that always returns a result, even in case a {@link Throwable} was thrown.
 */
public final class FailSafe<Argument, Result> implements Function<Argument, Result>, java.util.function.Function<Argument, Result>
{
    private final ThrowingFunction<Argument, Result> delegate;
    private final Function<Throwable, Result> fallback;


    public FailSafe(
        Function<Throwable, Result> fallback,
        ThrowingFunction<Argument, Result> delegate)
    {
        this.delegate = delegate;
        this.fallback = fallback;
    }


    @Override
    public Result value(Argument argument)
    {
        try
        {
            return delegate.value(argument);
        }
        catch (Throwable e)
        {
            return fallback.value(e);
        }
    }

    @Override
    public Result apply(Argument argument)
    {
        return value(argument);
    }
}

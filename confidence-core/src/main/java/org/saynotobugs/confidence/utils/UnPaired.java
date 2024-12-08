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

package org.saynotobugs.confidence.utils;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.Function;
import org.dmfs.jems2.Pair;


/**
 * Un-pairs the {@link Pair} argument of a function.
 * <p>
 * TODO: move to jems.
 */
public final class UnPaired<Left, Right, Result> implements Function<Pair<Left, Right>, Result>
{

    private final BiFunction<? super Left, ? super Right, ? extends Result> mDelegate;


    public UnPaired(BiFunction<? super Left, ? super Right, ? extends Result> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Result value(Pair<Left, Right> pair)
    {
        return mDelegate.value(pair.left(), pair.right());
    }
}

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

package org.saynotobugs.confidence.utils;

import org.dmfs.jems2.Procedure;


public final class Intermittent<T> implements Procedure<Iterable<? extends T>>
{
    private final Runnable mBetween;
    private final Procedure<? super T> mEach;


    public Intermittent(Runnable between, Procedure<? super T> each)
    {
        mBetween = between;
        mEach = each;
    }


    @Override
    public void process(Iterable<? extends T> arg)
    {
        boolean first = true;
        for (T a : arg)
        {
            if (first)
            {
                first = false;
            }
            else
            {
                mBetween.run();
            }
            mEach.process(a);
        }
    }
}

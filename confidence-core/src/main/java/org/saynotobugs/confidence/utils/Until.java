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


import org.dmfs.jems2.Predicate;
import org.dmfs.jems2.iterator.BaseIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TODO: move to jems
 */
public final class Until<T> implements Iterable<T>
{
    private final Predicate<? super T> mPredicate;
    private final Iterable<? extends T> mDelegate;

    public Until(Predicate<? super T> predicate, Iterable<? extends T> delegate)
    {
        mPredicate = predicate;
        mDelegate = delegate;
    }

    @Override
    public Iterator<T> iterator()
    {
        Iterator<? extends T> delegate = mDelegate.iterator();
        return new BaseIterator<T>()
        {
            public T mNext;
            private boolean mDeterminedNext;
            private boolean mDone;

            public boolean hasNext()
            {
                return this.moveToNext();
            }

            public T next()
            {
                if (!this.moveToNext())
                {
                    throw new NoSuchElementException("No more elements to iterate");
                }
                else
                {
                    this.mDeterminedNext = false;
                    return this.mNext;
                }
            }

            private boolean moveToNext()
            {
                if (!this.mDone && !this.mDeterminedNext)
                {
                    if (delegate.hasNext())
                    {
                        T next = delegate.next();
                        if (!mPredicate.satisfiedBy(next))
                        {
                            mNext = next;
                            mDeterminedNext = true;
                            return true;
                        }
                        else
                        {
                            mNext = next;
                            mDeterminedNext = true;
                            mDone = true;
                            return true;
                        }
                    }

                    this.mDone = true;
                    return false;
                }
                else
                {
                    return !this.mDone || mDeterminedNext;
                }
            }
        }
            ;
    }
}

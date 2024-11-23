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

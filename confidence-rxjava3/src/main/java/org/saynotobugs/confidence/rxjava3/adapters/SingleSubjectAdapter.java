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

package org.saynotobugs.confidence.rxjava3.adapters;

import io.reactivex.rxjava3.subjects.SingleSubject;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;


/**
 * An {@link RxSubjectAdapter} to a {@link SingleSubject}. Calls to {@link #onNext(Object)} are delegated to {@link SingleSubject#onSuccess(Object)},
 * which automatically "completes" the subject. On the other hand, {@link SingleSubject}s can not complete without a value, so calls to
 * {@link #onComplete()} throw an {@link UnsupportedOperationException}.
 */
public final class SingleSubjectAdapter<T> implements RxSubjectAdapter<T>
{
    private final SingleSubject<? super T> mDelegate;


    public SingleSubjectAdapter(SingleSubject<? super T> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public void onNext(T next)
    {
        mDelegate.onSuccess(next);
    }


    @Override
    public void onComplete()
    {
        throw new UnsupportedOperationException("SingleSubjectAdapter.onComplete() called, but Singles require a value");
    }

    @Override
    public void onError(Throwable error)
    {
        mDelegate.onError(error);
    }

    @Override
    public boolean hasSubscribers()
    {
        return mDelegate.hasObservers();
    }
}

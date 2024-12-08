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

import io.reactivex.rxjava3.subjects.CompletableSubject;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;


/**
 * An {@link RxSubjectAdapter} to a {@link CompletableSubject}. {@link CompletableSubject}s don't have values,
 * so calls to {@link #onNext(Object)} throw an {@link UnsupportedOperationException}.
 */
public final class CompletableSubjectAdapter<T> implements RxSubjectAdapter<T>
{
    private final CompletableSubject mDelegate;


    public CompletableSubjectAdapter(CompletableSubject delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public void onNext(T next)
    {
        throw new UnsupportedOperationException(
            String.format("CompletableSubjectAdapter.onNext() called with %s, but Completables don't take any values.", next));
    }


    @Override
    public void onComplete()
    {
        mDelegate.onComplete();
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

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

import io.reactivex.rxjava3.core.Emitter;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;

public final class EmitterAdapter<T> implements RxSubjectAdapter<T>
{
    private final Emitter<T> mEmitter;

    public EmitterAdapter(Emitter<T> emitter)
    {
        mEmitter = emitter;
    }

    @Override
    public void onNext(T next)
    {
        mEmitter.onNext(next);
    }

    @Override
    public void onComplete()
    {
        mEmitter.onComplete();
    }

    @Override
    public void onError(Throwable error)
    {
        mEmitter.onError(error);
    }

    @Override
    public boolean hasSubscribers()
    {
        return false; // we don't really know
    }

}

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

import io.reactivex.rxjava3.observers.TestObserver;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public final class RxTestObserver<T> extends TestObserver<T> implements RxTestAdapter<T>
{
    private final AtomicInteger mAcknowledged = new AtomicInteger(0);


    @Override
    public long completions()
    {
        return completions;
    }


    @Override
    public Collection<T> newValues(int count)
    {
        int valueCount = Math.min(count, values.size() - mAcknowledged.get());
        List<T> newValues = new ArrayList<>(valueCount);
        newValues.addAll(values.subList(mAcknowledged.get(), mAcknowledged.get() + valueCount));
        return newValues;
    }


    @Override
    public void awaitNext(int count)
    {
        awaitCount(mAcknowledged.get() + count);
    }


    @Override
    public void ack(int count)
    {
        mAcknowledged.addAndGet(count);
    }


    @Override
    public Iterable<Throwable> errors()
    {
        return new ArrayList<>(errors);
    }


    @Override
    public boolean isCancelled()
    {
        return super.isDisposed();
    }

    @Override
    public void cancel()
    {
        dispose();
    }
}

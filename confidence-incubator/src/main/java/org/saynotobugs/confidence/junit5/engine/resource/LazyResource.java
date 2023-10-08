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

package org.saynotobugs.confidence.junit5.engine.resource;

import org.dmfs.jems2.Fragile;
import org.dmfs.jems2.FragileBiProcedure;
import org.dmfs.jems2.FragileFunction;
import org.dmfs.jems2.FragileProcedure;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.junit5.engine.Resource;
import org.saynotobugs.confidence.junit5.engine.ResourceHandle;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@StaticFactories(value = "Resources", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class LazyResource<T> implements Resource<T>
{
    private interface Breakable
    {
        void run() throws Exception;
    }

    private final AtomicInteger mRefCount = new AtomicInteger(0);
    private Fragile<T, Exception> mResourceSource;
    private Breakable mCleanUp = () -> {};

    /**
     * Constructor of a {@link Resource} that does not need a separate context.
     */
    public LazyResource(Fragile<T, Exception> resourceSource, FragileProcedure<? super T, Exception> cleanUp)
    {
        this(resourceSource, context -> context, (resourceValue, context) -> cleanUp.process(resourceValue));
    }

    /**
     * Constructor of a {@link Resource} that's created lazily from a given context. Use this constructor when more
     * context than just the resource itself is needed to clean up after usage.
     */
    public <Context> LazyResource(
        Fragile<Context, Exception> contextSource,
        FragileFunction<? super Context, ? extends T, Exception> contextToValueFunction,
        FragileBiProcedure<? super T, ? super Context, Exception> cleanUp)
    {
        mResourceSource = new Fragile<T, Exception>()
        {
            @Override
            public T value() throws Exception
            {
                synchronized (this)
                {
                    if (mResourceSource == this)
                    {
                        Context context = contextSource.value();
                        T value = contextToValueFunction.value(context);
                        mResourceSource = () -> value;
                        mCleanUp = () -> cleanUp.process(value, context);
                        return value;
                    }
                    return mResourceSource.value();
                }
            }
        };
    }


    @Override
    public ResourceHandle<T> value() throws Exception
    {
        mRefCount.incrementAndGet();

        T resourceValue = mResourceSource.value();
        return new ResourceHandle<T>()
        {
            private final AtomicBoolean mClosed = new AtomicBoolean(false);

            @Override
            public T value()
            {
                return resourceValue;
            }

            @Override
            public void close() throws Exception
            {
                if (!mClosed.compareAndSet(false, true))
                {
                    throw new IllegalStateException("ResourceHandle already closed");
                }
                if (mRefCount.decrementAndGet() == 0)
                {
                    mCleanUp.run();
                }
            }
        };
    }
}

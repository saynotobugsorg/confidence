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
import org.dmfs.jems2.FragileProcedure;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.junit5.engine.Resource;

@StaticFactories(value = "Resources", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class LazyResource<T> implements Resource<T>
{
    private interface Breakable
    {
        void run() throws Exception;
    }

    private Fragile<? extends T, Exception> mResourceGenerator;
    private Breakable mCleanUp = () -> {};


    public LazyResource(Fragile<? extends T, Exception> resourceGenerator, FragileProcedure<? super T, Exception> cleanUp)
    {
        mResourceGenerator = () -> {
            synchronized (this)
            {
                T resource = resourceGenerator.value();
                mResourceGenerator = () -> resource;
                mCleanUp = () -> cleanUp.process(resource);
                return resource;
            }
        };
    }

    @Override
    public T value()
    {
        try
        {
            return mResourceGenerator.value();
        }
        catch (Exception e)
        {
            throw new RuntimeException("can't generate resource", e);
        }
    }

    @Override
    public void close() throws Exception
    {
        mCleanUp.run();
    }
}

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

package org.saynotobugs.confidence.junit5.engine.environment;

import org.dmfs.jems2.Fragile;
import org.dmfs.jems2.FragileProcedure;
import org.saynotobugs.confidence.junit5.engine.verifiable.WithResource;


public final class Resource<T> implements Fragile<WithResource.Resource<T>, Exception>
{
    private final Fragile<? extends T, Exception> mResourceGenerator;
    private final FragileProcedure<? super T, Exception> mCleanUp;


    public Resource(Fragile<T, Exception> resourceGenerator, FragileProcedure<T, Exception> cleanUp)
    {
        mResourceGenerator = resourceGenerator;
        mCleanUp = cleanUp;
    }


    @Override
    public WithResource.Resource<T> value() throws Exception
    {
        T resource = mResourceGenerator.value();
        return new WithResource.Resource<T>()
        {
            @Override
            public void close() throws Exception
            {
                mCleanUp.process(resource);
            }


            @Override
            public T value()
            {
                return resource;
            }

        };
    }
}

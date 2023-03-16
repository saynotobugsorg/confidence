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

package org.saynotobugs.confidence.junit5.engine.verifiable;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.Fragile;
import org.dmfs.jems2.Function;
import org.dmfs.jems2.Single;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.junit5.engine.Verifiable;


/**
 * A {@link Verifiable} that provides a certain environment to another {@link Verifiable}.
 */
@StaticFactories(value = "Engine", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class Given implements Verifiable
{
    public interface Resource<T> extends Single<T>, AutoCloseable
    {
    }


    private final Runnable mDelegate;
    private final String mName;


    public <T> Given(Fragile<Resource<T>, Exception> environment, Function<T, Verifiable> delegate)
    {
        this("", environment, delegate);
    }


    public <T> Given(String name, Fragile<Resource<T>, Exception> environment, Function<T, Verifiable> delegate)
    {
        this(name, () -> {
            try (Resource<T> resource = environment.value())
            {
                delegate.value(resource.value()).verify();
            }
            catch (Exception e)
            {
                throw new RuntimeException("Can't create test environment", e);
            }
        });
    }


    public <T, V> Given(Fragile<Resource<T>, Exception> env1, Fragile<Resource<V>, Exception> env2, BiFunction<T, V, Verifiable> delegate)
    {
        this("", env1, env2, delegate);
    }


    public <T, V> Given(String name, Fragile<Resource<T>, Exception> env1, Fragile<Resource<V>, Exception> env2, BiFunction<T, V, Verifiable> delegate)
    {
        this(name, () -> {
            try (Resource<T> resource = env1.value(); Resource<V> resource2 = env2.value())
            {
                delegate.value(resource.value(), resource2.value()).verify();
            }
            catch (Exception e)
            {
                throw new RuntimeException("Can't create test environment", e);
            }
        });
    }


    public Given(String name, Runnable delegate)
    {
        mName = name;
        mDelegate = delegate;
    }


    @Override
    public void verify()
    {
        mDelegate.run();
    }


    @Override
    public String name()
    {
        return mName;
    }
}

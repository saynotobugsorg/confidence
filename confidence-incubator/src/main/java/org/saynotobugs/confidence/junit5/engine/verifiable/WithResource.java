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

import org.dmfs.jems2.*;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactory;
import org.saynotobugs.confidence.junit5.engine.Verifiable;


/**
 * A {@link Verifiable} that provides a certain environment to another {@link Verifiable}.
 */
@StaticFactories(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class WithResource implements Verifiable
{
    public interface Resource<T> extends Single<T>, AutoCloseable
    {
    }


    private final Runnable mDelegate;
    private final String mName;


    public <T> WithResource(Fragile<Resource<T>, Exception> res, Function<T, Verifiable> delegate)
    {
        this("", res, delegate);
    }


    public <T> WithResource(String name, Fragile<Resource<T>, Exception> res, Function<T, Verifiable> delegate)
    {
        this(name, () -> {
            try (Resource<T> resource = res.value())
            {
                delegate.value(resource.value()).verify();
            }
            catch (Exception e)
            {
                throw new RuntimeException("Can't create test environment", e);
            }
        });
    }


    @StaticFactory(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine", methodName = "withResources")
    public <T, V> WithResource(Fragile<Resource<T>, Exception> res1, Fragile<Resource<V>, Exception> res2, BiFunction<T, V, Verifiable> delegate)
    {
        this("", res1, res2, delegate);
    }


    @StaticFactory(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine", methodName = "withResources")
    public <T, V> WithResource(String name, Fragile<Resource<T>, Exception> res1, Fragile<Resource<V>, Exception> res2, BiFunction<T, V, Verifiable> delegate)
    {
        this(name, res1, ignored -> res2.value(), delegate);
    }


    @StaticFactory(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine", methodName = "withResources")
    public <T, V> WithResource(String name,
        Fragile<Resource<T>, Exception> res1,
        FragileFunction<T, Resource<V>, Exception> res2,
        BiFunction<T, V, Verifiable> delegate)
    {
        this(name, () -> {
            try (Resource<T> resource1 = res1.value(); Resource<V> resource2 = res2.value(resource1.value()))
            {
                delegate.value(resource1.value(), resource2.value()).verify();
            }
            catch (Exception e)
            {
                throw new RuntimeException("Can't create test environment", e);
            }
        });
    }


    private WithResource(String name, Runnable delegate)
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

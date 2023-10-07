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

package org.saynotobugs.confidence.junit5.engine.assertion;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.Function;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactory;
import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.Resource;


/**
 * A {@link Assertion} that provides resource values to another {@link Assertion}.
 * <p>
 * After the delegate {@link Assertion} has finished, the given resources are closed.
 */
@StaticFactories(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class WithResource implements Assertion
{
    private final Assertion mDelegate;


    public <T> WithResource(Resource<T> res, Function<T, Assertion> delegate)
    {
        this(() -> {
            try (Resource<T> r = res)
            {
                delegate.value(r.value()).verify();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        });
    }

    @StaticFactory(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine", methodName = "withResources")
    public <T, V> WithResource(Resource<T> res1, Resource<V> res2, BiFunction<T, V, Assertion> delegate)
    {
        this(() -> {
            try (Resource<T> r1 = res1; Resource<V> r2 = res2)
            {
                delegate.value(r1.value(), r2.value()).verify();
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        });
    }


    private WithResource(Assertion delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public void verify()
    {
        mDelegate.verify();
    }
}

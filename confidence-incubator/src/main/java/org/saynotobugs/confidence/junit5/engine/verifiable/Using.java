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
import org.dmfs.jems2.Function;
import org.dmfs.jems2.Generator;
import org.saynotobugs.confidence.junit5.engine.Verifiable;


public final class Using implements Verifiable
{
    private final Runnable mDelegate;
    private final String mName;


    public <T> Using(Generator<T> environment, Function<T, Verifiable> delegate)
    {
        this("", environment, delegate);
    }


    public <T> Using(String name, Generator<T> environment, Function<T, Verifiable> delegate)
    {
        this(name, () -> delegate.value(environment.next()).verify());
    }


    public <T, V> Using(Generator<T> env1, Generator<V> env2, BiFunction<T, V, Verifiable> delegate)
    {
        this("", env1, env2, delegate);
    }


    public <T, V> Using(String name, Generator<T> env1, Generator<V> env2, BiFunction<T, V, Verifiable> delegate)
    {
        this(name, () -> delegate.value(env1.next(), env2.next()).verify());
    }


    public Using(String name, Runnable delegate)
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

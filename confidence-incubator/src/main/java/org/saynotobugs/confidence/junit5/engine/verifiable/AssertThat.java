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

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.junit5.engine.Verifiable;

import static org.saynotobugs.confidence.Assertion.assertThat;


@StaticFactories(value = "Engine", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class AssertThat<T> implements Verifiable
{
    private final String mName;
    private final T mInstance;
    private final Quality<T> mQuality;


    public AssertThat(String name, T instance, Quality<T> quality)
    {
        mName = name;
        mInstance = instance;
        mQuality = quality;
    }


    public AssertThat(T instance, Quality<T> quality)
    {
        this("", instance, quality);
    }


    @Override
    public void verify()
    {
        assertThat(mInstance, mQuality);
    }


    @Override
    public String name()
    {
        return mName;
    }

}

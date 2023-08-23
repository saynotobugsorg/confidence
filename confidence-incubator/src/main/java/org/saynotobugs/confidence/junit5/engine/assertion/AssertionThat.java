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

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.junit5.engine.Assertion;

import static org.saynotobugs.confidence.Assertion.assertThat;


@StaticFactories(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class AssertionThat<T> implements Assertion
{
    private final String mName;
    private final T mInstance;
    private final Quality<? super T> mQuality;


    public AssertionThat(String name, T instance, Quality<? super T> quality)
    {
        mName = name;
        mInstance = instance;
        mQuality = quality;
    }


    public AssertionThat(T instance, Quality<? super T> quality)
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

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

package org.saynotobugs.confidence.junit5.engine.resource;

import org.dmfs.jems2.FragileProcedure;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.junit5.engine.Resource;
import org.saynotobugs.confidence.junit5.engine.ResourceComposition;

@StaticFactories(value = "Resources", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class Initialized<T> extends ResourceComposition<T>
{
    public Initialized(
        FragileProcedure<? super T, Exception> initializationProcedure,
        Resource<? extends T> delegate)
    {
        super(new Derived<>(
            original -> {
                initializationProcedure.process(original);
                return original;
            },
            delegate
        ));
    }
}

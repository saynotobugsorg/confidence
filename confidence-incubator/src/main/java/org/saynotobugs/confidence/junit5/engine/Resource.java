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

package org.saynotobugs.confidence.junit5.engine;

import org.dmfs.jems2.Fragile;
import org.dmfs.srcless.annotations.composable.Composable;
import org.saynotobugs.confidence.junit5.engine.resource.Derived;
import org.saynotobugs.confidence.junit5.engine.resource.Initialized;
import org.saynotobugs.confidence.junit5.engine.resource.LazyResource;

/**
 * A test resource.
 * <p>
 * You almost certainly should not implement this interface directly but compose your resource using {@link Derived},
 * {@link Initialized} and/or {@link LazyResource} instead.
 */
@Composable
public interface Resource<T> extends Fragile<ResourceHandle<T>, Exception>
{
    @Override
    ResourceHandle<T> value() throws Exception;
}

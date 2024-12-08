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

package org.saynotobugs.confidence.rxjava3;

import io.reactivex.rxjava3.schedulers.TestScheduler;
import org.dmfs.srcless.annotations.composable.Composable;
import org.saynotobugs.confidence.Quality;


/**
 * An expectation on the current state of an {@link RxTestAdapter}.
 */
@FunctionalInterface
@Composable
public interface RxExpectation<T>
{
    /**
     * Returns a {@link Quality} to verify the state of an {@link RxTestAdapter}.
     */
    Quality<RxTestAdapter<T>> quality(TestScheduler scheduler);
}

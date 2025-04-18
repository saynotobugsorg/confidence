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

package org.saynotobugs.confidence.rxjava3.rxexpectation.internal;

import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.quality.object.InstanceOf;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;


public final class Errors<T> extends QualityComposition<RxTestAdapter<T>>
{
    public Errors(Class<Throwable> error)
    {
        this(new InstanceOf<>(error));
    }


    public Errors(Quality<? super Throwable> errorQuality)
    {
        super(new Has<>("errors that", RxTestAdapter::errors, new Iterates<>(errorQuality)));
    }
}

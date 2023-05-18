/*
 * Copyright 2022 dmfs GmbH
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

package org.saynotobugs.confidence.rxjava3.rxexpectation.internal;

import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.collection.HasSize;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;


public final class HasNoFurtherValues<T> extends QualityComposition<RxTestAdapter<T>>
{
    public HasNoFurtherValues()
    {
        super(
            new Has<>(
                new Text("pending emissions"),
                new Text("pending emissions"),
                ackSubscriber -> ackSubscriber.newValues(Integer.MAX_VALUE),
                new HasSize(0)));
    }
}

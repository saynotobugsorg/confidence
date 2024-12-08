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

package org.saynotobugs.confidence.rxjava3.rxexpectation;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.object.Successfully;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.RxExpectationComposition;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Disposal<T> extends RxExpectationComposition<T>
{
    /**
     * Creates an {@link RxExpectation} that disposes the subscription.
     */
    public Disposal()
    {
        super(testScheduFler -> new Successfully<>(new Text("disposal"), new Text("disposal"), new Text("disposal"), RxTestAdapter::cancel));
    }
}

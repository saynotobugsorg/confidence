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

package org.saynotobugs.confidence.rxjava3.rxexpectation;

import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.bifunction.Just;
import org.saynotobugs.confidence.description.bifunction.Original;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.Implied;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.rxjava3.RxExpectationComposition;
import org.saynotobugs.confidence.rxjava3.rxexpectation.internal.Emits;
import org.saynotobugs.confidence.rxjava3.rxexpectation.internal.EmitsNothing;
import org.saynotobugs.confidence.rxjava3.rxexpectation.internal.IsComplete;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class CompletesWith<T> extends RxExpectationComposition<T>
{
    @SafeVarargs
    public CompletesWith(T... values)
    {
        this(values.length, new Iterates<>(values));
    }


    @SafeVarargs
    public CompletesWith(Quality<? super T>... values)
    {
        this(values.length, new Iterates<>(values));
    }


    public CompletesWith(int elementCount, Quality<? super Iterable<T>> values)
    {
        super(testScheduler -> new DescribedAs<>(
            new Just<>(new Spaced(new Text("completed with"), values.description())),
            new Original<>(),
            new Just<>(new Spaced(new Text("completes with"), values.description())),
            new Implied<>(
                new Seq<>(
                    new IsComplete(),
                    new Emits<>(elementCount, values),
                    new EmitsNothing<>()),
                new Anything()
            )));
    }
}

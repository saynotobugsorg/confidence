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

package org.saynotobugs.confidence.rxjava3.procedure;

import org.dmfs.jems2.Single;
import org.dmfs.jems2.procedure.ForEach;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Successfully;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Error extends QualityComposition<RxSubjectAdapter<?>>
{
    public Error(Throwable error)
    {
        this(() -> error);
    }


    public Error(Single<Throwable> error)
    {
        super(new Successfully<>(
            new Spaced(new Text("error"), new Value(error.value())),
            new Text("error failed with"),
            new Spaced(new Text("error"), new Value(error.value())),
            rxSubjectAdapter -> new ForEach<>(error).process(rxSubjectAdapter::onError)));
    }

}

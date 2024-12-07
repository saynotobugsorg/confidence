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

package org.saynotobugs.confidence.rxjava3.transformerteststep;

import org.dmfs.jems2.iterable.Just;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.rxjava3.RxExpectation;
import org.saynotobugs.confidence.rxjava3.TransformerTestStepComposition;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Downstream<Up, Down> extends TransformerTestStepComposition<Up, Down>
{
    public Downstream(RxExpectation<Down> event)
    {
        super((scheduler, upstream) -> new Just<>(new DescribedAs<>(
            new TextAndOriginal<>("to downstream"),
            new TextAndOriginal<>("to downstream"),
            new TextAndOriginal<>("to downstream"),
            event.quality(scheduler))));
    }


    @SafeVarargs
    public Downstream(RxExpectation<Down>... events)
    {
        this(new Seq<>(events));
    }


    public Downstream(Iterable<? extends RxExpectation<Down>> events)
    {
        super((scheduler, upstream) -> new Mapped<>(
            downstreamTestEvent -> new DescribedAs<>(orig -> new Spaced(new Text("to downstream"), orig), downstreamTestEvent.quality(scheduler)),
            events));
    }
}

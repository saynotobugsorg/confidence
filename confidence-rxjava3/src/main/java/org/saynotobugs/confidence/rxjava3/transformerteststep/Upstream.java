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

package org.saynotobugs.confidence.rxjava3.transformerteststep;

import org.dmfs.jems2.iterable.Joined;
import org.dmfs.jems2.iterable.Just;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.object.Satisfies;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;
import org.saynotobugs.confidence.rxjava3.TransformerTestStepComposition;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Upstream<Up, Down> extends TransformerTestStepComposition<Up, Down>
{

    public Upstream(Quality<? super RxSubjectAdapter<Up>> event)
    {
        super((scheduler, upstream) -> new Just<>(
            new DescribedAs<>(
                new org.saynotobugs.confidence.description.bifunction.Just<>(new Spaced(new Text("upstream"), event.description())),
                new TextAndOriginal<>("upstream"),
                new TextAndOriginal<>("upstream"),
                new Satisfies<>(testadapter -> event.assessmentOf(upstream).isSuccess(),
                    event.description()))));
    }

    @SafeVarargs
    public Upstream(Quality<? super RxSubjectAdapter<Up>>... events)
    {
        this(new Seq<>(events));
    }


    public Upstream(Iterable<? extends Quality<? super RxSubjectAdapter<Up>>> events)
    {
        super((scheduler, upstream) -> new Just<>(
            new DescribedAs<>(
                new org.saynotobugs.confidence.description.bifunction.Just<>(new Spaced(
                    new Joined<>(
                        new Seq<>(new Text("upstream")),
                        new Mapped<>(Quality::description, events)))),
                new TextAndOriginal<>("upstream"),
                new TextAndOriginal<>("upstream"),
                new Satisfies<>(testadapter -> new AllOf<>(events).assessmentOf(upstream).isSuccess(),
                    new AllOf<>(events).description()))));
    }
}

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
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.object.Satisfies;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;
import org.saynotobugs.confidence.rxjava3.RxTestAdapter;
import org.saynotobugs.confidence.rxjava3.TransformerTestStep;

import io.reactivex.rxjava3.schedulers.TestScheduler;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Upstream<Up, Down> implements TransformerTestStep<Up, Down>
{
    private final Iterable<Quality<? super RxSubjectAdapter<? super Up>>> mEvents;


    @SafeVarargs
    public Upstream(Quality<? super RxSubjectAdapter<? super Up>>... events)
    {
        this(new Seq<>(events));
    }


    public Upstream(Iterable<Quality<? super RxSubjectAdapter<? super Up>>> events)
    {
        mEvents = events;
    }


    @Override
    public Iterable<Quality<RxTestAdapter<Down>>> qualities(TestScheduler scheduler, RxSubjectAdapter<? super Up> upstream)
    {
        return new Just<>(
            new DescribedAs<>(orig -> new Delimited(new TextDescription("upstream"), orig),
                new Satisfies<>(testadapter -> new AllOf<>(mEvents).assessmentOf(upstream).isSuccess(), new AllOf<>(mEvents).description())));
    }
}

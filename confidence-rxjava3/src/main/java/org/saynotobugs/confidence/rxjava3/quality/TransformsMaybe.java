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

package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeTransformer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import io.reactivex.rxjava3.subjects.MaybeSubject;
import org.dmfs.jems2.Function;
import org.dmfs.jems2.iterable.Expanded;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.reactivestreams.Publisher;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.Indented;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOfFailingFast;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.rxjava3.TransformerTestStep;
import org.saynotobugs.confidence.rxjava3.adapters.MaybeSubjectAdapter;
import org.saynotobugs.confidence.rxjava3.adapters.RxTestObserver;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class TransformsMaybe<Up, Down> extends QualityComposition<Function<? super TestScheduler, ? extends MaybeTransformer<Up, Down>>>
{

    @SafeVarargs
    public TransformsMaybe(TransformerTestStep<Up, Down>... testSteps)
    {
        this(new Seq<>(testSteps));
    }


    public TransformsMaybe(Iterable<? extends TransformerTestStep<Up, Down>> testSteps)
    {
        super(new TransformsTestSteps<>(testSteps));
    }


    public TransformsMaybe(
        Function<? super Scheduler, ? extends Publisher<Up>> upstream,
        Quality<? super Function<? super TestScheduler, ? extends Maybe<? extends Down>>> downStreamQualities)
    {
        super(new Has<>(
            transformerFunction -> (Function<TestScheduler, Maybe<Down>>)
                scheduler -> Maybe.fromPublisher(upstream.value(scheduler))
                    .compose(transformerFunction.value(scheduler)),
            downStreamQualities));
    }

    private static class TransformsTestSteps<Up, Down> implements Quality<Function<? super TestScheduler, ? extends MaybeTransformer<Up, Down>>>
    {
        private final Iterable<? extends TransformerTestStep<Up, Down>> mEvents;

        public TransformsTestSteps(Iterable<? extends TransformerTestStep<Up, Down>> events)
        {
            mEvents = events;
        }


        @Override
        public Assessment assessmentOf(Function<? super TestScheduler, ? extends MaybeTransformer<Up, Down>> candidate)
        {
            TestScheduler t = new TestScheduler();
            RxTestObserver<Down> testAdapter = new RxTestObserver<>();
            MaybeSubject<Up> upstream = MaybeSubject.create();
            candidate.value(t).apply(upstream.hide()).subscribe(testAdapter);
            return new AllOfFailingFast<RxTestObserver<Down>>(COMMA,
                new Expanded<>(e -> e.qualities(t, new MaybeSubjectAdapter<>(upstream)), mEvents)
            ).assessmentOf(testAdapter);
        }


        @Override
        public Description description()
        {
            TestScheduler t = new TestScheduler();
            MaybeSubject<Up> upstream = MaybeSubject.create();
            return new DescribedAs<>(orig -> new Composite(new Text("MaybeTransformer that transforms"), new Indented(new Composite(NEW_LINE, orig))),
                new AllOfFailingFast<>(COMMA,
                    new Expanded<>(e -> e.qualities(t, new MaybeSubjectAdapter<>(upstream)), mEvents)
                )).description();
        }
    }
}

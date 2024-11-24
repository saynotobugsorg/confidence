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

package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableTransformer;
import io.reactivex.rxjava3.core.Scheduler;
import org.dmfs.jems2.Function;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.grammar.To;
import org.saynotobugs.confidence.rxjava3.function.At;
import org.saynotobugs.confidence.rxjava3.function.Scheduled;
import org.saynotobugs.confidence.rxjava3.procedure.Complete;
import org.saynotobugs.confidence.rxjava3.procedure.Error;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Completes;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Errors;
import org.saynotobugs.confidence.rxjava3.rxexpectation.IsAlive;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Within;
import org.saynotobugs.confidence.rxjava3.transformerteststep.Downstream;
import org.saynotobugs.confidence.rxjava3.transformerteststep.Upstream;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.saynotobugs.confidence.Assertion.assertThat;


class TransformsCompletableTest
{

    @Test
    void testComplete()
    {
        assertThat(new TransformsCompletable<>(new Upstream<>(new Complete()), new Downstream<>(new Completes<>())),
            new AllOf<>(
                new Passes<>(scheduler -> Completable::hide),
                new Fails<>(scheduler -> upsteam -> upsteam.andThen(Completable.error(new IOException())), "all of\n  ...,\n  1: to downstream had errors [ <java.io.IOException> ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS, scheduler), "all of\n  ...,\n  1: to downstream completed 0 times"),
                new HasDescription(
                    "CompletableTransformer that transforms\n  all of\n    0: upstream completion,\n    1: to downstream completes exactly once")
            ));
    }


    @Test
    void testDownstreamError()
    {
        assertThat(new TransformsCompletable<>(new Upstream<>(new Complete()), new Downstream<>(new Errors<>(IOException.class))),
            new AllOf<>(
                new Passes<>(scheduler -> upsteam -> upsteam.andThen(Completable.error(new IOException()))),
                new Fails<>(scheduler -> Completable::hide,
                    "all of\n  ...,\n  1: to downstream had errors that iterated [\n    0: missing instance of <class java.io.IOException>\n  ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS, scheduler),
                    "all of\n  ...,\n  1: to downstream had errors that iterated [\n    0: missing instance of <class java.io.IOException>\n  ]"),
                new HasDescription(
                    "CompletableTransformer that transforms\n  all of\n    0: upstream completion,\n    1: to downstream has errors that iterates [\n      0: instance of <class java.io.IOException>\n    ]")
            ));
    }


    @Test
    void testUpstreamError()
    {
        assertThat(new TransformsCompletable<>(new Upstream<>(new Error(new IOException())), new Downstream<>(new Completes<>())),
            new AllOf<>(
                new Passes<>(scheduler -> upsteam -> upsteam.onErrorComplete()),
                new Fails<>(scheduler -> Completable::hide, "all of\n  ...,\n  1: to downstream had errors [ <java.io.IOException> ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS, scheduler), "all of\n  ...,\n  1: to downstream completed 0 times"),
                new HasDescription("CompletableTransformer that transforms\n  all of\n    0: upstream error <java.io.IOException>,\n    1: to downstream completes exactly once")
            ));
    }


    @Test
    void testWithScheduledCompletable()
    {
        assertThat((Function<Scheduler, ? extends CompletableTransformer>) scheduler -> upstream -> upstream.delay(100, MILLISECONDS, scheduler),
            new TransformsCompletable<>(
                new Scheduled<>(new At<>(100, new Complete())),
                new To<>(new CompletableThat<>(
                    new Within<>(Duration.ofMillis(199), new IsAlive()),
                    new Within<>(Duration.ofMillis(1), new Completes<>())
                ))));
    }


    @Test
    void testWithScheduledCompletableErroring()
    {
        assertThat((Function<Scheduler, ? extends CompletableTransformer>) scheduler -> upstream -> upstream.delay(100, MILLISECONDS, scheduler),
            new TransformsCompletable<>(
                new Scheduled<>(new At<>(100, new Error(new IOException()))),
                new To<>(new CompletableThat<>(
                    new Within<>(Duration.ofMillis(99), new IsAlive()),
                    new Within<>(Duration.ofMillis(1), new Errors<>(IOException.class))
                ))));
    }
}
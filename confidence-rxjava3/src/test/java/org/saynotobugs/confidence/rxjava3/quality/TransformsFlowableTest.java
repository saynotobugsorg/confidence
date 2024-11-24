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

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.grammar.To;
import org.saynotobugs.confidence.rxjava3.function.After;
import org.saynotobugs.confidence.rxjava3.function.At;
import org.saynotobugs.confidence.rxjava3.function.Scheduled;
import org.saynotobugs.confidence.rxjava3.procedure.Complete;
import org.saynotobugs.confidence.rxjava3.procedure.Emit;
import org.saynotobugs.confidence.rxjava3.rxexpectation.*;
import org.saynotobugs.confidence.rxjava3.transformerteststep.Downstream;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.saynotobugs.confidence.Assertion.assertThat;


class TransformsFlowableTest
{

    @Test
    void test()
    {
        assertThat(new TransformsFlowable<>(new org.saynotobugs.confidence.rxjava3.transformerteststep.Upstream<>(new Complete()), new Downstream<>(new Completes<>())),
            new AllOf<>(
                new Passes<>(scheduler -> Flowable::hide),
                new Fails<>(scheduler -> upsteam -> upsteam.ambWith(Flowable.error(new IOException())), "all of\n  ...,\n  1: to downstream had errors [ <java.io.IOException> ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS), "all of\n  ...,\n  1: to downstream completed 0 times"),
                new HasDescription(
                    "FlowableTransformer that transforms\n  all of\n    0: upstream completion,\n    1: to downstream completes exactly once")
            ));
    }


    @Test
    void testMultipleSteps()
    {
        assertThat(new TransformsFlowable<>(
                new org.saynotobugs.confidence.rxjava3.transformerteststep.Upstream<>(new Emit<>(123), new Emit<>(4)),
                new Downstream<>(new Emits<>(246, 8)),
                new org.saynotobugs.confidence.rxjava3.transformerteststep.Upstream<>(new Emit<>(200)),
                new Downstream<>(new Emits<>(400)),
                new org.saynotobugs.confidence.rxjava3.transformerteststep.Upstream<>(new Complete()),
                new Downstream<>(new Completes<>())),
            new AllOf<>(
                new Passes<>(scheduler -> upstream -> upstream.map(i -> i * 2)),
                new Fails<>(scheduler -> upsteam -> upsteam.map(i -> i * 3),
                    "all of\n  ...,\n  1: to downstream emitted 2 items iterated [\n    0: 369\n    1: 12\n  ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.ambWith(Flowable.error(new IOException())),
                    "all of\n  ...,\n  1: to downstream had errors [ <java.io.IOException> ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS, scheduler),
                    "all of\n  ...,\n  1: to downstream emitted 0 items iterated [\n    0: missing 246\n    1: missing 8\n  ]"),
                new HasDescription(
                    "FlowableTransformer that transforms\n  all of\n    0: upstream all of\n      0: emissions [123]\n      1: emissions [4],\n    1: to downstream emits 2 items iterates [\n      0: 246\n      1: 8\n    ],\n    2: upstream emissions [200],\n    3: to downstream emits 1 items 400,\n    4: upstream completion,\n    5: to downstream completes exactly once"
                )
            ));
    }


    @Test
    void testWithScheduledFlowable()
    {
        assertThat(scheduler -> upstream -> upstream.map(x -> x + "x"),
            new TransformsFlowable<>(
                new Scheduled<>(new At<>(100, new Emit<>("a"))),
                new To<>(new PublisherThat<>(
                    new Within<>(Duration.ofMillis(99), new EmitsNothing<>()),
                    new Within<>(Duration.ofMillis(1), new Emits<>("ax"))
                ))));
    }


    @Test
    void testScheduledFlowableWithComplete()
    {
        assertThat(scheduler -> upstream -> upstream.map(x -> x + "x"),
            new TransformsFlowable<>(
                new Scheduled<>(
                    new At<>(100, new Emit<>("a", "b", "c")),
                    new After<>(Duration.ofMillis(1000), new Complete())),
                new To<>(new PublisherThat<>(
                    new Within<>(Duration.ofMillis(99), new EmitsNothing<>()),
                    new Within<>(Duration.ofMillis(1), new Emits<>("ax", "bx", "cx")),
                    new Within<>(Duration.ofMillis(999), new IsAlive<>()),
                    new Within<>(Duration.ofMillis(1), new Completes<>())
                ))));
    }
}
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

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.procedure.Complete;
import org.saynotobugs.confidence.rxjava3.procedure.Emit;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Completes;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Emits;
import org.saynotobugs.confidence.rxjava3.transformerteststep.Downstream;
import org.saynotobugs.confidence.rxjava3.transformerteststep.Upstream;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Flowable;

import static org.saynotobugs.confidence.Assertion.assertThat;


class TransformsFlowableTest
{

    @Test
    void test()
    {
        assertThat(new TransformsFlowable<>(new Upstream<>(new Complete()), new Downstream<>(new Completes<>())),
            new AllOf<>(
                new Passes<>(scheduler -> Flowable::hide),
                new Fails<>(scheduler -> upsteam -> upsteam.ambWith(Flowable.error(new IOException())), "(1) to downstream { completed <0> times\n  ... }"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS), "(1) to downstream { completed <0> times\n  ... }"),
                new HasDescription(
                    "FlowableTransformer that transforms\n" +
                        "  (0) upstream completion,\n" +
                        "    (1) to downstream completes exactly once\n" +
                        "      and\n" +
                        "      emits nothing")
            ));
    }


    @Test
    void testMultipleSteps()
    {
        assertThat(new TransformsFlowable<>(
                new Upstream<>(new Emit<>(123)),
                new Downstream<>(new Emits<>(246)),
                new Upstream<>(new Emit<>(200)),
                new Downstream<>(new Emits<>(400)),
                new Upstream<>(new Complete()),
                new Downstream<>(new Completes<>())),
            new AllOf<>(
                new Passes<>(scheduler -> upstream -> upstream.map(i -> i * 2)),
                new Fails<>(scheduler -> upsteam -> upsteam.map(i -> i * 3),
                    "(1) to downstream emitted <1> items that iterated [ 0: <369> ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.ambWith(Flowable.error(new IOException())),
                    "(1) to downstream emitted <0> items that iterated [ 0: missing <246> ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS, scheduler),
                    "(1) to downstream emitted <0> items that iterated [ 0: missing <246> ]"),
                new HasDescription(
                    "FlowableTransformer that transforms\n" +
                        "  (0) upstream emissions [<123>],\n" +
                        "    (1) to downstream emits <1> items that iterates [ 0: <246> ],\n" +
                        "    (2) upstream emissions [<200>],\n" +
                        "    (3) to downstream emits <1> items that iterates [ 0: <400> ],\n" +
                        "    (4) upstream completion,\n" +
                        "    (5) to downstream completes exactly once\n" +
                        "      and\n" +
                        "      emits nothing"
                )
            ));
    }
}
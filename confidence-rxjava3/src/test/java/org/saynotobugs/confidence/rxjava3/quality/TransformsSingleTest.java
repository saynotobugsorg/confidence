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

import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.grammar.To;
import org.saynotobugs.confidence.rxjava3.function.At;
import org.saynotobugs.confidence.rxjava3.function.Scheduled;
import org.saynotobugs.confidence.rxjava3.procedure.Emit;
import org.saynotobugs.confidence.rxjava3.procedure.Error;
import org.saynotobugs.confidence.rxjava3.rxexpectation.CompletesWith;
import org.saynotobugs.confidence.rxjava3.rxexpectation.EmitsNothing;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Errors;
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


class TransformsSingleTest
{

    @Test
    void testComplete()
    {
        assertThat(new TransformsSingle<>(new Upstream<>(new Emit<>(123)), new Downstream<>(new CompletesWith<>(123))),
            new AllOf<>(
                new Passes<>(scheduler -> Single::hide,
                    "all of\n" +
                        "  0: upstream emissions [123],\n" +
                        "  1: to downstream completed with iterates [\n" +
                        "    0: 123\n" +
                        "  ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.ambWith(Single.error(new IOException())),
                    "all of\n  ...,\n  1: to downstream completed 0 times"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS),
                    "all of\n  ...,\n  1: to downstream completed 0 times"),
                new HasDescription(
                    "SingleTransformer that transforms\n  all of\n    0: upstream emissions [123],\n    1: to downstream completes with iterates [\n      0: 123\n    ]")
            ));
    }


    @Test
    void testDownstreamError()
    {
        assertThat(new TransformsSingle<Integer, Integer>(new Upstream<>(new Emit<>(123)), new Downstream<>(new Errors<>(IOException.class))),
            new AllOf<>(
                new Passes<>(scheduler -> upsteam -> upsteam.ambWith(Single.error(new IOException())),
                    "all of\n" +
                        "  0: upstream emissions [123],\n" +
                        "  1: to downstream had errors that iterated [\n" +
                        "    0: instance of <class java.io.IOException>\n" +
                        "  ]"),
                new Fails<>(scheduler -> Single::hide, "all of\n  ...,\n  1: to downstream had errors that iterated [\n    0: missing instance of <class java.io.IOException>\n  ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS),
                    "all of\n  ...,\n  1: to downstream had errors that iterated [\n    0: missing instance of <class java.io.IOException>\n  ]"),
                new HasDescription(
                    "SingleTransformer that transforms\n  all of\n    0: upstream emissions [123],\n    1: to downstream has errors that iterates [\n      0: instance of <class java.io.IOException>\n    ]")
            ));
    }


    @Test
    void testUpstreamError()
    {
        assertThat(new TransformsSingle<Integer, Integer>(new Upstream<>(new Error(new IOException())), new Downstream<>(new CompletesWith<>(123))),
            new AllOf<>(
                new Passes<>(scheduler -> upsteam -> upsteam.onErrorReturnItem(123),
                    "all of\n" +
                        "  0: upstream error <java.io.IOException>,\n" +
                        "  1: to downstream completed with iterates [\n" +
                        "    0: 123\n" +
                        "  ]"),
                new Fails<>(scheduler -> Single::hide,
                    "all of\n  ...,\n  1: to downstream completed 0 times"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS),
                    "all of\n  ...,\n  1: to downstream completed 0 times"),
                new HasDescription(
                    "SingleTransformer that transforms\n  all of\n    0: upstream error <java.io.IOException>,\n    1: to downstream completes with iterates [\n      0: 123\n    ]")
            ));
    }


    @Test
    void testWithScheduledSingle()
    {
        assertThat(scheduler -> upstream -> upstream.delay(100, MILLISECONDS, scheduler),
            new TransformsSingle<>(
                new Scheduled<>(new At<>(100, new Emit<>(123))),
                new To<>(new SingleThat<>(
                    new Within<>(Duration.ofMillis(199), new EmitsNothing<>()),
                    new Within<>(Duration.ofMillis(1), new CompletesWith<>(123))
                ))));
    }
}
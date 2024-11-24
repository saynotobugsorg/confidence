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

import io.reactivex.rxjava3.core.Observable;
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
import org.saynotobugs.confidence.rxjava3.transformerteststep.Upstream;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.saynotobugs.confidence.Assertion.assertThat;


class TransformsObservableTest
{

    @Test
    void test()
    {
        assertThat(new TransformsObservable<>(new Upstream<>(new Complete()), new Downstream<>(new Completes<>())),
            new AllOf<>(
                new Passes<>(scheduler -> Observable::hide),
                new Fails<>(scheduler -> upsteam -> upsteam.ambWith(Observable.error(new IOException())), "all of\n  ...,\n  1: to downstream had errors [ <java.io.IOException> ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS), "all of\n  ...,\n  1: to downstream completed 0 times"),
                new HasDescription(
                    "ObservableTransformer that transforms\n  all of\n    0: upstream completion,\n    1: to downstream completes exactly once")
            ));
    }


    @Test
    void testWithScheduledObservable()
    {
        assertThat(scheduler -> upstream -> upstream.delay(100, MILLISECONDS, scheduler),
            new TransformsObservable<>(
                new Scheduled<>(
                    new At<>(100, new Emit<>(123)),
                    new After<>(Duration.ofMillis(100), new Emit<>(456)),
                    new After<>(Duration.ofMillis(100), new Complete())),
                new To<>(new ObservableThat<>(
                    new Within<>(Duration.ofMillis(199), new EmitsNothing<>()),
                    new Within<>(Duration.ofMillis(1), new Emits<>(123)),
                    new Within<>(Duration.ofMillis(99), new EmitsNothing<>()),
                    new Within<>(Duration.ofMillis(1), new Emits<>(456)),
                    new Within<>(Duration.ofMillis(99), new IsAlive<>()),
                    new Within<>(Duration.ofMillis(1), new Completes<>())
                ))));
    }
}
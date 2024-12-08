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

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.rxjava3.function.After;
import org.saynotobugs.confidence.rxjava3.function.At;
import org.saynotobugs.confidence.rxjava3.function.Scheduled;
import org.saynotobugs.confidence.rxjava3.procedure.Emit;
import org.saynotobugs.confidence.rxjava3.procedure.Error;
import org.saynotobugs.confidence.rxjava3.rxexpectation.*;

import java.io.IOException;
import java.time.Duration;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ScheduledTest
{
    @Test
    void testEmpty()
    {
        assertThat(new Scheduled<>(),
            new Is(new PublisherThat<>(
                new EmitsNothing<>(),
                new Completes<>()
            )));
    }


    @Test
    void testWithTimestamps()
    {
        assertThat(new Scheduled<>(
                new At(100, new Emit<>(1))),
            new Is(new PublisherThat<>(
                new Within<>(Duration.ofMillis(99), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(1)),
                new Completes<>()
            )));
    }


    @Test
    void testWithMoreTimestamps()
    {
        assertThat(new Scheduled<>(
                new At(100, new Emit<>(1)),
                new At(110, new Emit<>(2)),
                new At(150, new Emit<>(3)),
                new At(1000, new Emit<>(4))),
            new Is(new PublisherThat<>(
                new Within<>(Duration.ofMillis(99), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(1)),
                new Within<>(Duration.ofMillis(9), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(2)),
                new Within<>(Duration.ofMillis(39), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(3)),
                new Within<>(Duration.ofMillis(849), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(4)),
                new Completes<>()
            )));
    }


    @Test
    void testWithAfter()
    {
        assertThat(new Scheduled<>(
                new At(100, new Emit<>(1)),
                new After(Duration.ofMillis(110), new Emit<>(2)),
                new After(Duration.ofMillis(150), new Emit<>(3)),
                new After(Duration.ofMillis(1000), new Emit<>(4))),
            new Is(new PublisherThat<>(
                new Within<>(Duration.ofMillis(99), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(1)),
                new Within<>(Duration.ofMillis(109), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(2)),
                new Within<>(Duration.ofMillis(149), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(3)),
                new Within<>(Duration.ofMillis(999), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(4)),
                new Completes<>()
            )));
    }


    @Test
    void testWithError()
    {
        assertThat(new Scheduled<>(
                new At(100, new Emit<>(1)),
                new After(Duration.ofMillis(110), new Emit<>(2)),
                new After(Duration.ofMillis(150), new Error(new IOException()))),
            new Is(new PublisherThat<>(
                new Within<>(Duration.ofMillis(99), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(1)),
                new Within<>(Duration.ofMillis(109), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Emits<>(2)),
                new Within<>(Duration.ofMillis(149), new EmitsNothing<>()),
                new Within<>(Duration.ofMillis(1), new Errors<>(IOException.class))
            )));
    }
}
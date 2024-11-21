/*
 * Copyright 2023 dmfs GmbH
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
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.saynotobugs.confidence.Assertion.assertThat;


class TimedTest
{
    @Test
    void testInstantAndValueQuality()
    {
        assertThat(new Timed<>(Instant.ofEpochMilli(123), new EqualTo<>(456)),
            new AllOf<>(
                new Passes<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 123, TimeUnit.MILLISECONDS)),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(789, 123, TimeUnit.MILLISECONDS), "both,\n  ... and\n  had value 789"),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  ..."),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(987, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  had value 987"),
                new HasDescription("both,\n  has time of 123l millis and\n  has value 456"))
        );
    }


    @Test
    void testInstantAndValue()
    {
        assertThat(new Timed<>(Instant.ofEpochMilli(123), 456),
            new AllOf<>(
                new Passes<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 123, TimeUnit.MILLISECONDS)),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(789, 123, TimeUnit.MILLISECONDS), "both,\n  ... and\n  had value 789"),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  ..."),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(987, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  had value 987"),
                new HasDescription("both,\n  has time of 123l millis and\n  has value 456"))
        );
    }


    @Test
    void testDurationAndValueQuality()
    {
        assertThat(new Timed<>(Duration.ofMillis(123), new EqualTo<>(456)),
            new AllOf<>(
                new Passes<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 123, TimeUnit.MILLISECONDS)),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(789, 123, TimeUnit.MILLISECONDS), "both,\n  ... and\n  had value 789"),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  ..."),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(987, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  had value 987"),
                new HasDescription("both,\n  has time of 123l millis and\n  has value 456"))
        );
    }


    @Test
    void testDurationAndValue()
    {
        assertThat(new Timed<>(Duration.ofMillis(123), 456),
            new AllOf<>(
                new Passes<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 123, TimeUnit.MILLISECONDS)),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(789, 123, TimeUnit.MILLISECONDS), "both,\n  ... and\n  had value 789"),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  ..."),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(987, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  had value 987"),
                new HasDescription("both,\n  has time of 123l millis and\n  has value 456"))
        );
    }


    @Test
    void testLongAndValueQuality()
    {
        assertThat(new Timed<>(123, new EqualTo<>(456)),
            new AllOf<>(
                new Passes<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 123, TimeUnit.MILLISECONDS)),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(789, 123, TimeUnit.MILLISECONDS), "both,\n  ... and\n  had value 789"),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  ..."),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(987, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  had value 987"),
                new HasDescription("both,\n  has time of 123l millis and\n  has value 456"))
        );
    }


    @Test
    void testLongAndValue()
    {
        assertThat(new Timed<>(123, 456),
            new AllOf<>(
                new Passes<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 123, TimeUnit.MILLISECONDS)),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(789, 123, TimeUnit.MILLISECONDS), "both,\n  ... and\n  had value 789"),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(456, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  ..."),
                new Fails<>(new io.reactivex.rxjava3.schedulers.Timed<>(987, 789, TimeUnit.MILLISECONDS), "both,\n  had time of 789l millis and\n  had value 987"),
                new HasDescription("both,\n  has time of 123l millis and\n  has value 456"))
        );
    }
}
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

import org.dmfs.jems2.Function;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.Both;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Timed<T> extends QualityComposition<io.reactivex.rxjava3.schedulers.Timed<T>>
{
    public Timed(long millis, T value)
    {
        this(millis, new EqualTo<>(value));
    }


    public Timed(long millis, Quality<? super T> valueQuality)
    {
        this(Instant.ofEpochMilli(millis), valueQuality);
    }


    public Timed(Duration timestamp, T value)
    {
        this(timestamp, new EqualTo<>(value));
    }


    public Timed(Duration timestamp, Quality<? super T> valueQuality)
    {
        this(Instant.ofEpochMilli(timestamp.toMillis()), valueQuality);
    }


    public Timed(Instant timestamp, T value)
    {
        this(timestamp, new EqualTo<>(value));
    }


    public Timed(Instant timestamp, Quality<? super T> valueQuality)
    {
        super(new Both<>(
            new Has<>(d -> new Spaced(new Text("has time of"), d, new Text("millis")),
                (Function<Description, Description>) d -> new Spaced(new Text("had time of"), d, new Text("millis")),
                timed -> timed.time(TimeUnit.MILLISECONDS),
                new EqualTo<>(timestamp.toEpochMilli())),
            new Has<>("value", io.reactivex.rxjava3.schedulers.Timed::value, valueQuality)
        ));
    }
}

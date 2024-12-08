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

import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Emits;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Within;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ObservableThatTest
{
    @Test
    void testMatch()
    {
        assertThat(new ObservableThat<>(new Within<>(Duration.ofSeconds(2), new Emits<>(1, 2, 3))),
            new AllOf<>(
                new Passes<>(scheduler -> Observable.just(1, 2, 3).delay(2, TimeUnit.SECONDS, scheduler),
                    "Observable that all of\n" +
                        "  0: after PT2S emitted 3 items iterated [\n" +
                        "    0: 1\n" +
                        "    1: 2\n" +
                        "    2: 3\n" +
                        "  ]"),
                new Fails<>(scheduler -> Observable.just(1, 2, 3).delay(30, TimeUnit.SECONDS, scheduler),
                    "Observable that all of\n  0: after PT2S emitted 0 items iterated [\n    0: missing 1\n    1: missing 2\n    2: missing 3\n  ]"),
                new HasDescription("Observable that all of\n  0: after PT2S emits 3 items iterates [\n    0: 1\n    1: 2\n    2: 3\n  ]")
            ));
    }
}

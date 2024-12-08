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
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Completes;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Emits;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;

import static org.saynotobugs.confidence.Assertion.assertThat;


class MaybeThatTest
{
    @Test
    void testNonEmpty()
    {
        assertThat(new MaybeThat<>(new Emits<>(new EqualTo<>(123))),
            new AllOf<>(
                new Passes<>(ignored -> Maybe.just(123), "Maybe that emitted 1 items 123"),
                new Fails<>(ignored -> Maybe.error(IOException::new), "Maybe that had errors [ <java.io.IOException> ]"),
                new Fails<>(ignored -> Maybe.empty(), "Maybe that emitted 0 items had 0 elements"),
                new Fails<>(ignored -> Maybe.just(124), "Maybe that emitted 1 items 124"),
                new HasDescription("Maybe that emits 1 items 123")
            ));
    }


    @Test
    void testEmpty()
    {
        assertThat(new MaybeThat<>(new Completes<>()),
            new AllOf<>(
                new Passes<>(ignored -> Maybe.empty(), "Maybe that completes exactly once"),
                new Fails<>(ignored -> Maybe.error(IOException::new), "Maybe that had errors [ <java.io.IOException> ]"),
                new Fails<>(ignored -> Maybe.just(124), "Maybe that emitted [ 124 ]"),
                new HasDescription("Maybe that completes exactly once")
            ));
    }

}
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

package org.saynotobugs.confidence.incubator.jupiter;

import org.saynotobugs.confidence.junit5.engine.Confidence;
import org.saynotobugs.confidence.junit5.engine.VerifiableGroup;
import org.saynotobugs.confidence.junit5.engine.verifiablegroup.Group;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.quality.optional.Present;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.Optional;

import static org.saynotobugs.confidence.junit5.engine.Assertions.assertThat;


@Confidence
class GroupTest
{
    VerifiableGroup Present = new Group(
        assertThat("without value",
            new Present<>(),
            new AllOf<>(
                new Passes<Optional<Object>>(Optional.of(123), Optional.of(1234), Optional.of("abc")),
                new Fails<>(Optional.empty(), "absent"),
                new HasDescription("present <anything>"))),

        assertThat("with value",
            new Present<>(123),
            new AllOf<>(
                new Passes<>(Optional.of(123)),
                new Fails<>(Optional.of(1234), "present <1234>"),
                new Fails<Optional<Integer>>(Optional.empty(), "absent"),
                new HasDescription("present <123>"))),

        assertThat("with Quality",
            new Present<>(new EqualTo<>(123)),
            new AllOf<>(
                new Passes<>(Optional.of(123)),
                new Fails<>(Optional.of(1234), "present <1234>"),
                new Fails<Optional<Integer>>(Optional.empty(), "absent"),
                new HasDescription("present <123>")))
    );

}
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

package org.saynotobugs.confidence.junit5.engine.testengine;

import org.saynotobugs.confidence.junit5.engine.Confidence;
import org.saynotobugs.confidence.junit5.engine.Verifiable;
import org.saynotobugs.confidence.junit5.engine.VerifiableGroup;
import org.saynotobugs.confidence.junit5.engine.verifiablegroup.Group;

import static org.saynotobugs.confidence.junit5.engine.Engine.assertThat;
import static org.saynotobugs.confidence.quality.Core.hasToString;
import static org.saynotobugs.confidence.quality.Core.supplies;


@Confidence
public final class ExampleConfidenceTest
{
    Verifiable passing_test = assertThat("abc", hasToString("abc"));
    Verifiable failingTest = assertThat("abc", hasToString("abcd"));
    Verifiable throwing_test = assertThat(() -> {throw new RuntimeException("error");}, supplies("abc"));

    VerifiableGroup group = new Group(
        assertThat("abc", hasToString("abc")),
        assertThat("abc", hasToString("abcd")),
        assertThat(() -> {throw new RuntimeException("error");}, supplies("abc"))
    );

    Verifiable[] array = {
        assertThat("abc", hasToString("abc")),
        assertThat("abc", hasToString("abcd")),
        assertThat(() -> {throw new RuntimeException("error");}, supplies("abc"))
    };
}
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

package org.saynotobugs.confidence.quality.grammar;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.Nothing;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class IsTest
{
    @Test
    void testValue()
    {
        assertThat(new Is<>(3),
            new AllOf<>(
                new Passes<>(3, "was 3"),
                new Fails<>(4, "was 4"),
                new HasDescription("is 3")));
    }


    @Test
    void testMatch()
    {
        assertThat(new Is<>(new Anything()),
            new AllOf<>(
                new Passes<>("12", "was \"12\""),
                new Passes<>(1, "was 1"),
                new Passes<>(new Object(), new DescribesAs(new MatchesPattern("was <.*>"))),
                new HasDescription("is <anything>")));
    }


    @Test
    void testMismatch()
    {
        assertThat(new Is<>(new Nothing()),
            new AllOf<>(
                new Fails<>(1, "was 1"),
                new HasDescription("is <nothing>")));
    }
}
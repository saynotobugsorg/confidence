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

package org.saynotobugs.confidence.quality.charsequence;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class HasLengthTest
{
    @Test
    void testIntCtor()
    {
        assertThat(new HasLength(3),
            new AllOf<>(
                new Passes<>("123", "\"123\" had length 3"),
                new Fails<>("", "\"\" had length 0"),
                new Fails<>("12", "\"12\" had length 2"),
                new Fails<>("1234", "\"1234\" had length 4"),
                new HasDescription("has length 3")));
    }


    @Test
    void testMatcherCtor()
    {
        assertThat(new HasLength(new LessThan<>(4)),
            new AllOf<>(
                new Passes<>("", "\"\" had length 0"),
                new Passes<>("12", "\"12\" had length 2"),
                new Passes<>("abc", "\"abc\" had length 3"),
                new Fails<>("abcd", "\"abcd\" had length 4"),
                new Fails<>("abcde", "\"abcde\" had length 5"),
                new HasDescription("has length less than 4")));
    }
}
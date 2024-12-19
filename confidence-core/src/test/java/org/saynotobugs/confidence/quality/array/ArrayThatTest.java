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

package org.saynotobugs.confidence.quality.array;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ArrayThatTest
{

    @Test
    void test()
    {
        assertThat(new ArrayThat(new Contains<>(3)),
            new AllOf<>(
                new Passes<Object>(new int[] { 1, 2, 3 }, "array contained 3"),
                new Passes<Object>(new int[] { 3 }, "array contained 3"),
                new Passes<Object>(new int[] { 3, 3, 3, 3, 3 }, "array contained 3"),
                new Fails<Object>(new int[] {}, "array did not contain any elements"),
                new Fails<Object>(new int[] { 1, 2, 4 }, "array [ 1, 2, 4 ] did not contain 3"),
                new Fails<>("abc", "\"abc\" was not an array"),
                new HasDescription("array that contains 3")
            ));
    }

}
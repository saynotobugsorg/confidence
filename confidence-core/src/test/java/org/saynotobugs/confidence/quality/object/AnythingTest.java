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

package org.saynotobugs.confidence.quality.object;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AnythingTest
{

    @Test
    void test()
    {
        assertThat(
            new Anything(),
            new AllOf<>(
                new Passes<>(123, "123"),
                new Passes<>("abc", "\"abc\""),
                new Passes<>(new String[] { "a", "b", "c" }, "[ \"a\", \"b\", \"c\" ]"),
                new Passes<>(new int[] { 1, 2, 3 }, "[ 1, 2, 3 ]"),
                new Passes<>(null, "<null>"),
                new Passes<>(new Seq<>(1, 2, 3), "[ 1, 2, 3 ]"),
                new HasDescription("<anything>")));
    }

}
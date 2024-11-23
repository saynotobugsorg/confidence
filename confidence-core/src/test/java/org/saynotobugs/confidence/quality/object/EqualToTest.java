/*
 * Copyright 2022 dmfs GmbH
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

package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class EqualToTest
{
    @Test
    void testSimpleValue()
    {
        assertThat(new EqualTo<>("123"),
            new AllOf<>(
                new Passes<Object>("123"),
                new Fails<>(new String[] { "123" }, "[ \"123\" ]"),
                new Fails<>("1234", "\"1234\""),
                new HasDescription("\"123\"")
            ));
    }


    @Test
    void testArray()
    {
        assertThat(new EqualTo<>(new String[] { "1", "2", "3" }),
            new AllOf<>(
                new Passes<>((Object[]) new String[][] { { "1", "2", "3" } }),
                new Fails<>("123", "\"123\""),
                new Fails<>(new String[] { "1", "2" }, "array that iterated [\n  ...\n  2: missing \"3\"\n]"),
                new HasDescription("[\n  \"1\"\n  \"2\"\n  \"3\"\n]")
            ));
    }
}
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

package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


class CompositeTest
{

    @Test
    void testNoDelegates()
    {
        assertThat(
            new Composite(),
            new DescribesAs(""));
    }


    @Test
    void testEmptyDelegate()
    {
        assertThat(
            new Composite(EMPTY),
            new DescribesAs(""));
    }


    @Test
    void testMultipleEmptyDelegates()
    {
        assertThat(
            new Composite(EMPTY, EMPTY, EMPTY),
            new DescribesAs(""));
    }


    @Test
    void testSingleDelegates()
    {
        assertThat(
            new Composite(new Text("abc")),
            new DescribesAs("abc"));
    }


    @Test
    void testMultipleDelegates()
    {
        assertThat(
            new Composite(new Text("abc"), new Text("xyz"), new Text("123")),
            new DescribesAs("abcxyz123"));
    }

}
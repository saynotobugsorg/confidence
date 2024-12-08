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

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.dmfs.jems2.iterable.EmptyIterable.emptyIterable;
import static org.saynotobugs.confidence.Assertion.assertThat;


class StructuredTest
{
    @Test
    void testNone()
    {
        assertThat(
            new Structured("in", "del", "out", emptyIterable()),
            new DescribesAs("inout"));
    }


    @Test
    void testOne()
    {
        assertThat(
            new Structured("in", "del", "out", new Seq<>(new Text("123"))),
            new DescribesAs("in123out"));
    }


    @Test
    void testMultipleDelimiterOnly()
    {
        assertThat(new Structured("del",
                new Seq<>(new Text("123"), new Text("abc"), new Text("xyz"))),
            new DescribesAs("123delabcdelxyz"));
    }


    @Test
    void testMultiple()
    {
        assertThat(new Structured("in", "del", "out",
                new Seq<>(new Text("123"), new Text("abc"), new Text("xyz"))),
            new DescribesAs("in123delabcdelxyzout"));
    }


    @Test
    void testSecondaryCtor()
    {
        assertThat(new Structured(new Text("---"),
                new Seq<>(new Text("123"), new Text("abc"), new Text("xyz"))),
            new DescribesAs("123---abc---xyz"));
    }

}
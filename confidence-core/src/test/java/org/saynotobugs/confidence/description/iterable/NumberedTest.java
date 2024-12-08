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

package org.saynotobugs.confidence.description.iterable;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.dmfs.jems2.iterable.EmptyIterable.emptyIterable;
import static org.saynotobugs.confidence.Assertion.assertThat;


class NumberedTest
{

    @Test
    void testEmpty()
    {
        assertThat(new Numbered(emptyIterable()),
            new Iterates<>(/*nothing*/));
    }


    @Test
    void testOneElement()
    {
        assertThat(new Numbered(new Seq<>(new Text("a"))),
            new Iterates<>(new DescribesAs("0: a")));
    }


    @Test
    void testMultipleElements()
    {
        assertThat(new Numbered(new Seq<>(new Text("a"), new Text("b"), new Text("c"))),
            new Iterates<>(new DescribesAs("0: a"),
                new DescribesAs("1: b"),
                new DescribesAs("2: c")));
    }


    @Test
    void testEmptyWithFunction()
    {
        assertThat(new Numbered((idx, desc) -> new Spaced(desc, new Text("(" + idx + ")")), emptyIterable()),
            new Iterates<>(/*nothing*/));
    }


    @Test
    void testOneElementWithFunction()
    {
        assertThat(new Numbered((idx, desc) -> new Spaced(desc, new Text("(" + idx + ")")), new Seq<>(new Text("a"))),
            new Iterates<>(new DescribesAs("a (0)")));
    }


    @Test
    void testMultipleElementsWithFunction()
    {
        assertThat(new Numbered((idx, desc) -> new Spaced(desc, new Text("(" + idx + ")")),
                new Seq<>(new Text("a"), new Text("b"), new Text("c"))),
            new Iterates<>(new DescribesAs("a (0)"),
                new DescribesAs("b (1)"),
                new DescribesAs("c (2)")));
    }

}
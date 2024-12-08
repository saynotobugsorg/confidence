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

package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.iterable.EmptyIterable.emptyIterable;
import static org.saynotobugs.confidence.Assertion.assertThat;


class ContainsTest
{

    @Test
    void testSingle()
    {
        assertThat(new Contains<>(123),
            new AllOf<>(
                new Passes<>(new Seq<>(123), "contained 123"),
                new Passes<>(new Seq<>(1, 2, 3, 123), "contained 123"),
                new Fails<Iterable<Integer>>(emptyIterable(), "[] did not contain 123"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 3), "[ 1, 2, 3 ] did not contain 123"),
                new HasDescription("contains 123")
            ));
    }


    @Test
    void testQuality()
    {
        assertThat(new Contains<>(new EqualTo<>(123)),
            new AllOf<>(
                new Passes<>(new Seq<>(123), "contained 123"),
                new Passes<>(new Seq<>(1, 2, 3, 123), "contained 123"),
                new Fails<Iterable<Integer>>(emptyIterable(), "[] did not contain 123"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 3), "[ 1, 2, 3 ] did not contain 123"),
                new HasDescription("contains 123")
            ));
    }

    @Test
    void testMultiple()
    {
        assertThat(new Contains<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(
                    new Seq<>(1, 2, 3), "contained all of {\n" +
                    "  1\n" +
                    "  2\n" +
                    "  3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(3, 1, 2), "contained all of {\n" +
                    "  1\n" +
                    "  2\n" +
                    "  3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(3, 3, 3, 1, 1, 1, 2, 2, 2), "contained all of {\n" +
                    "  1\n" +
                    "  2\n" +
                    "  3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(0, 1, 2, 3, 123), "contained all of {\n" +
                    "  1\n" +
                    "  2\n" +
                    "  3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(3, 2, 3, 123, 1), "contained all of {\n" +
                    "  1\n" +
                    "  2\n" +
                    "  3\n" +
                    "}"),
                new Fails<Iterable<Integer>>(emptyIterable(),
                    "[] did not contain {\n  1\n  2\n  3\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2), "[ 1, 2 ] did not contain {\n  3\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 2, 2), "[ 1, 2, 2, 2 ] did not contain {\n  3\n}"),
                new HasDescription("contains all of {\n  1\n  2\n  3\n}")
            ));
    }


    @Test
    void testMultipleMatchers()
    {
        assertThat(new Contains<>(new LessThan<>(1), new EqualTo<>(2), new GreaterThan<>(3)),
            new AllOf<>(
                new Passes<>(
                    new Seq<>(0, 2, 4), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(4, 2, 0), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(4, 4, 4, 2, 2, 2, 0, 0, 0), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(1, 0, 2, 3, 123), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(4, 2, 3, 123, 0), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Fails<Iterable<Integer>>(emptyIterable(),
                    "[] did not contain {\n  less than 1\n  2\n  greater than 3\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(2), "[ 2 ] did not contain {\n  less than 1\n  greater than 3\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(0, 2), "[ 0, 2 ] did not contain {\n  greater than 3\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(-10, 5, 6, 7), "[ -10, 5, 6, 7 ] did not contain {\n  2\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 2, 10, 100),
                    "[ 1, 2, 2, 10, 100 ] did not contain {\n  less than 1\n}"),
                new HasDescription("contains all of {\n  less than 1\n  2\n  greater than 3\n}")
            ));
    }


    @Test
    void testMultipleMatchersWithIterable()
    {
        assertThat(new Contains<>(new Seq<>(new LessThan<>(1), new EqualTo<>(2), new GreaterThan<>(3))),
            new AllOf<>(
                new Passes<>(
                    new Seq<>(0, 2, 4), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(4, 2, 0), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(4, 4, 4, 2, 2, 2, 0, 0, 0), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(1, 0, 2, 3, 123), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Passes<>(
                    new Seq<>(4, 2, 3, 123, 0), "contained all of {\n" +
                    "  less than 1\n" +
                    "  2\n" +
                    "  greater than 3\n" +
                    "}"),
                new Fails<Iterable<Integer>>(emptyIterable(),
                    "[] did not contain {\n  less than 1\n  2\n  greater than 3\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(2), "[ 2 ] did not contain {\n  less than 1\n  greater than 3\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(0, 2), "[ 0, 2 ] did not contain {\n  greater than 3\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(-10, 5, 6, 7), "[ -10, 5, 6, 7 ] did not contain {\n  2\n}"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 2, 10, 100),
                    "[ 1, 2, 2, 10, 100 ] did not contain {\n  less than 1\n}"),
                new HasDescription("contains all of {\n  less than 1\n  2\n  greater than 3\n}")
            ));
    }
}
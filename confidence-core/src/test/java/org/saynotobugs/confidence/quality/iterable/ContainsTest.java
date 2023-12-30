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
                new Passes<>(new Seq<>(123), new Seq<>(1, 2, 3, 123)),
                new Fails<Iterable<Integer>>(emptyIterable(), "[  ] did not contain <123>"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 3), "[ <1>,\n  <2>,\n  <3> ] did not contain <123>"),
                new HasDescription("contains <123>")
            ));
    }


    @Test
    void testQuality()
    {
        assertThat(new Contains<>(new EqualTo<>(123)),
            new AllOf<>(
                new Passes<>(new Seq<>(123), new Seq<>(1, 2, 3, 123)),
                new Fails<Iterable<Integer>>(emptyIterable(), "[  ] did not contain <123>"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 3), "[ <1>,\n  <2>,\n  <3> ] did not contain <123>"),
                new HasDescription("contains <123>")
            ));
    }

    @Test
    void testMultiple()
    {
        assertThat(new Contains<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(
                    new Seq<>(1, 2, 3),
                    new Seq<>(3, 1, 2),
                    new Seq<>(3, 3, 3, 1, 1, 1, 2, 2, 2),
                    new Seq<>(0, 1, 2, 3, 123),
                    new Seq<>(3, 2, 3, 123, 1)),
                new Fails<Iterable<Integer>>(emptyIterable(),
                    "[  ] did not contain { <1>,\n  <2>,\n  <3> }"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2), "[ <1>,\n  <2> ] did not contain { <3> }"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 2, 2), "[ <1>,\n  <2>,\n  <2>,\n  <2> ] did not contain { <3> }"),
                new HasDescription("contains all of { <1>,\n  <2>,\n  <3> }")
            ));
    }


    @Test
    void testMultipleMatchers()
    {
        assertThat(new Contains<>(new LessThan<>(1), new EqualTo<>(2), new GreaterThan<>(3)),
            new AllOf<>(
                new Passes<>(
                    new Seq<>(0, 2, 4),
                    new Seq<>(4, 2, 0),
                    new Seq<>(4, 4, 4, 2, 2, 2, 0, 0, 0),
                    new Seq<>(1, 0, 2, 3, 123),
                    new Seq<>(4, 2, 3, 123, 0)),
                new Fails<Iterable<Integer>>(emptyIterable(),
                    "[  ] did not contain { less than <1>,\n  <2>,\n  greater than <3> }"),
                new Fails<Iterable<Integer>>(new Seq<>(2), "[ <2> ] did not contain { less than <1>,\n  greater than <3> }"),
                new Fails<Iterable<Integer>>(new Seq<>(0, 2), "[ <0>,\n  <2> ] did not contain { greater than <3> }"),
                new Fails<Iterable<Integer>>(new Seq<>(-10, 5, 6, 7), "[ <-10>,\n  <5>,\n  <6>,\n  <7> ] did not contain { <2> }"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 2, 10, 100),
                    "[ <1>,\n  <2>,\n  <2>,\n  <10>,\n  <100> ] did not contain { less than <1> }"),
                new HasDescription("contains all of { less than <1>,\n  <2>,\n  greater than <3> }")
            ));
    }


    @Test
    void testMultipleMatchersWithIterable()
    {
        assertThat(new Contains<>(new Seq<>(new LessThan<>(1), new EqualTo<>(2), new GreaterThan<>(3))),
            new AllOf<>(
                new Passes<>(
                    new Seq<>(0, 2, 4),
                    new Seq<>(4, 2, 0),
                    new Seq<>(4, 4, 4, 2, 2, 2, 0, 0, 0),
                    new Seq<>(1, 0, 2, 3, 123),
                    new Seq<>(4, 2, 3, 123, 0)),
                new Fails<Iterable<Integer>>(emptyIterable(),
                    "[  ] did not contain { less than <1>,\n  <2>,\n  greater than <3> }"),
                new Fails<Iterable<Integer>>(new Seq<>(2), "[ <2> ] did not contain { less than <1>,\n  greater than <3> }"),
                new Fails<Iterable<Integer>>(new Seq<>(0, 2), "[ <0>,\n  <2> ] did not contain { greater than <3> }"),
                new Fails<Iterable<Integer>>(new Seq<>(-10, 5, 6, 7), "[ <-10>,\n  <5>,\n  <6>,\n  <7> ] did not contain { <2> }"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 2, 10, 100),
                    "[ <1>,\n  <2>,\n  <2>,\n  <10>,\n  <100> ] did not contain { less than <1> }"),
                new HasDescription("contains all of { less than <1>,\n  <2>,\n  greater than <3> }")
            ));
    }
}
package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.jems2.iterable.EmptyIterable;
import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;
import org.saynotobugs.confidence.test.quality.PassesPostMutation;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class IteratesTest
{

    @Test
    void testEmpty()
    {
        assertThat(new Iterates<>(/* nothing */),
            new AllOf<>(
                new Passes<Iterable<Integer>>(new EmptyIterable<>(), "iterated []"),
                new Fails<>(new Seq<>(1), "iterated [\n  0: additional 1\n]"),
                new Fails<>(new Seq<>(1, 2, 3), "iterated [\n  0: additional 1\n  1: additional 2\n  2: additional 3\n]"),
                new HasDescription("iterates []")
            ));
    }


    @Test
    void testOneValue()
    {
        assertThat(new Iterates<>(1),
            new AllOf<>(
                new Passes<>(new Seq<>(1), "iterated [\n  0: 1\n]"),
                new Fails<Iterable<Integer>>(new EmptyIterable<>(), "iterated [\n  0: missing 1\n]"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 2, 3), "iterated [\n  ...\n  1: additional 2\n  2: additional 3\n]"),
                new HasDescription("iterates [\n  0: 1\n]")
            ));
    }


    @Test
    void testMultipleValues()
    {
        assertThat(new Iterates<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(new Seq<>(1, 2, 3), "iterated [\n" +
                    "  0: 1\n" +
                    "  1: 2\n" +
                    "  2: 3\n" +
                    "]"),
                new Fails<Iterable<Integer>>(new EmptyIterable<>(), "iterated [\n  0: missing 1\n  1: missing 2\n  2: missing 3\n]"),
                new Fails<Iterable<Integer>>(new Seq<>(0, 2, 3, 4), "iterated [\n  0: 0\n  ...\n  3: additional 4\n]"),
                new HasDescription("iterates [\n" +
                    "  0: 1\n" +
                    "  1: 2\n" +
                    "  2: 3\n" +
                    "]")
            ));
    }


    @Test
    void testOneMatcher()
    {
        assertThat(new Iterates<>(new LessThan<>(1)),
            new AllOf<>(
                new Passes<>(new Seq<>(-1), "iterated [\n" +
                    "  0: -1\n" +
                    "]"),
                new Passes<>(new Seq<>(0), "iterated [\n" +
                    "  0: 0\n" +
                    "]"),
                new Fails<Iterable<Integer>>(new EmptyIterable<>(), "iterated [\n" +
                    "  0: missing less than 1\n" +
                    "]"),
                new Fails<Iterable<Integer>>(new Seq<>(1, 0, 3), "iterated [\n  0: 1\n  1: additional 0\n  2: additional 3\n]"),
                new HasDescription("iterates [\n" +
                    "  0: less than 1\n" +
                    "]")
            ));
    }


    @Test
    void testMultipleQualities()
    {
        assertThat(new Iterates<>(new LessThan<>(1), new GreaterThan<>(2), new LessThan<>(3)),
            new AllOf<>(
                new Passes<>(new Seq<>(0, 3, 2), "iterated [\n" +
                    "  0: 0\n" +
                    "  1: 3\n" +
                    "  2: 2\n" +
                    "]"),
                new Passes<>(new Seq<>(-1, 100, 0), "iterated [\n" +
                    "  0: -1\n" +
                    "  1: 100\n" +
                    "  2: 0\n" +
                    "]"),
                new Fails<Iterable<Integer>>(new EmptyIterable<>(),
                    "iterated [\n  0: missing less than 1\n  1: missing greater than 2\n  2: missing less than 3\n]"),
                new Fails<Iterable<Integer>>(new Seq<>(0, 2, 3, 4),
                    "iterated [\n  ...\n  1: 2\n  2: 3\n  3: additional 4\n]"),
                new HasDescription("iterates [\n  0: less than 1\n  1: greater than 2\n  2: less than 3\n]")
            ));
    }


    @Test
    void testNested()
    {
        assertThat(new Iterates<>(new Iterates<>(1, 2), new Iterates<>(3, 4)),
            new AllOf<>(
                new Passes<Iterable<Iterable<Integer>>>(new Seq<>(new Seq<>(1, 2), new Seq<>(3, 4)), "iterated [\n" +
                    "  0: iterated [\n" +
                    "    0: 1\n" +
                    "    1: 2\n" +
                    "  ]\n" +
                    "  1: iterated [\n" +
                    "    0: 3\n" +
                    "    1: 4\n" +
                    "  ]\n" +
                    "]"),
                new Fails<Iterable<Iterable<Integer>>>(new EmptyIterable<>(),
                    "iterated [\n  0: missing iterates [\n    0: 1\n    1: 2\n  ]\n  1: missing iterates [\n    0: 3\n    1: 4\n  ]\n]"),
                new Fails<Iterable<Iterable<Integer>>>(new Seq<>(new Seq<>(1, 2), new Seq<>(4, 4)),
                    "iterated [\n  ...\n  1: iterated [\n    0: 4\n    ...\n  ]\n]"),
                new HasDescription("iterates [\n  0: iterates [\n    0: 1\n    1: 2\n  ]\n  1: iterates [\n    0: 3\n    1: 4\n  ]\n]")
            ));
    }


    @Test
    void testOneValueWithPostMutation()
    {
        assertThat(new Iterates<>(1),
            new PassesPostMutation<>(() -> new ArrayList<>(singletonList(1)), new Text("clear list"), List::clear));
    }
}
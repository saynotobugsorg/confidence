package org.saynotobugs.confidence.description;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.dmfs.jems2.iterable.EmptyIterable.emptyIterable;
import static org.saynotobugs.confidence.Assertion.assertThat;


class IterableDescriptionTest
{
    @Test
    void testEmpty()
    {
        assertThat(
            new IterableDescription(emptyIterable()),
            new DescribesAs("[]"));
    }


    @Test
    void testOne()
    {
        assertThat(
            new IterableDescription(new Seq<>("abc")),
            new DescribesAs("[ \"abc\" ]"));
    }


    @Test
    void testTwo()
    {
        assertThat(
            new IterableDescription(new Seq<>("abc", "xyz")),
            new DescribesAs("[ \"abc\", \"xyz\" ]"));
    }


    @Test
    void testNested()
    {
        assertThat(
            new IterableDescription(new Seq<>(new Seq<>(1, 2, 3), new Seq<>("a", "b", "c"))),
            new DescribesAs("[ [ 1, 2, 3 ], [ \"a\", \"b\", \"c\" ] ]"));
    }
}
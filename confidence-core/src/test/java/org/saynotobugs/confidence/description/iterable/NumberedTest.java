package org.saynotobugs.confidence.description.iterable;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.TextDescription;
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
        assertThat(new Numbered(new Seq<>(new TextDescription("a"))),
            new Iterates<>(new DescribesAs("0: a")));
    }


    @Test
    void testMultipleElements()
    {
        assertThat(new Numbered(new Seq<>(new TextDescription("a"), new TextDescription("b"), new TextDescription("c"))),
            new Iterates<>(new DescribesAs("0: a"),
                new DescribesAs("1: b"),
                new DescribesAs("2: c")));
    }


    @Test
    void testEmptyWithFunction()
    {
        assertThat(new Numbered((idx, desc) -> new Delimited(desc, new TextDescription("(" + idx + ")")), emptyIterable()),
            new Iterates<>(/*nothing*/));
    }


    @Test
    void testOneElementWithFunction()
    {
        assertThat(new Numbered((idx, desc) -> new Delimited(desc, new TextDescription("(" + idx + ")")), new Seq<>(new TextDescription("a"))),
            new Iterates<>(new DescribesAs("a (0)")));
    }


    @Test
    void testMultipleElementsWithFunction()
    {
        assertThat(new Numbered((idx, desc) -> new Delimited(desc, new TextDescription("(" + idx + ")")),
                new Seq<>(new TextDescription("a"), new TextDescription("b"), new TextDescription("c"))),
            new Iterates<>(new DescribesAs("a (0)"),
                new DescribesAs("b (1)"),
                new DescribesAs("c (2)")));
    }

}
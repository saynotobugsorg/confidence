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
package org.saynotobugs.confidence.description;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.dmfs.jems2.iterable.EmptyIterable.emptyIterable;
import static org.saynotobugs.confidence.Assertion.assertThat;


class StructuredDescriptionTest
{
    @Test
    void testNone()
    {
        assertThat(
            new StructuredDescription("in", "del", "out", emptyIterable()),
            new DescribesAs("inout"));
    }


    @Test
    void testOne()
    {
        assertThat(
            new StructuredDescription("in", "del", "out", new Seq<>(new Text("123"))),
            new DescribesAs("in123out"));
    }


    @Test
    void testMultipleDelimiterOnly()
    {
        assertThat(new StructuredDescription("del",
                new Seq<>(new Text("123"), new Text("abc"), new Text("xyz"))),
            new DescribesAs("123delabcdelxyz"));
    }


    @Test
    void testMultiple()
    {
        assertThat(new StructuredDescription("in", "del", "out",
                new Seq<>(new Text("123"), new Text("abc"), new Text("xyz"))),
            new DescribesAs("in123delabcdelxyzout"));
    }


    @Test
    void testSecondaryCtor()
    {
        assertThat(new StructuredDescription(new Text("---"),
                new Seq<>(new Text("123"), new Text("abc"), new Text("xyz"))),
            new DescribesAs("123---abc---xyz"));
    }


}
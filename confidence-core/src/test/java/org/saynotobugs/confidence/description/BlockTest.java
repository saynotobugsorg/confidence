package org.saynotobugs.confidence.description;

import org.dmfs.jems2.iterable.EmptyIterable;
import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static jdk.internal.joptsimple.internal.Strings.EMPTY;
import static org.dmfs.jems2.iterable.EmptyIterable.emptyIterable;
import static org.saynotobugs.confidence.Assertion.assertThat;

class BlockTest
{
    @Test
    void testEmptyWithFallback()
    {
        assertThat(
            new Block("before [", ",", "]", emptyIterable()),
            new DescribesAs("before []"));
    }

    @Test
    void testEmpty()
    {
        assertThat(
            new Block("before [", ",", "]", emptyIterable(), "before ø"),
            new DescribesAs("before ø"));
    }

    @Test
    void testOne()
    {
        assertThat(
            new Block("before [", ",", "]", new Seq<>(new Text("abc")), "ø"),
            new DescribesAs("before [\n  abc\n]"));
    }

    @Test
    void testTwo()
    {
        assertThat(
            new Block("before [", ",", "]", new Seq<>(new Text("abc"), new Text("def")), "ø"),
            new DescribesAs("before [\n  abc,\n  def\n]"));
    }

    @Test
    void testThree()
    {
        assertThat(
            new Block("before [", ",", "]", new Seq<>(new Text("abc"), new Text("def"), new Text("ghi")), "ø"),
            new DescribesAs("before [\n  abc,\n  def,\n  ghi\n]"));
    }

    @Test
    void testNoSuffixEmptyWithFallback()
    {
        assertThat(
            new Block("before", ",", EMPTY, new EmptyIterable<>(), "ø"),
            new DescribesAs("ø"));
    }

    @Test
    void testNoSuffixOne()
    {
        assertThat(
            new Block("before", ",", EMPTY, new Seq<>(new Text("abc")), "ø"),
            new DescribesAs("before\n  abc\n"));
    }

    @Test
    void testNoSuffixTwo()
    {
        assertThat(
            new Block("before", ",", EMPTY, new Seq<>(new Text("abc"), new Text("def")), "ø"),
            new DescribesAs("before\n  abc,\n  def\n"));
    }
}
package org.saynotobugs.confidence.description;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;

class EnclosedTest
{
    @Test
    void testNonEmptyStringPrefixAndSuffix()
    {
        assertThat(new Enclosed("(", new Text("foo"), ")"),
            new DescribesAs("(foo)"));
    }

    @Test
    void testEmptyStringPrefixAndSuffix()
    {
        assertThat(new Enclosed("(", EMPTY, ")"),
            new DescribesAs("()"));
    }

    @Test
    void testNonEmptyStringPrefixAndSuffixWithEmpty()
    {
        assertThat(new Enclosed("(", new Text("foo"), ")", "--"),
            new DescribesAs("(foo)"));
    }

    @Test
    void testEmptyStringPrefixAndSuffixWithEmpty()
    {
        assertThat(new Enclosed("(", EMPTY, ")", "--"),
            new DescribesAs("--"));
    }

    @Test
    void testComplexStringPrefixAndSuffix()
    {
        assertThat(new Enclosed("(",
                new Indented(new Delimited(NEW_LINE, new Seq<>(new Text("line1"), new Text("line2")))),
                ")"),
            new DescribesAs("(line1\n  line2)"));
    }

    @Test
    void testEmptyIndentedStringPrefixAndSuffix()
    {
        assertThat(new Enclosed("(", new Indented(EMPTY), ")"),
            new DescribesAs("()"));
    }

    @Test
    void testComplexStringPrefixAndSuffixWithEmpty()
    {
        assertThat(new Enclosed("(",
                new Indented(new Delimited(NEW_LINE, new Seq<>(new Text("line1"), new Text("line2")))),
                ")", "--"),
            new DescribesAs("(line1\n  line2)"));
    }

    @Test
    void testEmptyIndentedStringPrefixAndSuffixWithEmpty()
    {
        assertThat(new Enclosed("(", new Indented(EMPTY), ")", "--"),
            new DescribesAs("--"));
    }

}
package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


class CompositeTest
{

    @Test
    void testNoDelegates()
    {
        assertThat(
            new Composite(),
            new DescribesAs(""));
    }


    @Test
    void testEmptyDelegate()
    {
        assertThat(
            new Composite(EMPTY),
            new DescribesAs(""));
    }


    @Test
    void testMultipleEmptyDelegates()
    {
        assertThat(
            new Composite(EMPTY, EMPTY, EMPTY),
            new DescribesAs(""));
    }


    @Test
    void testSingleDelegates()
    {
        assertThat(
            new Composite(new TextDescription("abc")),
            new DescribesAs("abc"));
    }


    @Test
    void testMultipleDelegates()
    {
        assertThat(
            new Composite(new TextDescription("abc"), new TextDescription("xyz"), new TextDescription("123")),
            new DescribesAs("abcxyz123"));
    }

}
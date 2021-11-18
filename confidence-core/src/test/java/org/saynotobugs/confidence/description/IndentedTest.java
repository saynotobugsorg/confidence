package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


class IndentedTest
{
    @Test
    void test()
    {
        assertThat(new Indented(NEW_LINE), new DescribesAs("\n  "));
        assertThat(new Indented(new Composite(NEW_LINE, NEW_LINE)), new DescribesAs("\n  \n  "));
    }
}
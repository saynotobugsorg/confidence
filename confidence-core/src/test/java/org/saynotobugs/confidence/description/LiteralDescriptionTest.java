package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.description.LiteralDescription.*;


class LiteralDescriptionTest
{
    @Test
    void test()
    {
        assertThat(NEW_LINE, new DescribesAs("\n"));
        assertThat(COMMA, new DescribesAs(","));
        assertThat(COMMA_NEW_LINE, new DescribesAs(",\n"));
        assertThat(DQUOTES, new DescribesAs("\""));
        assertThat(EMPTY, new DescribesAs(""));
        assertThat(NULL, new DescribesAs("<null>"));
        assertThat(SPACE, new DescribesAs(" "));
    }
}
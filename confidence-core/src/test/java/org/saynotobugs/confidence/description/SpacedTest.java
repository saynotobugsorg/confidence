package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class SpacedTest
{
    @Test
    void testDefaultDelimiter()
    {
        assertThat(new Spaced(), new DescribesAs(""));
        assertThat(new Spaced(new TextDescription("a")), new DescribesAs("a"));
        assertThat(new Spaced(new TextDescription("a"), new TextDescription("b"), new TextDescription("c")), new DescribesAs("a b c"));
    }

}
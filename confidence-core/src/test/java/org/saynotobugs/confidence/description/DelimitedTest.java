package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class DelimitedTest
{
    @Test
    void testCustomDelimiter()
    {
        assertThat(new Delimited("--"), new DescribesAs(""));
        assertThat(new Delimited("--", new Text("a")), new DescribesAs("a"));
        assertThat(new Delimited("--", new Text("a"), new Text("b"), new Text("c")), new DescribesAs("a--b--c"));
    }

}
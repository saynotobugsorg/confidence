package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;

class PrefixedTest
{
    @Test
    void testNonEmpty()
    {
        assertThat(new Prefixed("prefix", new Text("delegate"), "fallback"),
            new DescribesAs("prefixdelegate"));
    }

    @Test
    void testEmpty()
    {
        assertThat(new Prefixed("prefix", EMPTY, "fallback"),
            new DescribesAs("fallback"));
    }
}
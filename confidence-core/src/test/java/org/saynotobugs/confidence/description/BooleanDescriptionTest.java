package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;

class BooleanDescriptionTest
{
    @Test
    void testTrue()
    {
        assertThat(new BooleanDescription(true),
            new DescribesAs("true"));
    }

    @Test
    void testFalse()
    {
        assertThat(new BooleanDescription(false),
            new DescribesAs("false"));
    }
}
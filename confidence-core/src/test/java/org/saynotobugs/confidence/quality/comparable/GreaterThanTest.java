package org.saynotobugs.confidence.quality.comparable;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class GreaterThanTest
{
    @Test
    void test()
    {
        assertThat(new GreaterThan<>(10),
            new AllOf<>(
                new Passes<>(11, 12, 100, 1000),
                new Fails<>(10, "10"),
                new Fails<>(9, "9"),
                new HasDescription("greater than 10")
            ));
    }
}
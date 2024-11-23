package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class BothTest
{
    @Test
    void test()
    {
        assertThat(new Both<>(new LessThan<>(10), new GreaterThan<>(5)),
            new AllOf<>(
                new Passes<>(6, 7, 8, 9),
                new Fails<>(10, "both,\n  10 and\n  ..."),
                new Fails<>(3, "both,\n  ... and\n  3"),
                new HasDescription("both,\n  less than 10 and\n  greater than 5")
            ));
    }
}
package org.saynotobugs.confidence.quality.comparable;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class LessThanTest
{
    @Test
    void test()
    {
        assertThat(new LessThan<>(10),
            new AllOf<>(
                new Passes<>(9, 8, 7, 9, -1),
                new Fails<>(10, "<10>"),
                new Fails<>(11, "<11>"),
                new HasDescription("less than <10>")
            ));
    }
}
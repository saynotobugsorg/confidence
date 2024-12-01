package org.saynotobugs.confidence.quality.comparable;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ComparesEqualToTest
{
    @Test
    void test()
    {
        assertThat(new ComparesEqualTo<>(3),
            new AllOf<>(
                new Passes<>(3, "3"),
                new Fails<>(4, "4"),
                new HasDescription("compares equal to 3")));
    }
}
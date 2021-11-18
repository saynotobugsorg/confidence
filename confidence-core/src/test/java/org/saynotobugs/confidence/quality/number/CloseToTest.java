package org.saynotobugs.confidence.quality.number;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class CloseToTest
{

    @Test
    void test()
    {
        assertThat(new CloseTo(1.0, 0.01),
            new AllOf<>(
                new Passes<>(1.0, 1f, 1, 1.01, 0.99),
                new Fails<>(1.01001d, "<1.01001> differs from <1.0> by <0.01001>, which is more than the allowed <0.01>"),
                new HasDescription("differs from <1.0> by no more than <0.01>")
            ));
    }

}
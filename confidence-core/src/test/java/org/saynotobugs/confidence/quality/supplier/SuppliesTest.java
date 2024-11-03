package org.saynotobugs.confidence.quality.supplier;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class SuppliesTest
{

    @Test
    void test()
    {
        assertThat(new Supplies<>(123),
            new AllOf<>(
                new Passes<>(() -> 123),
                new Fails<>(() -> null, "supplied <null>"),
                new Fails<>(() -> 1234, "supplied 1234"),
                new HasDescription("supplies 123")
            ));
    }

}
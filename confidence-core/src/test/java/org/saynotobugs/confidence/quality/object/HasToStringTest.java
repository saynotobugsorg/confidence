package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class HasToStringTest
{

    @Test
    void test()
    {
        assertThat(new HasToString("123"),
            new AllOf<>(
                new Passes<>("123", 123),
                new Fails<>("1234", "had toString() \"1234\""),
                new HasDescription("has toString() \"123\"")));
    }

}
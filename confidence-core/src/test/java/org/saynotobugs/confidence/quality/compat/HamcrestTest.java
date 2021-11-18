package org.saynotobugs.confidence.quality.compat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class HamcrestTest
{
    @Test
    void test()
    {
        assertThat(new Hamcrest<>(Matchers.equalTo(123)),
            new AllOf<>(
                new Passes<>(123),
                new Fails<>(12, "was <12>"),
                new HasDescription("<123>")
            ));
    }
}
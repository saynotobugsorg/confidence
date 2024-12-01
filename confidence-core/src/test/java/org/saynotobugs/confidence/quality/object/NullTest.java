package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class NullTest
{
    @Test
    void test()
    {
        assertThat(new Null(),
            new AllOf<>(
                new Passes<>(null, "<null>"),
                new Fails<>(123, "123"),
                new HasDescription("<null>")));
    }
}
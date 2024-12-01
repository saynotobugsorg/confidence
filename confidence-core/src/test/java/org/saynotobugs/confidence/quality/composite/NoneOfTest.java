package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class NoneOfTest
{
    @Test
    void test()
    {
        assertThat(new NoneOf<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(0, ""),
                new Passes<>(4, ""),
                new Passes<>(5, ""),
                new Passes<>(6, ""),
                new Fails<>(1, "was\n  1\n  ..."),
                new Fails<>(2, "was\n  ...\n  2\n  ..."),
                new Fails<>(3, "was\n  ...\n  3"),
                new HasDescription("None of\n  1\n  2\n  3")));
    }


    @Test
    void testMatchers()
    {
        assertThat(new NoneOf<>(new EqualTo<>(1), new EqualTo<>(2), new EqualTo<>(3)),
            new AllOf<>(
                new Passes<>(0, ""),
                new Passes<>(4, ""),
                new Passes<>(5, ""),
                new Passes<>(6, ""),
                new Fails<>(1, "was\n  1\n  ..."),
                new Fails<>(2, "was\n  ...\n  2\n  ..."),
                new Fails<>(3, "was\n  ...\n  3"),
                new HasDescription("None of\n  1\n  2\n  3")));
    }
}
package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AnyOfTest
{
    @Test
    void test()
    {
        assertThat(new AnyOf<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(1, "1"),
                new Passes<>(2, "2"),
                new Passes<>(3, "3"),
                new Fails<>(0, "0 was none of\n  1\n  2\n  3"),
                new Fails<>(4, "4 was none of\n  1\n  2\n  3"),
                new HasDescription("any of\n  0: 1\n  1: 2\n  2: 3")
            ));
    }


    @Test
    void testMatchers()
    {
        assertThat(new AnyOf<>(new EqualTo<>(1), new EqualTo<>(2), new EqualTo<>(3), new LessThan<>(2)),
            new AllOf<>(
                new Passes<>(1, "1"),
                new Passes<>(2, "2"),
                new Passes<>(3, "3"),
                new Fails<>(0, "0 was none of\n  1\n  2\n  3"),
                new Fails<>(4, "4 was none of\n  1\n  2\n  3"),
                new HasDescription("any of\n  0: 1\n  1: 2\n  2: 3\n  3: less than 2")
            ));
    }
}
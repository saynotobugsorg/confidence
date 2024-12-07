package org.saynotobugs.confidence.quality.composite;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static java.util.Arrays.asList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class InTest
{

    @Test
    void testValues()
    {
        assertThat(new In<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(1, "1 in \n" +
                    "  0: 1\n" +
                    "  ..."),
                new Passes<>(2, "2 in \n" +
                    "  ...\n" +
                    "  1: 2\n" +
                    "  ..."),
                new Passes<>(3, "3 in \n" +
                    "  ...\n" +
                    "  2: 3"),
                new Fails<>(4, "4 not in \n  0: 1\n  1: 2\n  2: 3"),
                new Fails<>(0, "0 not in \n  0: 1\n  1: 2\n  2: 3"),
                new HasDescription("in\n  0: 1\n  1: 2\n  2: 3")));
    }


    @Test
    void testCollection()
    {
        assertThat(new In<>(asList(1, 2, 3)),
            new AllOf<>(
                new Passes<>(1, "1 in \n" +
                    "  0: 1\n" +
                    "  ..."),
                new Passes<>(2, "2 in \n" +
                    "  ...\n" +
                    "  1: 2\n" +
                    "  ..."),
                new Passes<>(3, "3 in \n" +
                    "  ...\n" +
                    "  2: 3"),
                new Fails<>(4, "4 not in \n  0: 1\n  1: 2\n  2: 3"),
                new Fails<>(0, "0 not in \n  0: 1\n  1: 2\n  2: 3"),
                new HasDescription("in\n  0: 1\n  1: 2\n  2: 3")));
    }


    @Test
    void testMatchers()
    {
        assertThat(new In<>(new EqualTo<>(1), new EqualTo<>(2), new EqualTo<>(3)),
            new AllOf<>(
                new Passes<>(1, "1 in \n" +
                    "  0: 1\n" +
                    "  ..."),
                new Passes<>(2, "2 in \n" +
                    "  ...\n" +
                    "  1: 2\n" +
                    "  ..."),
                new Passes<>(3, "3 in \n" +
                    "  ...\n" +
                    "  2: 3"),
                new Fails<>(4, "4 not in \n  0: 1\n  1: 2\n  2: 3"),
                new Fails<>(0, "0 not in \n  0: 1\n  1: 2\n  2: 3"),
                new HasDescription("in\n  0: 1\n  1: 2\n  2: 3")));
    }


    @Test
    void testMatcherIterable()
    {
        assertThat(new In<>(new Seq<>(new EqualTo<>(1), new EqualTo<>(2), new EqualTo<>(3))),
            new AllOf<>(
                new Passes<>(1, "1 in \n" +
                    "  0: 1\n" +
                    "  ..."),
                new Passes<>(2, "2 in \n" +
                    "  ...\n" +
                    "  1: 2\n" +
                    "  ..."),
                new Passes<>(3, "3 in \n" +
                    "  ...\n" +
                    "  2: 3"),
                new Fails<>(4, "4 not in \n  0: 1\n  1: 2\n  2: 3"),
                new Fails<>(0, "0 not in \n  0: 1\n  1: 2\n  2: 3"),
                new HasDescription("in\n  0: 1\n  1: 2\n  2: 3")));
    }
}
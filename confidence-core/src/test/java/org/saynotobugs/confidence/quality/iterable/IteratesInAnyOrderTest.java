package org.saynotobugs.confidence.quality.iterable;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static java.util.Arrays.asList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class IteratesInAnyOrderTest
{
    @Test
    void test()
    {
        assertThat(new IteratesInAnyOrder<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(asList(1, 2, 3), ""),
                new Passes<>(asList(1, 3, 2), ""),
                new Passes<>(asList(2, 1, 3), ""),
                new Passes<>(asList(2, 3, 1), ""),
                new Passes<>(asList(3, 1, 2), ""),
                new Passes<>(asList(3, 2, 1), ""),
                new Fails<>(asList(0, 1, 2, 3), "iterated also [0]\n  did not iterate []"),
                new Fails<>(asList(1, 2), "iterated also []\n  did not iterate [3]"),
                new Fails<>(asList(-1, 0, 1, 2), "iterated also [0,\n    -1]\n  did not iterate [3]"),
                new HasDescription("iterates in any order [1,\n  2,\n  3]")
            ));
    }


    @Test
    void testMatchers()
    {
        assertThat(new IteratesInAnyOrder<>(new EqualTo<>(1), new EqualTo<>(2), new EqualTo<>(3)),
            new AllOf<>(
                new Passes<>(asList(1, 2, 3), ""),
                new Passes<>(asList(1, 3, 2), ""),
                new Passes<>(asList(2, 1, 3), ""),
                new Passes<>(asList(2, 3, 1), ""),
                new Passes<>(asList(3, 1, 2), ""),
                new Passes<>(asList(3, 2, 1), ""),
                new Fails<>(asList(0, 1, 2, 3), "iterated also [0]\n  did not iterate []"),
                new Fails<>(asList(1, 2), "iterated also []\n  did not iterate [3]"),
                new Fails<>(asList(-1, 0, 1, 2), "iterated also [0,\n    -1]\n  did not iterate [3]"),
                new HasDescription("iterates in any order [1,\n  2,\n  3]")
            ));
    }


    @Test
    void testMatchers2()
    {
        assertThat(new IteratesInAnyOrder<>(new LessThan<>(10), new LessThan<>(20), new GreaterThan<>(10)),
            new AllOf<>(
                new Passes<>(asList(1, 1, 11), ""),
                new Passes<>(asList(1, 11, 11), ""),
                new Passes<>(asList(1, 11, 21), ""),
                new Passes<>(asList(21, 11, 1), ""),
                new Passes<>(asList(11, 11, 1), ""),
                new Passes<>(asList(11, 1, 1), ""),
                new Fails<>(asList(0, 1, 2, 3), "iterated also []\n  did not iterate [greater than 10]"),
                new Fails<>(asList(1, 1, 1), "iterated also []\n  did not iterate [greater than 10]"),
                new Fails<>(asList(1, 2), "iterated also []\n  did not iterate [greater than 10]"),
                new Fails<>(asList(-1, 0, 1, 2), "iterated also []\n  did not iterate [greater than 10]"),
                new HasDescription("iterates in any order [less than 10,\n  less than 20,\n  greater than 10]")
            ));
    }


    @Test
    void testMatchers3()
    {
        assertThat(new IteratesInAnyOrder<>(new LessThan<>(10), new LessThan<>(10), new GreaterThan<>(10)),
            new AllOf<>(
                new Passes<>(asList(1, 1, 11), ""),
                new Passes<>(asList(11, 1, 1), ""),
                new Passes<>(asList(1, 11, 1), ""),
                new Fails<>(asList(0, 11, 11), "No permutation of [less than 10,\n  less than 10,\n  greater than 10] matched [ 0, 11, 11 ]"),
                new Fails<>(asList(1, 11),
                    "[ 1, 11 ] has fewer elements than [less than 10,\n  less than 10,\n  greater than 10]"),
                new Fails<>(asList(1, 11, 11, 11),
                    "[ 1, 11, 11, 11 ] has more elements than [less than 10,\n  less than 10,\n  greater than 10]"),
                new Fails<>(asList(11, 11, 11), "iterated also []\n  did not iterate [less than 10,\n    less than 10]"),
                new HasDescription("iterates in any order [less than 10,\n  less than 10,\n  greater than 10]")
            ));
    }
}
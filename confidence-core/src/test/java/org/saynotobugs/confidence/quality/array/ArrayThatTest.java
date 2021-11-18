package org.saynotobugs.confidence.quality.array;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ArrayThatTest
{

    @Test
    void test()
    {
        assertThat(new ArrayThat(new Contains<>(3)),
            new AllOf<>(
                new Passes<Object>(new int[] { 1, 2, 3 }, new int[] { 3 }, new int[] { 3, 3, 3, 3, 3 }),
                new Fails<Object>(new int[] {}, "(1) array [  ] did not contain <3>"),
                new Fails<Object>(new int[] { 1, 2, 4 }, "(1) array [ <1>,\n  <2>,\n  <4> ] did not contain <3>"),
                new Fails<>("abc", "(0) not an array"),
                new HasDescription("(0) an array\n  and\n  (1) array that contains <3>")
            ));
    }

}
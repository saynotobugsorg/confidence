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
                new Passes<Object>(new int[] { 1, 2, 3 }, "array contained 3"),
                new Passes<Object>(new int[] { 3 }, "array contained 3"),
                new Passes<Object>(new int[] { 3, 3, 3, 3, 3 }, "array contained 3"),
                new Fails<Object>(new int[] {}, "array [] did not contain 3"),
                new Fails<Object>(new int[] { 1, 2, 4 }, "array [ 1, 2, 4 ] did not contain 3"),
                new Fails<>("abc", "\"abc\" was not an array"),
                new HasDescription("array that contains 3")
            ));
    }

}
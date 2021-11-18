package org.saynotobugs.confidence.utils;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.quality.object.InstanceOf;
import org.saynotobugs.confidence.quality.object.Throwing;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ArrayIterableTest
{

    @Test
    void testEmpty()
    {
        assertThat(
            new ArrayIterable(new int[] {}),
            new Iterates<>());
    }


    @Test
    void testInt()
    {
        assertThat(
            new ArrayIterable(new int[] { 1, 2, 3 }),
            new Iterates<>(1, 2, 3));
    }


    @Test
    void testBoolean()
    {
        assertThat(
            new ArrayIterable(new boolean[] { true, false, false, true }),
            new Iterates<>(true, false, false, true));
    }


    @Test
    void testNestedInt()
    {
        assertThat(
            new ArrayIterable(new int[][] { new int[] { 1, 2 }, new int[] { 3, 4 } }),
            new Iterates<>(new int[] { 1, 2 }, new int[] { 3, 4 }));
    }


    @Test
    void testNonArray()
    {
        assertThat(() -> new ArrayIterable("abc"),
            new Throwing(new InstanceOf<>(RuntimeException.class)));
    }
}
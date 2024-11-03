package org.saynotobugs.confidence.quality.charsequence;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class HasLengthTest
{
    @Test
    void testIntCtor()
    {
        assertThat(new HasLength(3),
            new AllOf<>(
                new Passes<>("123", "abc"),
                new Fails<>("", "had length 0"),
                new Fails<>("12", "had length 2"),
                new Fails<>("1234", "had length 4"),
                new HasDescription("has length 3")));
    }


    @Test
    void testMatcherCtor()
    {
        assertThat(new HasLength(new LessThan<>(4)),
            new AllOf<>(
                new Passes<>("", "12", "abc"),
                new Fails<>("abcd", "had length 4"),
                new Fails<>("abcde", "had length 5"),
                new HasDescription("has length less than 4")));
    }
}
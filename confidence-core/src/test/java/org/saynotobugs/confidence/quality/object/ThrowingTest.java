package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.trivial.Anything;
import org.saynotobugs.confidence.quality.trivial.Nothing;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.NoSuchElementException;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ThrowingTest
{

    @Test
    void test()
    {
        assertThat(new Throwing(new Anything()),
            new AllOf<>(
                new Passes<>((Throwing.Breakable) () -> {throw new NoSuchElementException();}),
                new Fails<>(() -> {}, "not throwing <anything>"),
                new HasDescription("throwing <anything>")
            ));
    }


    @Test
    void testNothing()
    {
        assertThat(new Throwing(new Nothing()),
            new AllOf<>(
                new Fails<>(() -> {throw new NoSuchElementException();}, "throwing <java.util.NoSuchElementException>"),
                new Fails<>(() -> {}, "not throwing <nothing>"),
                new HasDescription("throwing <nothing>")
            ));
    }


    @Test
    void testClass()
    {
        assertThat(new Throwing(NoSuchElementException.class),
            new AllOf<>(
                new Passes<>((Throwing.Breakable) () -> {throw new NoSuchElementException();}),
                new Fails<>(() -> {}, "not throwing instance of <class java.util.NoSuchElementException>"),
                new HasDescription("throwing instance of <class java.util.NoSuchElementException>")
            ));
    }
}
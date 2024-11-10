package org.saynotobugs.confidence.utils;

import org.dmfs.jems2.iterable.EmptyIterable;
import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Grammar.is;
import static org.saynotobugs.confidence.core.quality.Iterable.emptyIterable;
import static org.saynotobugs.confidence.core.quality.Iterable.iterates;

class UntilTest
{
    @Test
    void testEmpty()
    {
        assertThat(new Until<>(s -> s.length() > 3, new EmptyIterable<String>()),
            is(emptyIterable()));
    }

    @Test
    void testOneElementNoMatch()
    {
        assertThat(new Until<>(s -> s.length() > 3, new Seq<>("ab")),
            iterates("ab"));
    }

    @Test
    void testMultipleElementsNoMatch()
    {
        assertThat(new Until<>(s -> s.length() > 3, new Seq<>("ab", "cd", "ef")),
            iterates("ab", "cd", "ef"));
    }


    @Test
    void testOneElementWithMatch()
    {
        assertThat(new Until<>(s -> s.length() > 3, new Seq<>("abcd")),
            iterates("abcd"));
    }

    @Test
    void testMultipleElementsFirstMatch()
    {
        assertThat(new Until<>(s -> s.length() > 3, new Seq<>("abcd", "cd", "ef")),
            iterates("abcd"));
    }

    @Test
    void testMultipleElementsSecondMatch()
    {
        assertThat(new Until<>(s -> s.length() > 3, new Seq<>("ab", "cdef", "ef")),
            iterates("ab", "cdef"));
    }

    @Test
    void testMultipleElementsLastMatch()
    {
        assertThat(new Until<>(s -> s.length() > 3, new Seq<>("ab", "cd", "efgh")),
            iterates("ab", "cd", "efgh"));
    }

}
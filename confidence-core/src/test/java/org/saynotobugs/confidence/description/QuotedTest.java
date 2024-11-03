package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class QuotedTest
{

    @Test
    void testAllEmpty()
    {
        assertThat(new Quoted("", scribe -> {}),
            new DescribesAs(""));
    }


    @Test
    void testEmptyDelegate()
    {
        assertThat(new Quoted("abc", scribe -> {}),
            new DescribesAs("abcabc"));
    }


    @Test
    void testEmptyQuotes()
    {
        assertThat(new Quoted("", scribe -> scribe.append("123")),
            new DescribesAs("123"));
    }


    @Test
    void testNonEmpty()
    {
        assertThat(new Quoted("abc", scribe -> scribe.append("123")),
            new DescribesAs("abc123abc"));
    }


    @Test
    void testEntryAndExit()
    {
        assertThat(new Quoted("abc", scribe -> scribe.append("123"), "xyz"),
            new DescribesAs("abc123xyz"));
    }
}
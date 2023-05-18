package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class TextTest
{

    @Test
    void testEmpty()
    {
        assertThat(
            new Text(""),
            new DescribesAs(""));
    }


    @Test
    void testNonEmpty()
    {
        assertThat(
            new Text("abc"),
            new DescribesAs("abc"));
    }


    @Test
    void testSpecialChars()
    {
        assertThat(
            new Text("\\\n\t\r\b"),
            new DescribesAs("\\\\\\n\\t\\r\\b"));
    }

}
package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class TextDescriptionTest
{

    @Test
    void testEmpty()
    {
        assertThat(
            new TextDescription(""),
            new DescribesAs(""));
    }


    @Test
    void testNonEmpty()
    {
        assertThat(
            new TextDescription("abc"),
            new DescribesAs("abc"));
    }


    @Test
    void testSpecialChars()
    {
        assertThat(
            new TextDescription("\\\n\t\r\b"),
            new DescribesAs("\\\\\\n\\t\\r\\b"));
    }

}
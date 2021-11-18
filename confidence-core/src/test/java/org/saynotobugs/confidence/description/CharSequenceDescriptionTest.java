package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class CharSequenceDescriptionTest
{
    @Test
    void testEmpty()
    {
        assertThat(new CharSequenceDescription(""), new DescribesAs("\"\""));
    }


    @Test
    void testNonEmpty()
    {
        assertThat(new CharSequenceDescription("abc"), new DescribesAs("\"abc\""));
    }


    @Test
    void testEscaped()
    {
        assertThat(new CharSequenceDescription("a \"b\" c"), new DescribesAs("\"a \\\"b\\\" c\""));
        assertThat(new CharSequenceDescription("a \t c"), new DescribesAs("\"a \\t c\""));
        assertThat(new CharSequenceDescription("a \n c"), new DescribesAs("\"a \\n c\""));
        assertThat(new CharSequenceDescription("a \r c"), new DescribesAs("\"a \\r c\""));
        assertThat(new CharSequenceDescription("a \\ c"), new DescribesAs("\"a \\\\ c\""));
        assertThat(new CharSequenceDescription("a \b c"), new DescribesAs("\"a \\b c\""));
        assertThat(new CharSequenceDescription("a \f c"), new DescribesAs("\"a \\f c\""));
    }
}
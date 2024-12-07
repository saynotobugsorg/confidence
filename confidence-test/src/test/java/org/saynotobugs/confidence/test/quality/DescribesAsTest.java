package org.saynotobugs.confidence.test.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;

import java.util.regex.Pattern;

import static org.saynotobugs.confidence.Assertion.assertThat;


class DescribesAsTest
{

    @Test
    void test()
    {
        assertThat(new DescribesAs("123"),
            new AllOf<>(
                new Passes<>(scribe -> scribe.append("123"),
                    "described as\n" +
                        "  ----\n" +
                        "  \"123\"\n" +
                        "  ----"),
                new Fails<>(scribe -> scribe.append("abc"), "described as\n  ----\n  \"abc\"\n  ----"),
                new HasDescription("describes as\n  ----\n  \"123\"\n  ----")
            ));
    }


    @Test
    void testPattern()
    {
        assertThat(new DescribesAs(Pattern.compile("\\w123\\w")),
            new AllOf<>(
                new Passes<>(scribe -> scribe.append("a123b"),
                    "described as\n" +
                        "  ----\n" +
                        "  matches pattern /\\\\w123\\\\w/\n" +
                        "  ----"),
                new Fails<>(scribe -> scribe.append("ab123"), "described as\n  ----\n  \"ab123\" mismatched pattern /\\\\w123\\\\w/\n  ----"),
                new HasDescription("describes as\n  ----\n  matches pattern /\\\\w123\\\\w/\n  ----")
            ));
    }

}
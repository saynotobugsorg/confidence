package org.saynotobugs.confidence.quality.charsequence;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ContainsPatternTest
{
    @Test
    void test()
    {
        assertThat(new ContainsPattern("123"),
            new AllOf<>(
                new Passes<>("123", "\"123\" contained pattern /123/"),
                new Passes<>("abc123xyz", "\"abc123xyz\" contained pattern /123/"),
                new Fails<>("12", "\"12\" did not contain pattern /123/"),
                new Fails<>("abc", "\"abc\" did not contain pattern /123/"),
                new HasDescription("contains pattern /123/")
            ));
    }
}
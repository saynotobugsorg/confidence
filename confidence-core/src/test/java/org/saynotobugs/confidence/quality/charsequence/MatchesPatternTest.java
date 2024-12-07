package org.saynotobugs.confidence.quality.charsequence;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class MatchesPatternTest
{
    @Test
    void test()
    {
        assertThat(new MatchesPattern("^.*123.*$"),
            new AllOf<>(
                new Passes<>("123", "\"123\" matched pattern /^.*123.*$/"),
                new Passes<>("abc123xyz", "\"abc123xyz\" matched pattern /^.*123.*$/"),
                new Fails<>("12", "\"12\" mismatched pattern /^.*123.*$/"),
                new Fails<>("abc", "\"abc\" mismatched pattern /^.*123.*$/"),
                new HasDescription("matches pattern /^.*123.*$/")
            ));
    }
}
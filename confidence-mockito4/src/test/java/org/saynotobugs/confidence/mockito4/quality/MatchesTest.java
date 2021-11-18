package org.saynotobugs.confidence.mockito4.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.mockito4.quality.Matches;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class MatchesTest
{

    @Test
    void test()
    {
        assertThat(new Matches<>("123"),
            new AllOf<>(
                new Passes<>("123"::equals),
                new Fails<>(arg -> false),
                new HasDescription("matches \"123\"")
            ));
    }

}
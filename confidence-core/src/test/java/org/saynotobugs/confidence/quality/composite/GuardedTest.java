package org.saynotobugs.confidence.quality.composite;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.charsequence.HasLength;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class GuardedTest
{
    @Test
    void test()
    {
        assertThat(new Guarded<>(new Seq<>(new HasLength(3)), new EqualTo<>("abc")),
            new AllOf<>(
                new Passes<>("abc"),
                new Fails<>("xyz", "\"xyz\""),
                new Fails<>("abcd", "had length 4"),
                new HasDescription("\"abc\"")
            )
        );
    }

}
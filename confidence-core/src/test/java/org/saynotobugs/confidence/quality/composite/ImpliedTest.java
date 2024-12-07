package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.charsequence.HasLength;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ImpliedTest
{
    @Test
    void test()
    {
        assertThat(new Implied<>(new HasLength(3), new DescribedAs<String>(
                new TextAndOriginal<>("was equal to"),
                new TextAndOriginal<>("was not equal to"),
                new TextAndOriginal<>("is equal to"),
                new EqualTo<>("abc"))),
            new AllOf<>(
                new Passes<>("abc", "was equal to \"abc\""),
                new Fails<>("xyz", "was not equal to \"xyz\""),
                new Fails<>("abcd", "\"abcd\" had length 4"),
                new HasDescription("is equal to \"abc\"")
            )
        );
    }

}
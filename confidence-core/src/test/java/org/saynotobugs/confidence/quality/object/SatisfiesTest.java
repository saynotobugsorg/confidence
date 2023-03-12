package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class SatisfiesTest
{

    @Test
    void test()
    {
        assertThat(new Satisfies<>(String::isEmpty,
                s -> new Spaced(new ValueDescription(s), new TextDescription("was not empty")), new TextDescription("is empty")),
            new AllOf<>(
                new Passes<>(""),
                new Fails<>("abc", "\"abc\" was not empty"),
                new HasDescription("is empty")
            ));
    }

}
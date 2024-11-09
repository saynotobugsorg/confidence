package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.valuedescription.Value;
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
                s -> new Spaced(new Value(s), new Text("was not empty")), new Text("is empty")),
            new AllOf<>(
                new Passes<>(""),
                new Fails<>("abc", "\"abc\" was not empty"),
                new HasDescription("is empty")
            ));
    }

}
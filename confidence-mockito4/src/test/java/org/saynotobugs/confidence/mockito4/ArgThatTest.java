package org.saynotobugs.confidence.mockito4;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.mockito4.quality.Matches;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Not;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.quality.object.HasToString;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ArgThatTest
{

    @Test
    void test()
    {
        assertThat(new ArgThat<>(new EqualTo<>("123")),
            new AllOf<>(
                new Matches<>("123"),
                new Not<>(new Matches<>("1234")),
                new HasToString("ArgThat \"123\"")
            ));
    }

}
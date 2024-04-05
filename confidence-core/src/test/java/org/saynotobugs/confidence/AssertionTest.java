package org.saynotobugs.confidence;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.Nothing;
import org.saynotobugs.confidence.quality.object.Throwing;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AssertionTest
{

    @Test
    void test()
    {
        assertThat(() -> assertThat("123", new Nothing()),
            new Is<>(new Throwing(AssertionError.class)));
    }

}
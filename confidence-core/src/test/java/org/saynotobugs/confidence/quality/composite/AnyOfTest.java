package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AnyOfTest
{
    @Test
    void test()
    {
        assertThat(new AnyOf<>(1, 2, 3),
            new AllOf<>(
                new Passes<>(1, 2, 3),
                new Fails<>(0, "<0> neither <1> nor \n  <2> nor \n  <3>"),
                new Fails<>(4, "<4> neither <1> nor \n  <2> nor \n  <3>"),
                new HasDescription("<1> or <2> or <3>")
            ));
    }


    @Test
    void testMatchers()
    {
        assertThat(new AnyOf<>(new EqualTo<>(1), new EqualTo<>(2), new EqualTo<>(3)),
            new AllOf<>(
                new Passes<>(1, 2, 3),
                new Fails<>(0, "<0> neither <1> nor \n  <2> nor \n  <3>"),
                new Fails<>(4, "<4> neither <1> nor \n  <2> nor \n  <3>"),
                new HasDescription("<1> or <2> or <3>")
            ));
    }
}
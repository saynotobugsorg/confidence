package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.trivial.Anything;
import org.saynotobugs.confidence.quality.trivial.Nothing;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AllOfTest
{
    @Test
    void testMatch()
    {
        assertThat(new AllOf<>(), new Passes<>(123, "abc"));
        assertThat(new AllOf<>(new Anything()), new Passes<>(123, "abc"));
        assertThat(new AllOf<>(new Anything(), new Anything()), new Passes<>(123, "abc"));
    }


    @Test
    void testMismatch()
    {
        assertThat(new AllOf<>(new Nothing()), new Fails<>(123, "{ <123> }"));
        assertThat(new AllOf<>(new Anything(), new Nothing()), new Fails<>(123, "{ ...\n  <123> }"));
        assertThat(new AllOf<>(new Nothing(), new Anything(), new Anything()), new Fails<>(123, "{ <123>\n  ... }"));
    }
}
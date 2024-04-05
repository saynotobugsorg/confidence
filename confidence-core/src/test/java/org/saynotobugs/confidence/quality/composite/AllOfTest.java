package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.Nothing;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.Passes;
import org.saynotobugs.confidence.test.quality.PassesPostMutation;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
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


    @Test
    void testPostMutation()
    {
        assertThat(new AllOf<>(new Contains<>(1), new Contains<>(2)),
            new PassesPostMutation<>(() -> new ArrayList<>(asList(1, 2)), new Text("clear list"), List::clear));
    }
}
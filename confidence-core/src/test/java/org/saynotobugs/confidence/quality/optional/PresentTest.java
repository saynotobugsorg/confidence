package org.saynotobugs.confidence.quality.optional;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.Optional;

import static org.saynotobugs.confidence.Assertion.assertThat;


class PresentTest
{

    @Test
    void testDefault()
    {
        assertThat(new Present<>(),
            new AllOf<>(
                new Passes<Optional<Object>>(Optional.of(123), Optional.of(1234), Optional.of("abc")),
                new Fails<>(Optional.empty(), "absent"),
                new HasDescription("present <anything>")));
    }


    @Test
    void testWithValue()
    {
        assertThat(new Present<>(123),
            new AllOf<>(
                new Passes<>(Optional.of(123)),
                new Fails<>(Optional.of(1234), "present <1234>"),
                new Fails<Optional<Integer>>(Optional.empty(), "absent"),
                new HasDescription("present <123>")));
    }


    @Test
    void testWithMatcher()
    {
        assertThat(new Present<>(new EqualTo<>(123)),
            new AllOf<>(
                new Passes<>(Optional.of(123)),
                new Fails<>(Optional.of(1234), "present <1234>"),
                new Fails<Optional<Integer>>(Optional.empty(), "absent"),
                new HasDescription("present <123>")));
    }

}
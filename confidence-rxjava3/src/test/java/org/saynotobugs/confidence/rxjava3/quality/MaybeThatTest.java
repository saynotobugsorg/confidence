package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.Maybe;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Completes;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Emits;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;

import static org.saynotobugs.confidence.Assertion.assertThat;


class MaybeThatTest
{
    @Test
    void testNonEmpty()
    {
        assertThat(new MaybeThat<>(new Emits<>(new EqualTo<>(123))),
            new AllOf<>(
                new Passes<>(ignored -> Maybe.just(123)),
                new Fails<>(ignored -> Maybe.error(IOException::new), "Maybe that (0) emitted <0> items that iterated [ 0: missing <123> ]"),
                new Fails<>(ignored -> Maybe.empty(), "Maybe that (0) emitted <0> items that iterated [ 0: missing <123> ]"),
                new Fails<>(ignored -> Maybe.just(124), "Maybe that (0) emitted <1> items that iterated [ 0: <124> ]"),
                new HasDescription("Maybe that (0) emits <1> items that iterates [ 0: <123> ]")
            ));
    }


    @Test
    void testEmpty()
    {
        assertThat(new MaybeThat<>(new Completes<>()),
            new AllOf<>(
                new Passes<>(ignored -> Maybe.empty()),
                new Fails<>(ignored -> Maybe.error(IOException::new), "Maybe that (0) { completed <0> times\n  ... }"),
                new Fails<>(ignored -> Maybe.just(124), "Maybe that (0) { ...\n  emitted [ <124> ] }"),
                new HasDescription("Maybe that (0) { completes exactly once\n    and\n    emits nothing }")
            ));
    }

}
package org.saynotobugs.confidence.rxjava3.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Completes;
import org.saynotobugs.confidence.rxjava3.rxexpectation.IsAlive;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;

import io.reactivex.rxjava3.core.Completable;

import static org.saynotobugs.confidence.Assertion.assertThat;


class CompletableThatTest
{
    @Test
    void testComplete()
    {
        assertThat(new CompletableThat<>(new Completes<>()),
            new AllOf<>(
                new Passes<>(ignored -> Completable.complete()),
                new Fails<>(ignored -> Completable.error(IOException::new), "Completable that (0) { completed <0> times\n  ... }"),
                new Fails<>(ignored -> Completable.never(), "Completable that (0) { completed <0> times\n  ... }"),
                new HasDescription("Completable that (0) completes exactly once\n    and\n    emits nothing")
            ));
    }

    @Test
    void testNever()
    {
        assertThat(new CompletableThat<>(new IsAlive<>()),
            new AllOf<>(
                new Passes<>(ignored -> Completable.never()),
                new Fails<>(ignored -> Completable.error(IOException::new), "Completable that (0) was has error contains { <anything> }\n  ..."),
                new Fails<>(ignored -> Completable.complete(), "Completable that (0) was ...\n  completes exactly once\n  ..."),
                new HasDescription("Completable that (0) alive")
            ));
    }

}
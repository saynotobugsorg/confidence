package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.Completable;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Completes;
import org.saynotobugs.confidence.rxjava3.rxexpectation.IsAlive;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;

import static org.saynotobugs.confidence.Assertion.assertThat;


class CompletableThatTest
{
    @Test
    void testComplete()
    {
        assertThat(new CompletableThat<>(new Completes<>()),
            new AllOf<>(
                new Passes<>(ignored -> Completable.complete()),
                new Fails<>(ignored -> Completable.error(IOException::new), "Completable that had errors [ <java.io.IOException> ]"),
                new Fails<>(ignored -> Completable.never(), "Completable that completed 0 times"),
                new HasDescription("Completable that completes exactly once")
            ));
    }


    @Test
    void testNever()
    {
        assertThat(new CompletableThat<>(new IsAlive<>()),
            new AllOf<>(
                new Passes<>(ignored -> Completable.never()),
                new Fails<>(ignored -> Completable.error(IOException::new), "Completable that had errors [ <java.io.IOException> ]"),
                // TODO fix descrption test when "not" was fixed
                new Fails<>(ignored -> Completable.complete(), new Anything()),
                new HasDescription("Completable that is alive")
            ));
    }

}
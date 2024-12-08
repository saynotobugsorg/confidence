package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.Completable;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Completes;
import org.saynotobugs.confidence.rxjava3.rxexpectation.IsAlive;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Within;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.saynotobugs.confidence.Assertion.assertThat;


class CompletableThatTest
{
    @Test
    void testComplete()
    {
        assertThat(new CompletableThat<>(new Completes<>()),
            new AllOf<>(
                new Passes<>(ignored -> Completable.complete(), "Completable that completes exactly once"),
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
                new Passes<>(ignored -> Completable.never(), "Completable that is alive"),
                new Fails<>(ignored -> Completable.error(IOException::new), "Completable that had errors [ <java.io.IOException> ]"),
                new Fails<>(ignored -> Completable.complete(), "Completable that completes exactly once"),
                new HasDescription("Completable that is alive")
            ));
    }


    @Test
    void testMultiple()
    {
        assertThat(new CompletableThat<>(new Within<>(Duration.ofMillis(1000), new IsAlive<>()),
                new Within<>(Duration.ofMillis(1), new Completes<>())),
            new AllOf<>(
                new Passes<>(scheduler -> Completable.complete().delay(1001, TimeUnit.MILLISECONDS, scheduler),
                    "Completable that all of\n" +
                        "  0: after PT1S is alive\n" +
                        "  1: after PT0.001S completes exactly once"),
                new Fails<>(scheduler -> Completable.error(IOException::new), "Completable that all of\n  0: after PT1S had errors [ <java.io.IOException> ]"),
                new Fails<>(scheduler -> Completable.complete(), "Completable that all of\n  0: after PT1S completes exactly once"),
                new Fails<>(scheduler -> Completable.never(), "Completable that all of\n  ...\n  1: after PT0.001S completed 0 times"),
                new HasDescription("Completable that all of\n  0: after PT1S is alive\n  1: after PT0.001S completes exactly once")
            ));
    }

}
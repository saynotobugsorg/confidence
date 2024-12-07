package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import org.dmfs.jems2.Function;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Emits;
import org.saynotobugs.confidence.rxjava3.rxexpectation.EmitsNothing;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Errors;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Within;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.saynotobugs.confidence.Assertion.assertThat;


class SingleThatTest
{
    @Test
    void testEmits()
    {
        assertThat(new SingleThat<>(new Emits<>(new EqualTo<>(123))),
            new AllOf<>(
                new Passes<>(ignored -> Single.just(123), "emits 1 items 123"),
                new Fails<>(ignored -> Single.error(IOException::new), "Single that had errors [ <java.io.IOException> ]"),
                new Fails<>(ignored -> Single.just(124), "Single that emitted 1 items 124"),
                new HasDescription("Single that emits 1 items 123")
            ));
    }

    @Test
    void testErrors()
    {
        assertThat(new SingleThat<>(new Errors<>(IOException.class)),
            new AllOf<>(
                new Passes<>(ignored -> Single.error(new IOException()), "had errors that iterated [\n" +
                    "  0: instance of <class java.io.IOException>\n" +
                    "]"),
                new Fails<>(ignored -> Single.error(NoSuchElementException::new), "Single that had errors that iterated [\n  0: instance of <class java.util.NoSuchElementException>\n]"),
                new Fails<>(ignored -> Single.just(124), "Single that had errors that iterated [\n  0: missing instance of <class java.io.IOException>\n]"),
                new HasDescription("Single that has errors that iterates [\n  0: instance of <class java.io.IOException>\n]")
            ));
    }

    @Test
    void testMultiple()
    {
        assertThat(new SingleThat<Integer>(new Within(Duration.ofMillis(100), new EmitsNothing()),
                new Within<>(Duration.ofMillis(100), new Emits<>(123))),
            new AllOf<>(
                new Passes<>((Function<Scheduler, Single<Integer>>) scheduler -> Single.just(123).delay(200, TimeUnit.MILLISECONDS, scheduler),
                    "all of\n" +
                        "  0: emits nothing\n" +
                        "  1: emits 1 items 123"),
                new Fails<>((Function<Scheduler, Single<Integer>>) scheduler -> Single.error(NoSuchElementException::new),
                    "Single that all of\n  ...\n  1: after PT0.1S had errors [ <java.util.NoSuchElementException> ]"),
                new Fails<>((Function<Scheduler, Single<Integer>>) scheduler -> Single.just(124),
                    "Single that all of\n  0: after PT0.1S emitted [ 124 ]"),
                new HasDescription("Single that all of\n  0: after PT0.1S emits nothing\n  1: after PT0.1S emits 1 items 123")
            ));
    }
}
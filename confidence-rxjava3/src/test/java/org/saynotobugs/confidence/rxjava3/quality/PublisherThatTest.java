package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Emits;
import org.saynotobugs.confidence.rxjava3.rxexpectation.InExactly;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Within;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.saynotobugs.confidence.Assertion.assertThat;


class PublisherThatTest
{
    @Test
    void testMatch()
    {
        assertThat(new PublisherThat<>(new Within<>(Duration.ofSeconds(2), new Emits<>(1, 2, 3))),
            new AllOf<>(
                new Passes<>(scheduler -> Flowable.just(1, 2, 3).delay(2, TimeUnit.SECONDS, scheduler),
                    "all of\n" +
                        "  0: emits 3 items iterates [\n" +
                        "    0: 1\n" +
                        "    1: 2\n" +
                        "    2: 3\n" +
                        "  ]"),
                new Fails<>(scheduler -> Flowable.just(1, 2, 3).delay(30, TimeUnit.SECONDS, scheduler),
                    "Publisher that all of\n  0: after PT2S emitted 0 items iterated [\n    0: missing 1\n    1: missing 2\n    2: missing 3\n  ]"),
                new HasDescription("Publisher that all of\n  0: after PT2S emits 3 items iterates [\n    0: 1\n    1: 2\n    2: 3\n  ]")
            ));
    }

    @Test
    void testMatchInExactly()
    {
        assertThat(new PublisherThat<>(new InExactly<>(Duration.ofSeconds(2), new Emits<>(1, 2, 3))),
            new AllOf<>(
                new Passes<>(scheduler -> Flowable.just(1, 2, 3).delay(2, TimeUnit.SECONDS, scheduler),
                    "all of\n" +
                        "  0: in exactly PT2S emits 3 items iterates [\n" +
                        "    0: 1\n" +
                        "    1: 2\n" +
                        "    2: 3\n" +
                        "  ]"),
                new Fails<>(scheduler -> Flowable.just(1, 2, 3).delay(1, TimeUnit.SECONDS, scheduler),
                    "Publisher that all of\n  0: in less than PT2S emitted [ 1, 2, 3 ]"),
                new Fails<>(scheduler -> Flowable.<Integer>empty().delay(1, TimeUnit.SECONDS, scheduler), "Publisher that all of\n  0: immediately completes exactly once"),
                new Fails<>(scheduler -> Flowable.just(1, 2, 3).delay(30, TimeUnit.SECONDS, scheduler),
                    "Publisher that all of\n  0: in exactly PT2S emitted 0 items iterated [\n    0: missing 1\n    1: missing 2\n    2: missing 3\n  ]"),
                new HasDescription("Publisher that all of\n  0: in exactly PT2S emits 3 items iterates [\n    0: 1\n    1: 2\n    2: 3\n  ]")
            ));
    }
}
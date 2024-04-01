package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Emits;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Within;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ObservableThatTest
{
    @Test
    void testMatch()
    {
        assertThat(new ObservableThat<>(new Within<>(Duration.ofSeconds(2), new Emits<>(1, 2, 3))),
            new AllOf<>(
                new Passes<>(scheduler -> Observable.just(1, 2, 3).delay(2, TimeUnit.SECONDS, scheduler)),
                new Fails<>(scheduler -> Observable.just(1, 2, 3).delay(30, TimeUnit.SECONDS, scheduler),
                    "Observable that (0) after PT2S emitted <0> items that iterated [ 0: missing <1>,\n  1: missing <2>,\n  2: missing <3> ]"),
                new HasDescription("Observable that (0) after PT2S emits <3> items that iterates [ 0: <1>,\n    1: <2>,\n    2: <3> ]")
            ));
    }
}

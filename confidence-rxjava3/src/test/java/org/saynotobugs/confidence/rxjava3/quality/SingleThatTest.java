package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Emits;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;

import static org.saynotobugs.confidence.Assertion.assertThat;


class SingleThatTest
{
    @Test
    void testEmits()
    {
        assertThat(new SingleThat<>(new Emits<>(new EqualTo<>(123))),
            new AllOf<>(
                new Passes<>(ignored -> Single.just(123)),
                new Fails<>(ignored -> Single.error(IOException::new), "Single that had errors [ <java.io.IOException> ]"),
                new Fails<>(ignored -> Single.just(124), "Single that emitted 1 items 124"),
                new HasDescription("Single that emits 1 items 123")
            ));
    }
}
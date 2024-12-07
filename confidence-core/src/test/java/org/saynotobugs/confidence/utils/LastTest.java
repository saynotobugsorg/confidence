package org.saynotobugs.confidence.utils;

import org.dmfs.jems2.Optional;
import org.dmfs.jems2.iterable.EmptyIterable;
import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.Not;
import org.saynotobugs.confidence.quality.object.Satisfies;

import static org.saynotobugs.confidence.Assertion.assertThat;

class LastTest
{
    @Test
    void testEmpty()
    {
        assertThat(new Last<>(new EmptyIterable<>()),
            new Not<>(new Satisfies<>(Optional::isPresent)));
    }

    @Test
    void testSingleElement()
    {
        assertThat(new Last<>(new Seq<>("a")),
            new AllOf<>(
                new Satisfies<>(Optional::isPresent),
                new Has<>(Optional::value, "a")));
    }


    @Test
    void testSeveralElementa()
    {
        assertThat(new Last<>(new Seq<>("a", "b", "c")),
            new AllOf<>(
                new Satisfies<>(Optional::isPresent),
                new Has<>(Optional::value, "c")));
    }

}
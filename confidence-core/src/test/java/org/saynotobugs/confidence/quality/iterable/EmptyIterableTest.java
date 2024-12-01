package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static java.util.Collections.emptyList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class EmptyIterableTest
{

    @Test
    void test()
    {
        assertThat(new EmptyIterable(),
            new AllOf<>(
                new Passes<Iterable<Integer>>(emptyList(), "[]"),
                new Fails<>(new Seq<>(1, 2, 3), "[ 1, 2, 3 ]"),
                new HasDescription("[]")
            ));
    }
}
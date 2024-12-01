package org.saynotobugs.confidence.quality.function;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.grammar.To;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ResultOfTest
{
    @Test
    void test()
    {
        assertThat(new ResultOf<>(1, new Maps<>(30, new To<>(34))),
            new AllOf<>(
                // 1 + 3 + 30
                new Passes<>(delegate -> x -> delegate.apply(x + 100) + 3 + x, "mapped 30 to 34"),
                // 34
                new Passes<>(delegate -> x -> 34, "mapped 30 to 34"),
                // 1 + 33
                new Passes<>(delegate -> x -> delegate.apply(-100) + 33, "mapped 30 to 34"),
                new Fails<>(
                    delegate -> x -> delegate.apply(100),
                    "delegate which outputs 1 resulted in outer function mapped 30 to 1"
                ),
                new HasDescription("delegate which outputs 1 resulting in outer function that maps 30 to 34")
            )
        );
    }
}

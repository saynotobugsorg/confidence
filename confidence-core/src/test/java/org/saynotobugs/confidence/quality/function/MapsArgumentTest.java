package org.saynotobugs.confidence.quality.function;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class MapsArgumentTest
{
    @Test
    void test()
    {
        assertThat(new MapsArgument<Integer, Integer, Integer>(1, new EqualTo<>(2)),
            new AllOf<>(
                new Passes<>(
                    delegate -> x -> delegate.apply(x * 2),
                    delegate -> x -> delegate.apply(2) + 1
                ),
                new Fails<>(
                    delegate -> x -> delegate.apply(x * 10),
                    "function passed <10> to delegate function for outer argument <1>"
                ),
                new Fails<>(
                    delegate -> x -> 0,
                    "function did not call delegate"
                ),
                new HasDescription("function passing <2> to delegate function for outer argument <1>")
            )
        );
    }
}

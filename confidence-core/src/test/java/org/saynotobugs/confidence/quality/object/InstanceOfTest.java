package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.That;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class InstanceOfTest
{
    @Test
    void testNoDelegate()
    {
        assertThat(new InstanceOf<>(Number.class),
            new AllOf<>(
                new Passes<>(1, 1L, 1f),
                new Fails<>("string", "instance of <class java.lang.String>"),
                new Fails<>(new Object(), "instance of <class java.lang.Object>"),
                new HasDescription("instance of <class java.lang.Number>")
            ));
    }

    @Test
    void testDelegate()
    {
        assertThat(new InstanceOf<>(Number.class, new That<>(new Has<>("intValue", Number::intValue, new EqualTo<>(1)))),
            new AllOf<>(
                new Passes<>(1, 1.001, 1L, 1f),
                new Fails<>(0.999, "all of\n  ...\n  1: that had intValue 0"),
                new Fails<>(2, "all of\n  ...\n  1: that had intValue 2"),
                new Fails<>("string", "all of\n  0: instance of <class java.lang.String>"),
                new Fails<>(new Object(), "all of\n  0: instance of <class java.lang.Object>"),
                new HasDescription("all of\n  0: instance of <class java.lang.Number>\n  1: that has intValue 1")
            ));
    }
}
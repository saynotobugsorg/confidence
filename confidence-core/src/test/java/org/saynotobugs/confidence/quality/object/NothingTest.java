package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;

import static org.saynotobugs.confidence.Assertion.assertThat;


class NothingTest
{

    @Test
    void test()
    {
        assertThat(new Nothing(),
            new AllOf<>(
                new Fails<Object>("abc", "\"abc\""),
                new Fails<Object>(123, "123"),
                new Fails<Object>(null, "<null>"),
                new HasDescription("<nothing>")));
    }

}
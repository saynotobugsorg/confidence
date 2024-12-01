package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.Nothing;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class NotTest
{

    @Test
    void test()
    {
        assertThat(new Not<>("123"),
            new AllOf<>(
                new Fails<>("123", "\"123\""),
                new HasDescription("not \"123\"")));

        assertThat(new Not<>(new Anything()),
            new AllOf<>(
                new Fails<>("123", "\"123\""),
                new HasDescription("not <anything>")));

        assertThat(new Not<>(new Nothing()),
            new AllOf<>(
                new Passes<>("123", "\"123\""),
                new HasDescription("not <nothing>")));
    }

}
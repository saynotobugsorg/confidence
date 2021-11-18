package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class DescriptionDescriptionTest
{

    @Test
    void test()
    {
        assertThat(
            new DescriptionDescription(new TextDescription("abc")),
            new DescribesAs("\n  ----\n  abc\n  ----"));
    }

}
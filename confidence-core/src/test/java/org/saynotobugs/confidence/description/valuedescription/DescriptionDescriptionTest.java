package org.saynotobugs.confidence.description.valuedescription;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class DescriptionDescriptionTest
{

    @Test
    void test()
    {
        assertThat(
            new DescriptionDescription(new Text("abc")),
            new DescribesAs("\n  ----\n  abc\n  ----"));
    }

}
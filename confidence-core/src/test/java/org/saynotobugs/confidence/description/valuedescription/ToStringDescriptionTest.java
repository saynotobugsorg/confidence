package org.saynotobugs.confidence.description.valuedescription;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ToStringDescriptionTest
{

    @Test
    void testNonEmpty()
    {
        assertThat(new ToStringDescription(new Object()
            {
                @Override
                public String toString()
                {
                    return "test to String";
                }
            }),
            new DescribesAs("<test to String>"));
    }

}
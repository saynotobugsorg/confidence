package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class NumberDescriptionTest
{

    @Test
    void testInt()
    {
        assertThat(new NumberDescription(123), new DescribesAs("<123>"));
    }


    @Test
    void testLong()
    {
        assertThat(new NumberDescription(123l), new DescribesAs("<123l>"));
    }


    @Test
    void testFloat()
    {
        assertThat(new NumberDescription(123.23f), new DescribesAs("<123.23f>"));
    }


    @Test
    void testDouble()
    {
        assertThat(new NumberDescription(123.23d), new DescribesAs("<123.23d>"));
    }
}
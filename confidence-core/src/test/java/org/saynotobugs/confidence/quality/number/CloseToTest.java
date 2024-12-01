package org.saynotobugs.confidence.quality.number;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class CloseToTest
{

    @Test
    void testNumber()
    {
        assertThat(new CloseTo(1.0, 0.01),
            new AllOf<>(
                new Passes<Number>(1.0, "differs from 1.0 by less than 0.01"),
                new Passes<>(1f, "differs from 1.0 by less than 0.01"),
                new Passes<>(1, "differs from 1.0 by less than 0.01"),
                new Passes<>(1.00999, "differs from 1.0 by less than 0.01"),
                new Passes<>(0.99000001, "differs from 1.0 by less than 0.01"),
                new Fails<>(1.01001d, "1.01001d differs from 1.0 by 0.01001, which exceeds the ε of 0.01"),
                new HasDescription("differs from 1.0 by less than 0.01")
            ));
    }


    @Test
    void testDouble1ulp()
    {
        assertThat(new CloseTo(1.0),
            new AllOf<>(
                new Passes<>(1.0000000000000001, ""),
                new Passes<>(0.9999999999999999, ""),
                new Fails<>(1.01001d, "1.01001d differs from 1.0 by 0.01001, which exceeds the ε of 2.220446049250313E-16"),
                new HasDescription("differs from 1.0 by less than 2.220446049250313E-16")
            ));
    }


    @Test
    void testDouble10ulp()
    {
        assertThat(new CloseTo(1.0, 10),
            new AllOf<>(
                new Passes<>(1.000000000000001, ""),
                new Passes<>(0.999999999999999, ""),
                new Fails<>(1.01001d, "1.01001d differs from 1.0 by 0.01001, which exceeds the ε of 2.220446049250313E-15"),
                new HasDescription("differs from 1.0 by less than 2.220446049250313E-15")
            ));
    }

    @Test
    void testFloat1ulp()
    {
        assertThat(new CloseTo(1.0f),
            new AllOf<>(
                new Passes<>(1.0000001, ""),
                new Passes<>(0.9999999, ""),
                new Fails<>(1.01001d, "1.01001d differs from 1.0 by 0.01001, which exceeds the ε of 1.1920929E-7"),
                new HasDescription("differs from 1.0 by less than 1.1920929E-7")
            ));
    }


    @Test
    void testFloat10ulp()
    {
        assertThat(new CloseTo(1.0f, 10),
            new AllOf<>(
                new Passes<>(1.000001, ""),
                new Passes<>(0.999999, ""),
                new Fails<>(1.01001d, "1.01001d differs from 1.0 by 0.01001, which exceeds the ε of 0.0000011920929"),
                new HasDescription("differs from 1.0 by less than 0.0000011920929")
            ));
    }
}
/*
 * Copyright 2024 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
                new Passes<Number>(1.0, "1.0d differed from 1.0 by 0.0, which was less than 0.01"),
                new Passes<>(1f, "1.0f differed from 1.0 by 0.0, which was less than 0.01"),
                new Passes<>(1, "1 differed from 1.0 by 0.0, which was less than 0.01"),
                new Passes<>(1.00999, "1.00999d differed from 1.0 by 0.00999, which was less than 0.01"),
                new Passes<>(0.99000001, "0.99000001d differed from 1.0 by 0.00999999, which was less than 0.01"),
                new Fails<>(1.01001d, "1.01001d differed from 1.0 by 0.01001, which exceeded the ε of 0.01"),
                new HasDescription("differs from 1.0 by less than 0.01")
            ));
    }


    @Test
    void testDouble1ulp()
    {
        assertThat(new CloseTo(1.0),
            new AllOf<>(
                new Passes<>(1.0000000000000001, "1.0d differed from 1.0 by 0.0, which was less than 2.220446049250313E-16"),
                new Passes<>(0.9999999999999999, "0.9999999999999999d differed from 1.0 by 1E-16, which was less than 2.220446049250313E-16"),
                new Fails<>(1.01001d, "1.01001d differed from 1.0 by 0.01001, which exceeded the ε of 2.220446049250313E-16"),
                new HasDescription("differs from 1.0 by less than 2.220446049250313E-16")
            ));
    }


    @Test
    void testDouble10ulp()
    {
        assertThat(new CloseTo(1.0, 10),
            new AllOf<>(
                new Passes<>(1.000000000000001, "1.000000000000001d differed from 1.0 by 1E-15, which was less than 2.220446049250313E-15"),
                new Passes<>(0.999999999999999, "0.999999999999999d differed from 1.0 by 1E-15, which was less than 2.220446049250313E-15"),
                new Fails<>(1.01001d, "1.01001d differed from 1.0 by 0.01001, which exceeded the ε of 2.220446049250313E-15"),
                new HasDescription("differs from 1.0 by less than 2.220446049250313E-15")
            ));
    }

    @Test
    void testFloat1ulp()
    {
        assertThat(new CloseTo(1.0f),
            new AllOf<>(
                new Passes<>(1.0000001, "1.0000001d differed from 1.0 by 1E-7, which was less than 1.1920929E-7"),
                new Passes<>(0.9999999, "0.9999999d differed from 1.0 by 1E-7, which was less than 1.1920929E-7"),
                new Fails<>(1.01001d, "1.01001d differed from 1.0 by 0.01001, which exceeded the ε of 1.1920929E-7"),
                new HasDescription("differs from 1.0 by less than 1.1920929E-7")
            ));
    }


    @Test
    void testFloat10ulp()
    {
        assertThat(new CloseTo(1.0f, 10),
            new AllOf<>(
                new Passes<>(1.000001, "1.000001d differed from 1.0 by 0.000001, which was less than 0.0000011920929"),
                new Passes<>(0.999999, "0.999999d differed from 1.0 by 0.000001, which was less than 0.0000011920929"),
                new Fails<>(1.01001d, "1.01001d differed from 1.0 by 0.01001, which exceeded the ε of 0.0000011920929"),
                new HasDescription("differs from 1.0 by less than 0.0000011920929")
            ));
    }
}
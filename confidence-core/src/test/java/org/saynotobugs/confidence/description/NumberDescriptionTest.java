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

package org.saynotobugs.confidence.description;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;


class NumberDescriptionTest
{

    @Test
    void testInt()
    {
        assertThat(new NumberDescription(123), new DescribesAs("123"));
    }


    @Test
    void testLong()
    {
        assertThat(new NumberDescription(123l), new DescribesAs("123l"));
    }


    @Test
    void testFloat()
    {
        assertThat(new NumberDescription(123.23f), new DescribesAs("123.23f"));
    }


    @Test
    void testDouble()
    {
        assertThat(new NumberDescription(123.23d), new DescribesAs("123.23d"));
    }
}
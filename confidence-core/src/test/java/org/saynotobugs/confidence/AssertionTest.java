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

package org.saynotobugs.confidence;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.Nothing;
import org.saynotobugs.confidence.quality.object.Throwing;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AssertionTest
{

    @Test
    void test()
    {
        assertThat(() -> assertThat("123", new Nothing()),
            new Is<>(new Throwing(AssertionError.class)));
    }

}
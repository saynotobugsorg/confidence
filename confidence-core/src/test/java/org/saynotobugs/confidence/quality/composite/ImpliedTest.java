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

package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.charsequence.HasLength;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ImpliedTest
{
    @Test
    void test()
    {
        assertThat(new Implied<>(new HasLength(3), new DescribedAs<String>(
                new TextAndOriginal<>("was equal to"),
                new TextAndOriginal<>("was not equal to"),
                new TextAndOriginal<>("is equal to"),
                new EqualTo<>("abc"))),
            new AllOf<>(
                new Passes<>("abc", "was equal to \"abc\""),
                new Fails<>("xyz", "was not equal to \"xyz\""),
                new Fails<>("abcd", "\"abcd\" had length 4"),
                new HasDescription("is equal to \"abc\"")
            )
        );
    }

}
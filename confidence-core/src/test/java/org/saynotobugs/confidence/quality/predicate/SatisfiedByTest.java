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

package org.saynotobugs.confidence.quality.predicate;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class SatisfiedByTest
{

    @Test
    void testMatch()
    {
        assertThat(new SatisfiedBy<>("12"),
            new AllOf<>(
                new Passes<>("12"::equals, "satisfied by \"12\""),
                new Passes<>(s -> s.length() == 2, "satisfied by \"12\""),
                new Fails<>("123"::equals, "not satisfied by \"12\""),
                new HasDescription("satisfied by \"12\"")));
    }

}
/*
 * Copyright 2022 dmfs GmbH
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.saynotobugs.confidence.quality.grammar;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class SoItTest
{

    @Test
    void test()
    {
        assertThat(new SoIt<>(new Contains<>("123")),
            new AllOf<>(
                new Passes<>(singletonList("123"), asList("1", "2", "123", "4")),
                new Fails<>(asList("1", "2", "4"), "but [ \"1\",\n  \"2\",\n  \"4\" ] did not contain { \"123\" }"),
                new HasDescription("so it contains { \"123\" }")
            ));
    }

}
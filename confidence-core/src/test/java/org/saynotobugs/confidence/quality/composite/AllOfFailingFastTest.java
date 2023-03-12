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

package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


class AllOfFailingFastTest
{

    @Test
    void testVarArgs()
    {
        assertThat(new AllOfFailingFast<>(new LessThan<>(10), new LessThan<>(5), new LessThan<>(3)),
            new AllOf<>(
                new Passes<>(0, 1, 2),
                new Fails<>(3, "(2) <3>"),
                new Fails<>(5, "(1) <5>"),
                new Fails<>(10, "(0) <10>"),
                new HasDescription("(0) less than <10>\n  and\n  (1) less than <5>\n  and\n  (2) less than <3>")
            ));
    }


    @Test
    void testVarArgsWithDelimiter()
    {
        assertThat(new AllOfFailingFast<>(NEW_LINE, new LessThan<>(10), new LessThan<>(5), new LessThan<>(3)),
            new AllOf<>(
                new Passes<>(0, 1, 2),
                new Fails<>(3, "(2) <3>"),
                new Fails<>(5, "(1) <5>"),
                new Fails<>(10, "(0) <10>"),
                new HasDescription("(0) less than <10>\n  (1) less than <5>\n  (2) less than <3>")
            ));
    }

}
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
import static org.saynotobugs.confidence.description.LiteralDescription.*;


class LiteralDescriptionTest
{
    @Test
    void test()
    {
        assertThat(NEW_LINE, new DescribesAs("\n"));
        assertThat(COMMA, new DescribesAs(","));
        assertThat(COMMA_NEW_LINE, new DescribesAs(",\n"));
        assertThat(DQUOTES, new DescribesAs("\""));
        assertThat(EMPTY, new DescribesAs(""));
        assertThat(NULL, new DescribesAs("<null>"));
        assertThat(SPACE, new DescribesAs(" "));
    }
}
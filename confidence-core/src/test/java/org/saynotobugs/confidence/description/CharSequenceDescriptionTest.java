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


class CharSequenceDescriptionTest
{
    @Test
    void testEmpty()
    {
        assertThat(new CharSequenceDescription(""), new DescribesAs("\"\""));
    }


    @Test
    void testNonEmpty()
    {
        assertThat(new CharSequenceDescription("abc"), new DescribesAs("\"abc\""));
    }


    @Test
    void testEscaped()
    {
        assertThat(new CharSequenceDescription("a \"b\" c"), new DescribesAs("\"a \\\"b\\\" c\""));
        assertThat(new CharSequenceDescription("a \t c"), new DescribesAs("\"a \\t c\""));
        assertThat(new CharSequenceDescription("a \n c"), new DescribesAs("\"a \\n c\""));
        assertThat(new CharSequenceDescription("a \r c"), new DescribesAs("\"a \\r c\""));
        assertThat(new CharSequenceDescription("a \\ c"), new DescribesAs("\"a \\\\ c\""));
        assertThat(new CharSequenceDescription("a \b c"), new DescribesAs("\"a \\b c\""));
        assertThat(new CharSequenceDescription("a \f c"), new DescribesAs("\"a \\f c\""));
    }
}
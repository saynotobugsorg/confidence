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

package org.saynotobugs.confidence.test.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;

import java.util.regex.Pattern;

import static org.saynotobugs.confidence.Assertion.assertThat;


class DescribesAsTest
{

    @Test
    void test()
    {
        assertThat(new DescribesAs("123"),
            new AllOf<>(
                new Passes<>(scribe -> scribe.append("123"),
                    "described as\n" +
                        "  ----\n" +
                        "  \"123\"\n" +
                        "  ----"),
                new Fails<>(scribe -> scribe.append("abc"), "described as\n  ----\n  \"abc\"\n  ----"),
                new HasDescription("describes as\n  ----\n  \"123\"\n  ----")
            ));
    }


    @Test
    void testPattern()
    {
        assertThat(new DescribesAs(Pattern.compile("\\w123\\w")),
            new AllOf<>(
                new Passes<>(scribe -> scribe.append("a123b"),
                    "described as\n" +
                        "  ----\n" +
                        "  \"a123b\" matched pattern /\\\\w123\\\\w/\n" +
                        "  ----"),
                new Fails<>(scribe -> scribe.append("ab123"), "described as\n  ----\n  \"ab123\" mismatched pattern /\\\\w123\\\\w/\n  ----"),
                new HasDescription("describes as\n  ----\n  matches pattern /\\\\w123\\\\w/\n  ----")
            ));
    }

}
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

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;

class EnclosedTest
{

    @Test
    void testEmptyStringPrefixAndSuffix()
    {
        assertThat(new Enclosed("(", EMPTY, ")"),
            new DescribesAs("()"));
    }

    @Test
    void testEmptyStringPrefixAndSuffixWithEmptyFallback()
    {
        assertThat(new Enclosed("(", EMPTY, ")", "ø"),
            new DescribesAs("ø"));
    }

    @Test
    void testNonEmptyStringPrefixAndSuffix()
    {
        assertThat(new Enclosed("(", new Text("foo"), ")"),
            new DescribesAs("(foo)"));
    }


    @Test
    void testNonEmptyStringPrefixAndSuffixWithEmptyFallback()
    {
        assertThat(new Enclosed("(", new Text("foo"), ")", "ø"),
            new DescribesAs("(foo)"));
    }


    @Test
    void testComplexStringPrefixAndSuffix()
    {
        assertThat(new Enclosed("(",
                new Indented(new Delimited(NEW_LINE, new Seq<>(new Text("line1"), new Text("line2")))),
                ")"),
            new DescribesAs("(line1\n  line2)"));
    }

    @Test
    void testEmptyIndentedStringPrefixAndSuffix()
    {
        assertThat(new Enclosed("(", new Indented(EMPTY), ")"),
            new DescribesAs("()"));
    }

    @Test
    void testComplexStringPrefixAndSuffixWithEmpty()
    {
        assertThat(new Enclosed("(",
                new Indented(new Delimited(NEW_LINE, new Seq<>(new Text("line1"), new Text("line2")))),
                ")", "--"),
            new DescribesAs("(line1\n  line2)"));
    }

    @Test
    void testEmptyIndentedStringPrefixAndSuffixWithEmpty()
    {
        assertThat(new Enclosed("(", new Indented(EMPTY), ")", "--"),
            new DescribesAs("--"));
    }

}
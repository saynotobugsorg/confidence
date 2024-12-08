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

package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ContainsNoneOfTest
{
    @Test
    void testLiteral()
    {
        assertThat(new ContainsNoneOf<>("a", "b", "c"),
            new AllOf<>(
                new Passes<>(new Seq<String>(), "elements []"),
                new Passes<>(new Seq<>("1", "2", "3"), "elements [\n" +
                    "  0: was\n" +
                    "    \"1\"\n" +
                    "    \"1\"\n" +
                    "    \"1\"\n" +
                    "  1: was\n" +
                    "    \"2\"\n" +
                    "    \"2\"\n" +
                    "    \"2\"\n" +
                    "  2: was\n" +
                    "    \"3\"\n" +
                    "    \"3\"\n" +
                    "    \"3\"\n" +
                    "]"),
                new Fails<>(new Seq<>("1", "a", "3"),
                    "elements [\n  ...\n  1: was\n    \"a\"\n    ...\n  ...\n]"),
                new Fails<>(new Seq<>("1", "a", "c", "5"),
                    "elements [\n  ...\n  1: was\n    \"a\"\n    ...\n  2: was\n    ...\n    \"c\"\n  ...\n]"),
                new Fails<>(new Seq<>("c", "b", "a"),
                    "elements [\n  0: was\n    ...\n    \"c\"\n  1: was\n    ...\n    \"b\"\n    ...\n  2: was\n    \"a\"\n    ...\n]"),
                new Fails<>(new Seq<>("a", "a", "a"),
                    "elements [\n  0: was\n    \"a\"\n    ...\n  1: was\n    \"a\"\n    ...\n  2: was\n    \"a\"\n    ...\n]"),
                new Fails<>(new Seq<>("a", "a", "a", "b", "b", "b"),
                    "elements [\n  0: was\n    \"a\"\n    ...\n  1: was\n    \"a\"\n    ...\n  2: was\n    \"a\"\n    ...\n  3: was\n    ...\n    \"b\"\n    ...\n  4: was\n    ...\n    \"b\"\n    ...\n  5: was\n    ...\n    \"b\"\n    ...\n]"),
                new HasDescription("each element none of\n  \"a\"\n  \"b\"\n  \"c\"")
            ));
    }

    @Test
    void testQuality()
    {
        assertThat(new ContainsNoneOf<>(new MatchesPattern("[abc]"), new EqualTo<>("b")),
            new AllOf<>(
                new Passes<Iterable<String>>(new Seq<>(), "elements []"),
                new Passes<>(new Seq<>("1", "2", "3"), "elements [\n" +
                    "  0: was\n" +
                    "    \"1\" mismatched pattern /[abc]/\n" +
                    "    \"1\"\n" +
                    "  1: was\n" +
                    "    \"2\" mismatched pattern /[abc]/\n" +
                    "    \"2\"\n" +
                    "  2: was\n" +
                    "    \"3\" mismatched pattern /[abc]/\n" +
                    "    \"3\"\n" +
                    "]"),
                new Fails<>(new Seq<>("1", "a", "3"),
                    "elements [\n  ...\n  1: was\n    \"a\" matched pattern /[abc]/\n    ...\n  ...\n]"),
                new Fails<>(new Seq<>("1", "a", "c", "5"),
                    "elements [\n  ...\n  1: was\n    \"a\" matched pattern /[abc]/\n    ...\n  2: was\n    \"c\" matched pattern /[abc]/\n    ...\n  ...\n]"),
                new Fails<>(new Seq<>("c", "b", "a"),
                    "elements [\n  0: was\n    \"c\" matched pattern /[abc]/\n    ...\n  1: was\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  2: was\n    \"a\" matched pattern /[abc]/\n    ...\n]"),
                new Fails<>(new Seq<>("a", "a", "a"),
                    "elements [\n  0: was\n    \"a\" matched pattern /[abc]/\n    ...\n  1: was\n    \"a\" matched pattern /[abc]/\n    ...\n  2: was\n    \"a\" matched pattern /[abc]/\n    ...\n]"),
                new Fails<>(new Seq<>("a", "a", "a", "b", "b", "b"),
                    "elements [\n  0: was\n    \"a\" matched pattern /[abc]/\n    ...\n  1: was\n    \"a\" matched pattern /[abc]/\n    ...\n  2: was\n    \"a\" matched pattern /[abc]/\n    ...\n  3: was\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  4: was\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  5: was\n    \"b\" matched pattern /[abc]/\n    \"b\"\n]"),
                new HasDescription("each element none of\n  matches pattern /[abc]/\n  \"b\"")
            ));
    }

    @Test
    void testQualityIterable()
    {
        assertThat(new ContainsNoneOf<>(new Seq<>(new MatchesPattern("[abc]"), new EqualTo<>("b"))),
            new AllOf<>(
                new Passes<Iterable<String>>(new Seq<>(), "elements []"),
                new Passes<>(new Seq<>("1", "2", "3"), "elements [\n" +
                    "  0: was\n" +
                    "    \"1\" mismatched pattern /[abc]/\n" +
                    "    \"1\"\n" +
                    "  1: was\n" +
                    "    \"2\" mismatched pattern /[abc]/\n" +
                    "    \"2\"\n" +
                    "  2: was\n" +
                    "    \"3\" mismatched pattern /[abc]/\n" +
                    "    \"3\"\n" +
                    "]"),
                new Fails<>(new Seq<>("1", "a", "3"),
                    "elements [\n  ...\n  1: was\n    \"a\" matched pattern /[abc]/\n    ...\n  ...\n]"),
                new Fails<>(new Seq<>("1", "a", "c", "5"),
                    "elements [\n  ...\n  1: was\n    \"a\" matched pattern /[abc]/\n    ...\n  2: was\n    \"c\" matched pattern /[abc]/\n    ...\n  ...\n]"),
                new Fails<>(new Seq<>("c", "b", "a"),
                    "elements [\n  0: was\n    \"c\" matched pattern /[abc]/\n    ...\n  1: was\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  2: was\n    \"a\" matched pattern /[abc]/\n    ...\n]"),
                new Fails<>(new Seq<>("a", "a", "a"),
                    "elements [\n  0: was\n    \"a\" matched pattern /[abc]/\n    ...\n  1: was\n    \"a\" matched pattern /[abc]/\n    ...\n  2: was\n    \"a\" matched pattern /[abc]/\n    ...\n]"),
                new Fails<>(new Seq<>("a", "a", "a", "b", "b", "b"),
                    "elements [\n  0: was\n    \"a\" matched pattern /[abc]/\n    ...\n  1: was\n    \"a\" matched pattern /[abc]/\n    ...\n  2: was\n    \"a\" matched pattern /[abc]/\n    ...\n  3: was\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  4: was\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  5: was\n    \"b\" matched pattern /[abc]/\n    \"b\"\n]"),
                new HasDescription("each element none of\n  matches pattern /[abc]/\n  \"b\"")
            ));
    }
}
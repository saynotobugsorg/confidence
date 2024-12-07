/*
 * Copyright 2023 dmfs GmbH
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
                new Passes<>(new Seq<String>(), "contained none of { \"a\",\n  \"b\",\n  \"c\" }"),
                new Passes<>(new Seq<>("1", "2", "3"), "contained none of { \"a\",\n  \"b\",\n  \"c\" }"),
                new Fails<>(new Seq<>("1", "a", "3"),
                    "contained {\n  ...\n  1: \"a\"\n  ...\n}"),
                new Fails<>(new Seq<>("1", "a", "c", "5"),
                    "contained {\n  ...\n  1: \"a\"\n  2: \"c\"\n  ...\n}"),
                new Fails<>(new Seq<>("c", "b", "a"),
                    "contained {\n  0: \"c\"\n  1: \"b\"\n  2: \"a\"\n}"),
                new Fails<>(new Seq<>("a", "a", "a"),
                    "contained {\n  0: \"a\"\n  1: \"a\"\n  2: \"a\"\n}"),
                new Fails<>(new Seq<>("a", "a", "a", "b", "b", "b"),
                    "contained {\n  0: \"a\"\n  1: \"a\"\n  2: \"a\"\n  3: \"b\"\n  4: \"b\"\n  5: \"b\"\n}"),
                new HasDescription("contains none of { \"a\",\n  \"b\",\n  \"c\" }")
            ));
    }

    @Test
    void testQuality()
    {
        assertThat(new ContainsNoneOf<>(new MatchesPattern("[abc]"), new EqualTo<>("b")),
            new AllOf<>(
                new Passes<Iterable<String>>(new Seq<>(), ""),
                new Passes<>(new Seq<>("1", "2", "3"), ""),
                new Fails<>(new Seq<>("1", "a", "3"),
                    "contained {\n  ...\n  1: \"a\" {\n    matches pattern /[abc]/\n  }\n  ...\n}"),
                new Fails<>(new Seq<>("1", "a", "c", "5"),
                    "contained {\n  ...\n  1: \"a\" {\n    matches pattern /[abc]/\n  }\n  2: \"c\" {\n    matches pattern /[abc]/\n  }\n  ...\n}"),
                new Fails<>(new Seq<>("c", "b", "a"),
                    "contained {\n  0: \"c\" {\n    matches pattern /[abc]/\n  }\n  1: \"b\" {\n    matches pattern /[abc]/\n    \"b\"\n  }\n  2: \"a\" {\n    matches pattern /[abc]/\n  }\n}"),
                new Fails<>(new Seq<>("a", "a", "a"),
                    "contained {\n  0: \"a\" {\n    matches pattern /[abc]/\n  }\n  1: \"a\" {\n    matches pattern /[abc]/\n  }\n  2: \"a\" {\n    matches pattern /[abc]/\n  }\n}"),
                new Fails<>(new Seq<>("a", "a", "a", "b", "b", "b"),
                    "contained {\n  0: \"a\" {\n    matches pattern /[abc]/\n  }\n  1: \"a\" {\n    matches pattern /[abc]/\n  }\n  2: \"a\" {\n    matches pattern /[abc]/\n  }\n  3: \"b\" {\n    matches pattern /[abc]/\n    \"b\"\n  }\n  4: \"b\" {\n    matches pattern /[abc]/\n    \"b\"\n  }\n  5: \"b\" {\n    matches pattern /[abc]/\n    \"b\"\n  }\n}"),
                new HasDescription("contains none of { matches pattern /[abc]/,\n  \"b\" }")
            ));
    }

    @Test
    void testQualityIterable()
    {
        assertThat(new ContainsNoneOf<>(new Seq<>(new MatchesPattern("[abc]"), new EqualTo<>("b"))),
            new AllOf<>(
                new Passes<Iterable<String>>(new Seq<>(), ""),
                new Passes<>(new Seq<>("1", "2", "3"), ""),
                new Fails<>(new Seq<>("1", "a", "3"),
                    "contained {\n  ...\n  1: \"a\" {\n    \"a\" matched pattern /[abc]/\n  }\n  ...\n}"),
                new Fails<>(new Seq<>("1", "a", "c", "5"),
                    "contained {\n  ...\n  1: \"a\" {\n    \"a\" matched pattern /[abc]/\n  }\n  2: \"c\" {\n    \"c\" matched pattern /[abc]/\n  }\n  ...\n}"),
                new Fails<>(new Seq<>("c", "b", "a"),
                    "contained {\n  0: \"c\" {\n    \"c\" matched pattern /[abc]/\n  }\n  1: \"b\" {\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  }\n  2: \"a\" {\n    \"a\" matched pattern /[abc]/\n  }\n}"),
                new Fails<>(new Seq<>("a", "a", "a"),
                    "contained {\n  0: \"a\" {\n    \"a\" matched pattern /[abc]/\n  }\n  1: \"a\" {\n    \"a\" matched pattern /[abc]/\n  }\n  2: \"a\" {\n    \"a\" matched pattern /[abc]/\n  }\n}"),
                new Fails<>(new Seq<>("a", "a", "a", "b", "b", "b"),
                    "contained {\n  0: \"a\" {\n    \"a\" matched pattern /[abc]/\n  }\n  1: \"a\" {\n    \"a\" matched pattern /[abc]/\n  }\n  2: \"a\" {\n    \"a\" matched pattern /[abc]/\n  }\n  3: \"b\" {\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  }\n  4: \"b\" {\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  }\n  5: \"b\" {\n    \"b\" matched pattern /[abc]/\n    \"b\"\n  }\n}"),
                new HasDescription("contains none of { matches pattern /[abc]/,\n  \"b\" }")
            ));
    }
}
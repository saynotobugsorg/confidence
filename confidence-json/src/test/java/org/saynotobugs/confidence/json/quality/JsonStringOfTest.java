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

package org.saynotobugs.confidence.json.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class JsonStringOfTest
{
    @Test
    void testObject()
    {
        assertThat(new JsonStringOf(new Object(new With("foo", new String("bar")))),
            new AllOf<>(
                new Passes<>("{\"foo\": \"bar\"}", "JSON {\n" +
                    "  \"foo\": \"bar\"\n" +
                    "}"),
                new Fails<>("{\"foo\": \"buzz\"}", "JSON {\n  \"foo\": \"buzz\"\n}"),
                new Fails<>("[\"foo\"]", "JSON not an object"),
                new Fails<>("\"foo\"", "JSON not an object"),
                new Fails<>("true", "JSON not an object"),
                new Fails<>("123", "JSON not an object"),
                new Fails<>("null", "JSON not an object"),
                new HasDescription("JSON {\n  \"foo\": \"bar\"\n}")
            ));
    }


    @Test
    void testArray()
    {
        assertThat(new JsonStringOf(new Array(new Object(new With("1")), new Object(new With("2")))),
            new AllOf<>(
                new Passes<>("[{\"1\": 1},{\"2\": 2}]", "JSON [\n" +
                    "  0: {\n" +
                    "    \"1\": <1>\n" +
                    "  },\n" +
                    "  1: {\n" +
                    "    \"2\": <2>\n" +
                    "  }\n" +
                    "]"),
                new Fails<>("[\"foo\"]", "JSON had length 1"),
                new Fails<>("{\"foo\": \"buzz\"}", "JSON not an array"),
                new Fails<>("\"foo\"", "JSON not an array"),
                new Fails<>("true", "JSON not an array"),
                new Fails<>("123", "JSON not an array"),
                new Fails<>("null", "JSON not an array"),
                new HasDescription("JSON [\n  0: {\n    \"1\": <anything>\n  },\n  1: {\n    \"2\": <anything>\n  }\n]")
            ));
    }

    @Test
    void testString()
    {
        assertThat(new JsonStringOf(new String("123")),
            new AllOf<>(
                new Passes<>("\"123\"", "JSON \"123\""),
                new Fails<>("\"foo\"", "JSON \"foo\""),
                new Fails<>("[\"foo\"]", "JSON not a string"),
                new Fails<>("{\"foo\": \"buzz\"}", "JSON not a string"),
                new Fails<>("true", "JSON not a string"),
                new Fails<>("123", "JSON not a string"),
                new Fails<>("null", "JSON not a string"),
                new HasDescription("JSON \"123\"")
            ));
    }
}
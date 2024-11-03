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
                new Passes<>("{\"foo\": \"bar\"}"),
                new Fails<>("{\"foo\": \"buzz\"}", "JSON object { \"foo\": \"buzz\" }"),
                new Fails<>("[\"foo\"]", "JSON object not an object"),
                new Fails<>("\"foo\"", "JSON object not an object"),
                new Fails<>("true", "JSON object not an object"),
                new Fails<>("123", "JSON object not an object"),
                new Fails<>("null", "JSON object not an object"),
                new HasDescription("JSON object { \"foo\": \"bar\" }")
            ));
    }


    @Test
    void testArray()
    {
        assertThat(new JsonStringOf(new Array(new Object(new With("1")), new Object(new With("2")))),
            new AllOf<>(
                new Passes<>("[{\"1\": 1},{\"2\": 2}]"),
                new Fails<>("[\"foo\"]", "JSON array { had length 1\n  and\n  { 0: object not an object\n    and\n    1: missing } }"),
                new Fails<>("{\"foo\": \"buzz\"}", "JSON array not an array"),
                new Fails<>("\"foo\"", "JSON array not an array"),
                new Fails<>("true", "JSON array not an array"),
                new Fails<>("123", "JSON array not an array"),
                new Fails<>("null", "JSON array not an array"),
                new HasDescription("JSON array { has length 2\n  and\n  { 0: object { \"1\": <anything> }\n    and\n    1: object { \"2\": <anything> } } }")
            ));
    }

    @Test
    void test2()
    {
        assertThat(new JsonStringOf(new String("123")),
            new AllOf<>(
                new Passes<>("\"123\""),
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
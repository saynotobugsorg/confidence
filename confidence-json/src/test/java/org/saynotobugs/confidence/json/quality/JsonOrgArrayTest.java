/*
 * Copyright 2025 dmfs GmbH
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

package org.saynotobugs.confidence.json.quality;

import org.dmfs.jems2.iterable.Seq;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class JsonOrgArrayTest
{
    @Test
    void testStrings()
    {
        assertThat(new JsonOrgArray("foo", "bar"),
            new AllOf<>(
                new Passes<>(new JSONArray(new Seq<>("foo", "bar")), "[\n" +
                    "  0: \"foo\",\n" +
                    "  1: \"bar\"\n" +
                    "]"),

                new Fails<>(new JSONArray(), "had length 0"),
                new Fails<>(new JSONArray(new Seq<>("foo")), "had length 1"),
                new Fails<>(new JSONArray(new Seq<>("foo", "bar", "baz")), "had length 3"),
                new HasDescription("[\n  0: \"foo\",\n  1: \"bar\"\n]")
            ));
    }

    @Test
    void testNumbers()
    {
        assertThat(new JsonOrgArray(42, 17),
            new AllOf<>(
                new Passes<>(new JSONArray(new Seq<>(42, 17)), "[\n" +
                    "  0: 42,\n" +
                    "  1: 17\n" +
                    "]"),

                new Fails<>(new JSONArray(), "had length 0"),
                new Fails<>(new JSONArray(new Seq<>(42)), "had length 1"),
                new Fails<>(new JSONArray(new Seq<>(42, 17, 1)), "had length 3"),
                new HasDescription("[\n  0: 42,\n  1: 17\n]")
            ));
    }

    @Test
    void testJsonAdapter()
    {
        assertThat(new JsonOrgArray(new Number(42), new Number(17)),
            new AllOf<>(
                new Passes<>(new JSONArray(new Seq<>(42, 17)), "[\n" +
                    "  0: 42,\n" +
                    "  1: 17\n" +
                    "]"),

                new Fails<>(new JSONArray(), "had length 0"),
                new Fails<>(new JSONArray(new Seq<>(42)), "had length 1"),
                new Fails<>(new JSONArray(new Seq<>(42, 17, 1)), "had length 3"),
                new HasDescription("[\n  0: 42,\n  1: 17\n]")
            ));
    }

    @Test
    void testArrayAdapter()
    {
        assertThat(new JsonOrgArray(
                new AllOf<>(
                    new At(0, new Number(42)), new At(1, new Number(17)))),
            new AllOf<>(
                new Passes<>(new JSONArray(new Seq<>(42, 17)), "{\n" +
                    "  all of\n" +
                    "    0: 0: 42\n" +
                    "    1: 1: 17\n" +
                    "}"),
                new Passes<>(new JSONArray(new Seq<>(42, 17, 3, 4)), "{\n" +
                    "  all of\n" +
                    "    0: 0: 42\n" +
                    "    1: 1: 17\n" +
                    "}"),
                new Fails<>(new JSONArray(), "{\n  all of\n    0: 0: missing\n    1: 1: missing\n}"),
                new Fails<>(new JSONArray(new Seq<>(42)), "{\n  all of\n    ...\n    1: 1: missing\n}"),
                new Fails<>(new JSONArray(new Seq<>(42, 13, 1)), "{\n  all of\n    ...\n    1: 1: 13\n}"),
                new HasDescription("{\n  all of\n    0: 0: 42\n    1: 1: 17\n}")
            ));
    }

}
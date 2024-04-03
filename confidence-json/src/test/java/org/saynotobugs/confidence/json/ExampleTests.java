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

package org.saynotobugs.confidence.json;

import org.junit.jupiter.api.Test;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Composite.allOf;
import static org.saynotobugs.confidence.core.quality.Composite.not;
import static org.saynotobugs.confidence.core.quality.Grammar.is;
import static org.saynotobugs.confidence.core.quality.Iterable.iterates;
import static org.saynotobugs.confidence.core.quality.Object.anything;
import static org.saynotobugs.confidence.json.quality.CharSequence.jsonStringOf;
import static org.saynotobugs.confidence.json.quality.Json.*;

class ExampleTests
{
    @Test
    void testSimpleObject()
    {
        assertThat("{\n" +
                "  \"key1\": \"abc\",\n" +
                "  \"key2\": 1.23\n" +
                "}",
            is(jsonStringOf(object(
                with("key1", string("abc")),
                with("key2", number(1.23))
            ))));
    }

    @Test
    void testNestedObject()
    {
        assertThat("{\n" +
                "  \"key1\": {\n" +
                "    \"anyValue\": \"abc\",\n" +
                "    \"nullValue\": null\n" +
                "  },\n" +
                "  \"key2\": {\n" +
                "    \"numberValue\": 4\n" +
                "  }\n" +
                "}",
            is(jsonStringOf(object(
                with("key1", object(
                    with("anyValue"),
                    with("nullValue", nullValue()),
                    hasMemberCount(2)
                )),
                with("key2", object(
                    with("numberValue", 4)
                ))
            ))));
    }

    @Test
    void testArray()
    {
        assertThat("[\n" +
                "  \"a\",\n" +
                "  \"b\",\n" +
                "  \"c\"\n" +
                "]",
            allOf(
                is(jsonStringOf(array("a", "b", "c"))), // simple version, same as ...
                is(jsonStringOf(array( // ...explicit version, same as ...
                    string("a"),
                    string("b"),
                    string("c")
                ))),
                is(jsonStringOf(array( // ...this version using indices
                    allOf(
                        at(0, string("a")),
                        at(1, string("b")),
                        at(2, string("c")))
                ))),
                is(jsonStringOf(array( // you can also use negative indices to count from the end
                    allOf(
                        at(-3, string("a")),
                        at(-2, string("b")),
                        at(-1, string("c")))
                ))),
                is(jsonStringOf(arrayThat(
                    iterates(  // you can also use regular Qualities of Iterable with the `that` adapter
                        string("a"),
                        string("b"),
                        string("c"))
                )))
            ));
    }


    @Test
    void testMixedArray()
    {
        assertThat("[\n" +
                "  \"a\",\n" +
                "  {\n" +
                "    \"b\": \"xyz\"\n" +
                "  },\n" +
                "  123,\n" +
                "  null,\n" +
                "  true\n" +
                "]",
            allOf(
                is(jsonStringOf(array(
                    string("a"),
                    object(with("b", string("xyz"))),
                    number(123),
                    nullValue(),
                    bool(true)
                )))));
    }


    @Test
    void testPlainNull()
    {
        assertThat("null",
            is(jsonStringOf(nullValue())));
    }

    @Test
    void testPlainString()
    {
        assertThat("\"someValueWith\\\"quotes\\\"\"",
            is(jsonStringOf(string("someValueWith\"quotes\""))));
    }


    @Test
    void testNotAnything()
    {
        assertThat("123",
            is(jsonStringOf(
                allOf(
                    not(object(anything())),
                    not(array(anything())),
                    not(string(anything())),
                    not(bool(anything())),
                    number(anything()),
                    not(nullValue())
                ))));
    }
}
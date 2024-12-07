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

import org.dmfs.express.json.JsonValue;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;

import static org.saynotobugs.confidence.Assertion.assertThat;

class JsonValueOfTest
{
    @Test
    void test() throws IOException
    {
        assertThat(new JsonValueOf(new Object(new Anything())),
            new AllOf<>(
                new Passes<JsonValue>(new org.dmfs.express.json.elementary.Object(), "JsonValue {\n" +
                    "  <{}>\n" +
                    "}"),
                new Fails<>(new org.dmfs.express.json.elementary.Array(), "JsonValue not an object"),
                new Fails<>(new org.dmfs.express.json.elementary.String("123"), "JsonValue not an object"),
                new HasDescription("JsonValue {\n  <anything>\n}")));
    }
}
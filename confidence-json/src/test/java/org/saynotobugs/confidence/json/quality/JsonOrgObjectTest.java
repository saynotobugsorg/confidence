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

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class JsonOrgObjectTest
{
    @Test
    void test()
    {
        assertThat(new JsonOrgObject(new With("foo", "bar")),
            new AllOf<>(
                new Passes<>(new JSONObject().put("foo", "bar"), "{\n" +
                    "  \"foo\": \"bar\"\n" +
                    "}"),
                new Passes<>(new JSONObject().put("foo", "bar").put("abc", "xyz"), "{\n" +
                    "  \"foo\": \"bar\"\n" +
                    "}"),
                new Fails<>(new JSONObject(), "{\n  \"foo\": not a member\n}"),
                new Fails<>(new JSONObject().put("abc", "xyz"), "{\n  \"foo\": not a member\n}"),
                new Fails<>(new JSONObject().put("foo", "xyz"), "{\n  \"foo\": \"xyz\"\n}"),
                new HasDescription("{\n  \"foo\": \"bar\"\n}")
            ));
    }

}
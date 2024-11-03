/*
 * Copyright 2024 dmfs GmbH
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

package org.saynotobugs.confidence.quality.map;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.saynotobugs.confidence.Assertion.assertThat;

class HasSizeTest
{
    @Test
    void testEmpty()
    {
        assertThat(new HasSize(0),
            new AllOf<>(
                new Passes<>(new HashMap<>(), Collections.EMPTY_MAP),
                new Fails<>(Collections.singletonMap("a", 1), "had size 1"),
                new HasDescription("has size 0")
            ));
    }

    @Test
    void testSingleton()
    {
        assertThat(new HasSize(1),
            new AllOf<>(
                new Passes<>(new HashMap<String, Integer>()
                {{
                    put("key1", 1);
                }}),
                new Fails<Map<String, Integer>>(Collections.emptyMap(), "had size 0"),
                new Fails<>(new HashMap<String, Integer>()
                {{
                    put("key1", 1);
                    put("key2", 2);
                }}, "had size 2"),
                new HasDescription("has size 1")
            ));
    }


    @Test
    void testMultiple()
    {
        assertThat(new HasSize(3),
            new AllOf<>(
                new Passes<>(new HashMap<String, Integer>()
                {{
                    put("key1", 1);
                    put("key2", 2);
                    put("key3", 3);
                }}),
                new Fails<Map<String, Integer>>(Collections.emptyMap(), "had size 0"),
                new Fails<>(new HashMap<String, Integer>()
                {{
                    put("key1", 1);
                    put("key2", 2);
                }}, "had size 2"),
                new HasDescription("has size 3")
            ));
    }
}
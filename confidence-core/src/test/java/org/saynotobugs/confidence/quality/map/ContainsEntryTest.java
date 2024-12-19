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

package org.saynotobugs.confidence.quality.map;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.HashMap;
import java.util.Map;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ContainsEntryTest
{
    @Test
    void testKeyOnly()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2", "v2");

        assertThat(new ContainsEntry<>("k1"),
            new AllOf<>(
                new Passes<>(map1, "contained entry (\"k1\": \"v1\")"),
                new Fails<>(new HashMap<String, String>(), "{} did not contain key \"k1\""),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain key \"k1\""),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains entry (\"k1\": <anything>)")
            ));
    }


    @Test
    void testKeyQualityOnly()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2", "v2");

        assertThat(new ContainsEntry<>(new EqualTo<>("k1")),
            new AllOf<>(
                new Passes<>(map1, "contained entry (\"k1\": \"v1\")"),
                new Fails<>(new HashMap<String, String>(), "{} did not contain key \"k1\""),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain key \"k1\""),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains entry (\"k1\": <anything>)")
            ));
    }


    @Test
    void testKeyAndValue()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2", "v2");
        Map<String, String> map3 = new HashMap<>();
        map3.put("k1", "v2");

        assertThat(new ContainsEntry<>("k1", "v1"),
            new AllOf<>(
                new Passes<>(map1, "contained entry (\"k1\": \"v1\")"),
                new Fails<>(new HashMap<String, String>(), "{} did not contain key \"k1\""),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain key \"k1\""),
                new Fails<>(map3, "{ \"k1\": \"v2\" } contained entry (\"k1\": \"v2\")"),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains entry (\"k1\": \"v1\")")
            ));
    }


    @Test
    void testKeyAndValueQuality()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2", "v2");
        Map<String, String> map3 = new HashMap<>();
        map3.put("k1", "v2");

        assertThat(new ContainsEntry<>("k1", new EqualTo<>("v1")),
            new AllOf<>(
                new Passes<>(map1, "contained entry (\"k1\": \"v1\")"),
                new Fails<>(new HashMap<String, String>(), "{} did not contain key \"k1\""),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain key \"k1\""),
                new Fails<>(map3, "{ \"k1\": \"v2\" } contained entry (\"k1\": \"v2\")"),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains entry (\"k1\": \"v1\")")
            ));
    }


    @Test
    void testKeyQuality()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2", "v2");
        Map<String, String> map3 = new HashMap<>();
        map3.put("k1", "v2");

        assertThat(new ContainsEntry<>(new EqualTo<>("k1"), new EqualTo<>("v1")),
            new AllOf<>(
                new Passes<>(map1, "contained entry (\"k1\": \"v1\")"),
                new Fails<>(new HashMap<String, String>(), "{} did not contain key \"k1\""),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain key \"k1\""),
                new Fails<>(map3, "{ \"k1\": \"v2\" } did not contain entry (\"k1\": \"v1\")"),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains entry (\"k1\": \"v1\")")
            ));
    }

}
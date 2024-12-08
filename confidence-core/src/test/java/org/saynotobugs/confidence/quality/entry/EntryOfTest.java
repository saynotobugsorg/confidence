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

package org.saynotobugs.confidence.quality.entry;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.Map;

import static org.saynotobugs.confidence.Assertion.assertThat;


class EntryOfTest
{

    @Test
    void testKeyQualityAndValueQuality()
    {
        assertThat(new EntryOf<>(new EqualTo<>(12), new EqualTo<>("abc")),
            new AllOf<>(
                new Passes<>(entry(12, "abc"), "Entry (12: \"abc\")"),
                new Fails<>(entry(13, "ab"), "Entry (13: \"ab\")"),
                new HasDescription("Entry (12: \"abc\")")
            ));
    }


    @Test
    void testKeyAndValue()
    {
        assertThat(new EntryOf<>(12, "abc"),
            new AllOf<>(
                new Passes<>(entry(12, "abc"), "Entry (12: \"abc\")"),
                new Fails<>(entry(13, "ab"), "Entry (13: \"ab\")"),
                new HasDescription("Entry (12: \"abc\")")
            ));
    }


    @Test
    void testKeyAndValueQuality()
    {
        assertThat(new EntryOf<>(12, new EqualTo<>("abc")),
            new AllOf<>(
                new Passes<>(entry(12, "abc"), "Entry (12: \"abc\")"),
                new Fails<>(entry(13, "ab"), "Entry (13: \"ab\")"),
                new HasDescription("Entry (12: \"abc\")")
            ));
    }


    @Test
    void testKeyOnly()
    {
        assertThat(new EntryOf<>(12),
            new AllOf<>(
                new Passes<>(entry(12, "abc"), "Entry (12: \"abc\")"),
                new Fails<>(entry(13, "ab"), "Entry (13: \"ab\")"),
                new HasDescription("Entry (12: <anything>)")
            ));
    }


    private static <K, V> Map.Entry<K, V> entry(K key, V value)
    {
        return new Map.Entry<K, V>()
        {
            @Override
            public K getKey()
            {
                return key;
            }


            @Override
            public V getValue()
            {
                return value;
            }


            @Override
            public V setValue(V v)
            {
                throw new UnsupportedOperationException("not implemented");
            }
        };
    }
}
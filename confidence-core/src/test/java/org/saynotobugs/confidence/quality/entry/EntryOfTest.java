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
                new Passes<>(entry(12, "abc")),
                new Fails<>(entry(13, "ab"), "Entry ( 12: \"abc\" )"),
                new HasDescription("Entry ( 12: \"abc\" )")
            ));
    }


    @Test
    void testKeyAndValue()
    {
        assertThat(new EntryOf<>(12, "abc"),
            new AllOf<>(
                new Passes<>(entry(12, "abc")),
                new Fails<>(entry(13, "ab"), "Entry ( 12: \"abc\" )"),
                new HasDescription("Entry ( 12: \"abc\" )")
            ));
    }


    @Test
    void testKeyAndValueQuality()
    {
        assertThat(new EntryOf<>(12, new EqualTo<>("abc")),
            new AllOf<>(
                new Passes<>(entry(12, "abc")),
                new Fails<>(entry(13, "ab"), "Entry ( 12: \"abc\" )"),
                new HasDescription("Entry ( 12: \"abc\" )")
            ));
    }


    @Test
    void testKeyOnly()
    {
        assertThat(new EntryOf<>(12),
            new AllOf<>(
                new Passes<>(entry(12, "abc")),
                new Fails<>(entry(13, "ab"), "Entry ( 12: <anything> )"),
                new HasDescription("Entry ( 12: <anything> )")
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
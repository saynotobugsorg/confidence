package org.saynotobugs.confidence.description.valuedescription;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import java.util.HashMap;
import java.util.Map;

import static org.saynotobugs.confidence.Assertion.assertThat;


class MapDescriptionTest
{

    @Test
    void testEmptyMap()
    {
        assertThat(
            new MapDescription(new HashMap<>()),
            new DescribesAs("{}"));
    }


    @Test
    void testNonEmptyMap()
    {
        Map<String, String> map = new HashMap<>();
        map.put("a", "x");
        assertThat(
            new MapDescription(map),
            new DescribesAs("{ \"a\": \"x\" }"));
    }


    @Test
    void testMapWithManyEntries()
    {
        Map<String, String> map = new HashMap<>();
        map.put("a", "x");
        map.put("b", "y");
        map.put("c", "z");
        assertThat(
            new MapDescription(map),
            new DescribesAs("{ \"a\": \"x\", \"b\": \"y\", \"c\": \"z\" }"));
    }

}
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
                new Passes<>(map1),
                new Fails<>(new HashMap<String, String>(), "{  } did not contain { Entry ( \"k1\": <anything> ) }"),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain { Entry ( \"k1\": <anything> ) }"),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains { Entry ( \"k1\": <anything> ) }")
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
                new Passes<>(map1),
                new Fails<>(new HashMap<String, String>(), "{  } did not contain { Entry ( \"k1\": <anything> ) }"),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain { Entry ( \"k1\": <anything> ) }"),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains { Entry ( \"k1\": <anything> ) }")
            ));
    }


    @Test
    void testKeyAndValue()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2", "v2");

        assertThat(new ContainsEntry<>("k1", "v1"),
            new AllOf<>(
                new Passes<>(map1),
                new Fails<>(new HashMap<String, String>(), "{  } did not contain { Entry ( \"k1\": \"v1\" ) }"),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain { Entry ( \"k1\": \"v1\" ) }"),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains { Entry ( \"k1\": \"v1\" ) }")
            ));
    }


    @Test
    void testKeyAndValueQuality()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2", "v2");

        assertThat(new ContainsEntry<>("k1", new EqualTo<>("v1")),
            new AllOf<>(
                new Passes<>(map1),
                new Fails<>(new HashMap<String, String>(), "{  } did not contain { Entry ( \"k1\": \"v1\" ) }"),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain { Entry ( \"k1\": \"v1\" ) }"),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains { Entry ( \"k1\": \"v1\" ) }")
            ));
    }


    @Test
    void testKeyQuality()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2", "v2");

        assertThat(new ContainsEntry<>(new EqualTo<>("k1")),
            new AllOf<>(
                new Passes<>(map1),
                new Fails<>(new HashMap<String, String>(), "{  } did not contain { Entry ( \"k1\": <anything> ) }"),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain { Entry ( \"k1\": <anything> ) }"),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains { Entry ( \"k1\": <anything> ) }")
            ));
    }

}
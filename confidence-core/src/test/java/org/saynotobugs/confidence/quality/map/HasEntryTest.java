package org.saynotobugs.confidence.quality.map;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.HashMap;
import java.util.Map;

import static org.saynotobugs.confidence.Assertion.assertThat;


class HasEntryTest
{

    @Test
    void test()
    {
        Map<String, String> map1 = new HashMap<>();
        map1.put("k1", "v1");
        Map<String, String> map2 = new HashMap<>();
        map2.put("k2", "v2");

        assertThat(new HasEntry<>("k1", "v1"),
            new AllOf<>(
                new Passes<>(map1),
                new Fails<>(new HashMap<String, String>(), "{  } did not contain { Entry ( \"k1\": \"v1\" ) }"),
                new Fails<>(map2, "{ \"k2\": \"v2\" } did not contain { Entry ( \"k1\": \"v1\" ) }"),
                //  new Fails<>(Map.of("k2", "v2"), new DescribesAs("")),
                new HasDescription("contains { Entry ( \"k1\": \"v1\" ) }")
            ));

    }

}
package org.saynotobugs.confidence.description;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import java.util.Map;

import static org.saynotobugs.confidence.Assertion.assertThat;


class MapEntryDescriptionTest
{

    @Test
    void testStrings()
    {
        assertThat(new MapEntryDescription(new Map.Entry<String, String>()
            {
                @Override
                public String getKey()
                {
                    return "key";
                }


                @Override
                public String getValue()
                {
                    return "value";
                }


                @Override
                public String setValue(String value)
                {
                    throw new AssertionError("unexpected call to setValue");
                }
            }),
            new DescribesAs("\"key\": \"value\""));
    }


    @Test
    void testNested()
    {
        assertThat(new MapEntryDescription(new Map.Entry<Integer, Iterable<Integer>>()
            {
                @Override
                public Integer getKey()
                {
                    return 42;
                }


                @Override
                public Iterable<Integer> getValue()
                {
                    return new Seq<>(1, 2);
                }


                @Override
                public Iterable<Integer> setValue(Iterable<Integer> value)
                {
                    throw new AssertionError("unexpected call to setValue");
                }

            }),
            new DescribesAs("42: [ 1, 2 ]"));
    }

}
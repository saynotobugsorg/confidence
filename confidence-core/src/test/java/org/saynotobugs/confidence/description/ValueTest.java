package org.saynotobugs.confidence.description;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.saynotobugs.confidence.Assertion.assertThat;


class ValueTest
{
    @Test
    void test()
    {
        assertThat(new Value(null), new DescribesAs("<null>"));
        assertThat(new Value(123), new DescribesAs("<123>"));
        assertThat(new Value("abc"), new DescribesAs("\"abc\""));
        assertThat(new Value(true), new DescribesAs("<true>"));
        assertThat(new Value(new Seq<>(5, "abc", 7)), new DescribesAs("[ <5>,\n  \"abc\",\n  <7> ]"));
        assertThat(new Value(new Map.Entry<String, Integer>()
        {
            @Override
            public String getKey()
            {
                return "abc";
            }


            @Override
            public Integer getValue()
            {
                return 123;
            }


            @Override
            public Integer setValue(Integer value)
            {
                throw new AssertionError("unexpected call to setValue");
            }

        }), new DescribesAs("\"abc\": <123>"));
        assertThat(new Value(empty()), new DescribesAs("<empty>"));
        assertThat(new Value(of(123)), new DescribesAs("<present <123>>"));
        assertThat(new Value(new String[]{"a", "b", "c"}), new DescribesAs("[ \"a\",\n  \"b\",\n  \"c\" ]"));
        assertThat(new Value(new Text("123")), new DescribesAs("\n  ----\n  123\n  ----"));
    }


    @Test
    void testMap()
    {
        Map<String, Integer> map = new HashMap<>();
        map.put("abc", 123);
        assertThat(new Value(map), new DescribesAs("{ \"abc\": <123> }"));
    }

    enum TestEnum
    {
        X, Y, Z
    }

    @Test
    void testSet()
    {
        assertThat(new Value(EnumSet.of(TestEnum.X, TestEnum.Y, TestEnum.Z)),
            new DescribesAs("{ <X>,\n  <Y>,\n  <Z> }"));
    }
}
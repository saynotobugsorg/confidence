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

package org.saynotobugs.confidence.description;

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.saynotobugs.confidence.Assertion.assertThat;


class ValueTest
{
    @Test
    void test()
    {
        assertThat(new Value(null), new DescribesAs("<null>"));
        assertThat(new Value(123), new DescribesAs("123"));
        assertThat(new Value("abc"), new DescribesAs("\"abc\""));
        assertThat(new Value(true), new DescribesAs("true"));
        assertThat(new Value(new Seq<>(5, "abc", 7)), new DescribesAs("[ 5, \"abc\", 7 ]"));
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

        }), new DescribesAs("\"abc\": 123"));
        assertThat(new Value(empty()), new DescribesAs("<empty>"));
        assertThat(new Value(of(123)), new DescribesAs("<present 123>"));
        assertThat(new Value(Pattern.compile("123")), new DescribesAs("/123/"));
        assertThat(new Value(new String[] { "a", "b", "c" }), new DescribesAs("[ \"a\", \"b\", \"c\" ]"));
        assertThat(new Value(new Text("123")), new DescribesAs("\n  ----\n  123\n  ----"));
    }


    @Test
    void testMap()
    {
        Map<String, Integer> map = new HashMap<>();
        map.put("abc", 123);
        assertThat(new Value(map), new DescribesAs("{ \"abc\": 123 }"));
    }

    enum TestEnum
    {
        X, Y, Z
    }

    @Test
    void testSet()
    {
        assertThat(new Value(EnumSet.of(TestEnum.X, TestEnum.Y, TestEnum.Z)),
            new DescribesAs("{ <X>, <Y>, <Z> }"));
    }
}
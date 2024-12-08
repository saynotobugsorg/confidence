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
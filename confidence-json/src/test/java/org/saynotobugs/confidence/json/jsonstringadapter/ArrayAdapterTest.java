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

package org.saynotobugs.confidence.json.jsonstringadapter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.json.JsonArrayAdapter;
import org.saynotobugs.confidence.json.JsonElementAdapter;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.HasToString;

import static java.util.Arrays.asList;
import static org.dmfs.jems2.confidence.Jems2.absent;
import static org.dmfs.jems2.confidence.Jems2.present;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ArrayAdapterTest
{
    @Test
    void testEmptyArray()
    {
        assertThat(new ArrayAdapter(new JSONArray()),
            new AllOf<>(
                new Has<>(JsonArrayAdapter::length, new Is<>(0)),
                new Has<>(adapter -> adapter.elementAt(-1), absent()),
                new Has<>(adapter -> adapter.elementAt(0), absent()),
                new Has<>(adapter -> adapter.elementAt(1), absent()),
                new HasToString("[]")
            ));
    }


    @Test
    void testArrayWithValues()
    {
        assertThat(new ArrayAdapter(new JSONArray(asList("a", 1, true, new JSONObject(), new JSONArray(), null))),
            new AllOf<>(
                new Has<>(JsonArrayAdapter::length, new Is<>(6)),
                new Has<>(adapter -> adapter.elementAt(-7), absent()),
                new Has<>(adapter -> adapter.elementAt(-6), present(new Has<>(JsonElementAdapter::asString, present("a")))),
                new Has<>(adapter -> adapter.elementAt(-5), present(new Has<>(JsonElementAdapter::asNumber, present(1)))),
                new Has<>(adapter -> adapter.elementAt(-4), present(new Has<>(JsonElementAdapter::asBoolean, present(true)))),
                new Has<>(adapter -> adapter.elementAt(-3), present(new Has<>(JsonElementAdapter::asObject, present(new Anything())))),
                new Has<>(adapter -> adapter.elementAt(-2), present(new Has<>(JsonElementAdapter::asArray, present(new Anything())))),
                new Has<>(adapter -> adapter.elementAt(-1), present(new Has<>(JsonElementAdapter::isNull, new Is<>(true)))),
                new Has<>(adapter -> adapter.elementAt(0), present(new Has<>(JsonElementAdapter::asString, present("a")))),
                new Has<>(adapter -> adapter.elementAt(1), present(new Has<>(JsonElementAdapter::asNumber, present(1)))),
                new Has<>(adapter -> adapter.elementAt(2), present(new Has<>(JsonElementAdapter::asBoolean, present(true)))),
                new Has<>(adapter -> adapter.elementAt(3), present(new Has<>(JsonElementAdapter::asObject, present(new Anything())))),
                new Has<>(adapter -> adapter.elementAt(4), present(new Has<>(JsonElementAdapter::asArray, present(new Anything())))),
                new Has<>(adapter -> adapter.elementAt(5), present(new Has<>(JsonElementAdapter::isNull, new Is<>(true)))),
                new Has<>(adapter -> adapter.elementAt(6), absent()),
                new HasToString("[\"a\",1,true,{},[],null]")
            ));
    }

}
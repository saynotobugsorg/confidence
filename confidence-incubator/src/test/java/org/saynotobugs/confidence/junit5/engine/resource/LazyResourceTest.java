/*
 * Copyright 2023 dmfs GmbH
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.saynotobugs.confidence.junit5.engine.resource;

import org.dmfs.jems2.Pair;
import org.dmfs.jems2.pair.ValuePair;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.junit5.engine.quality.ResourceThat;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.dmfs.jems2.confidence.Jems2.hasValue;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.AutoClosable.autoClosableThat;
import static org.saynotobugs.confidence.core.quality.Composite.allOf;
import static org.saynotobugs.confidence.core.quality.Composite.has;
import static org.saynotobugs.confidence.core.quality.Iterable.emptyIterable;
import static org.saynotobugs.confidence.core.quality.Iterable.iterates;
import static org.saynotobugs.confidence.core.quality.Object.throwing;


class LazyResourceTest
{
    @Test
    void test()
    {
        assertThat(() -> new LazyResource<>(() -> new ArrayList<>(asList(1, 2, 3)), List::clear),
            new ResourceThat<>(
                100,
                iterates(1, 2, 3),
                emptyIterable())
        );
    }

    @Test
    void testMultipleUsers()
    {
        assertThat(new LazyResource<>(() -> new ArrayList<>(asList(1, 2, 3)), List::clear),
            allOf(
                has("2 derivates", r -> new ValuePair<>(r.value(), r.value()),
                    allOf(
                        has("left", Pair::left, autoClosableThat(hasValue(iterates(1, 2, 3)))),
                        has("right", Pair::right, autoClosableThat(hasValue(iterates(1, 2, 3))))
                    )),
                hasValue(hasValue(emptyIterable()))
            )
        );
    }

    @Test
    void testMultipleCloses()
    {
        assertThat(new LazyResource<>(() -> new ArrayList<>(asList(1, 2, 3)), List::clear),
            hasValue(
                allOf(
                    autoClosableThat(hasValue(iterates(1, 2, 3))),
                    has("closable", value -> value::close, throwing(IllegalStateException.class)))));
    }

}
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

package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.jems2.comparator.By;
import org.dmfs.jems2.iterable.EmptyIterable;
import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class OrderedTest
{
    @Test
    void test()
    {
        assertThat(new Ordered<>(new By<>(String::length)),
            new AllOf<>(
                new Passes<>(new Seq<>("1", "2", "3"), "ordered"),
                new Passes<>(new Seq<>("1", "12", "123"), "ordered"),
                new Passes<>(new Seq<>("1", "12"), "ordered"),
                new Passes<>(new Seq<>("1"), "ordered"),
                new Passes<Iterable<String>>(new EmptyIterable<>(), "ordered"),
                new Fails<>(new Seq<>("123", "12", "1"), "[\n  0: \"123\" > 1: \"12\"\n  1: \"12\" > 2: \"1\"\n]"),
                new Fails<>(new Seq<>("1", "2", "12", "1"), "[\n  ...\n  2: \"12\" > 3: \"1\"\n]"),
                new HasDescription("ordered")
            )
        );
    }

    @Test
    void testWithDescription()
    {
        assertThat(new Ordered<>("by length", new By<>(String::length)),
            new AllOf<>(
                new Passes<>(new Seq<>("1", "2", "3"), "ordered by length"),
                new Passes<>(new Seq<>("1", "12", "123"), "ordered by length"),
                new Passes<>(new Seq<>("1", "12"), "ordered by length"),
                new Passes<>(new Seq<>("1"), "ordered by length"),
                new Passes<Iterable<String>>(new EmptyIterable<>(), "ordered by length"),
                new Fails<>(new Seq<>("123", "12", "1"), "[\n  0: \"123\" > 1: \"12\"\n  1: \"12\" > 2: \"1\"\n]"),
                new Fails<>(new Seq<>("1", "2", "12", "1"), "[\n  ...\n  2: \"12\" > 3: \"1\"\n]"),
                new HasDescription("ordered by length")
            )
        );
    }
}
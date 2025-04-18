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

package org.saynotobugs.confidence.utils;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.quality.object.InstanceOf;
import org.saynotobugs.confidence.quality.object.MutatedBy;
import org.saynotobugs.confidence.quality.object.Throwing;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ArrayIterableTest
{

    @Test
    void testEmpty()
    {
        assertThat(
            new ArrayIterable(new int[] {}),
            new Iterates<>());
    }


    @Test
    void testInt()
    {
        assertThat(
            new ArrayIterable(new int[] { 1, 2, 3 }),
            new Iterates<>(1, 2, 3));
    }


    @Test
    void testBoolean()
    {
        assertThat(
            new ArrayIterable(new boolean[] { true, false, false, true }),
            new Iterates<>(true, false, false, true));
    }


    @Test
    void testNestedInt()
    {
        assertThat(
            new ArrayIterable(new int[][] { new int[] { 1, 2 }, new int[] { 3, 4 } }),
            new Iterates<>(new int[] { 1, 2 }, new int[] { 3, 4 }));
    }


    @Test
    void testNonArray()
    {
        assertThat(() -> new ArrayIterable("abc"),
            new Throwing(new InstanceOf<>(RuntimeException.class)));
    }


    @Test
    void testThrowsAfterLast()
    {
        assertThat(new ArrayIterable(new String[] { "foo" }),
            new Has<>(iterable -> () -> iterable.iterator(),
                new MutatedBy<Iterator<Object>>(
                    new Text("get next"),
                    iterator -> iterator.next(),
                    new Has<>(iterator -> () -> iterator.next(), new Throwing(NoSuchElementException.class))
                )));
    }
}
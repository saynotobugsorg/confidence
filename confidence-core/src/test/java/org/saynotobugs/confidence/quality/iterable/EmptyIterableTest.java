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

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static java.util.Collections.emptyList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class EmptyIterableTest
{

    @Test
    void test()
    {
        assertThat(new EmptyIterable(),
            new AllOf<>(
                new Passes<Iterable<Integer>>(emptyList(), "[]"),
                new Fails<>(new Seq<>(1, 2, 3), "[ 1, 2, 3 ]"),
                new HasDescription("[]")
            ));
    }
}
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

package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class HashCodeEqualsTest
{
    @Test
    void testHashCode()
    {
        assertThat(new HashCodeEquals(123),
            new AllOf<>(
                new Passes<>(123, 123L),
                new Fails<>(124, "had hashCode 124"),
                new HasDescription("has hashCode 123")
            ));
    }

    @Test
    void testReferenceObject()
    {
        assertThat(new HashCodeEquals("123"),
            new AllOf<>(
                new Passes<>("123"),
                new Fails<Object>(321, "had hashCode 321"),
                new HasDescription("has hashCode 48690 like \"123\"")
            ));
    }
}
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

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.junit5.engine.quality.DelegatingResourceThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singletonList;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.emptyIterable;
import static org.saynotobugs.confidence.quality.Core.iterates;

class DerivedTest
{
    @Test
    void testSimple()
    {
        assertThat(orig -> new Derived<>(HashSet::new, orig),
            new DelegatingResourceThat<List<String>, Set<String>>(() -> singletonList("xyz"), iterates("xyz"))
        );
    }

    @Test
    void testWithCleanUp()
    {
        assertThat(orig -> new Derived<>(HashSet::new, orig, Set::clear),
            new DelegatingResourceThat<List<String>, Set<String>>(() -> singletonList("xyz"), iterates("xyz"), emptyIterable())
        );
    }

}
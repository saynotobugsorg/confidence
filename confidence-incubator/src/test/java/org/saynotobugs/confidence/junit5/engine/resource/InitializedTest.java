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

package org.saynotobugs.confidence.junit5.engine.resource;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.junit5.engine.quality.DelegatingResourceThat;

import java.util.ArrayList;
import java.util.List;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Iterable.iterates;

class InitializedTest
{
    @Test
    void test()
    {
        assertThat(orig -> new Initialized<>(list -> list.add("xyz"), orig),
            new DelegatingResourceThat<List<String>, List<String>>(ArrayList::new, iterates("xyz"))
        );
    }
}
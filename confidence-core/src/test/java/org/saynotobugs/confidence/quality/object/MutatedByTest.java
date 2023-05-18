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
import org.saynotobugs.confidence.description.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.allOf;
import static org.saynotobugs.confidence.quality.Core.iterates;
import static org.saynotobugs.confidence.test.quality.Test.*;


class MutatedByTest
{
    @Test
    void test()
    {
        assertThat(new MutatedBy<List<String>>(new Text("appending abc"), l -> l.add("abc"), iterates("abc")),
            allOf(
                passes(ArrayList::new, LinkedList::new),
                fails(() -> new ArrayList<>(asList("123")), "mutated by appending abc iterated [ 0: \"123\",\n  1: additional \"abc\" ]"),
                hasDescription("mutated by appending abc iterates [ 0: \"abc\" ]")
            )
        );
    }

}
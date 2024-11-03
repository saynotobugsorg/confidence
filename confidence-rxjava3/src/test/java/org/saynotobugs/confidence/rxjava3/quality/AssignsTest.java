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

package org.saynotobugs.confidence.rxjava3.quality;

import io.reactivex.rxjava3.functions.Function;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AssignsTest
{
    @Test
    void testWithValue()
    {
        assertThat(new Assigns<>("abc", 3),
            new AllOf<>(
                new Passes<Function<String, Integer>>(String::length, x -> 3),
                new Fails<Function<String, Integer>>(x -> 4, "assigned \"abc\" 4"),
                new HasDescription("assigns \"abc\" 3")
            ));
    }


    @Test
    void testWithQuality()
    {
        assertThat(new Assigns<>("abc", new EqualTo<>(3)),
            new AllOf<>(
                new Passes<Function<String, Integer>>(String::length, x -> 3),
                new Fails<Function<String, Integer>>(x -> 4, "assigned \"abc\" 4"),
                new HasDescription("assigns \"abc\" 3")
            ));
    }

}
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

package org.saynotobugs.confidence.test.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;

import static org.saynotobugs.confidence.Assertion.assertThat;


class FailedTest
{

    @Test
    void test()
    {
        assertThat(new Failed(new EqualTo<>("<123>")),
            new AllOf<>(
                // currently, we can't match the matching case due to the recursive description
                //new Passes<>(new Fail(new Text("")), ""),
                new Fails<>(new Pass(new Text("<123>")), "passed")));
    }

}
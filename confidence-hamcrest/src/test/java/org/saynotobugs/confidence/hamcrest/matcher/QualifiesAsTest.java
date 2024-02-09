/*
 * Copyright 2024 dmfs GmbH
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

package org.saynotobugs.confidence.hamcrest.matcher;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.object.EqualTo;

import static org.dmfs.jems2.hamcrest.matchers.matcher.MatcherMatcher.*;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

class QualifiesAsTest
{
    @Test
    void test()
    {
        assertThat(new QualifiesAs<>(new EqualTo<>("123")),
            allOf(
                matches("123"),
                mismatches("12", "\"12\""),
                describesAs("\"123\"")
            ));
    }

}
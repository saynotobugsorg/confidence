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

package org.saynotobugs.confidence.utils;

import org.junit.jupiter.api.Test;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.equalTo;
import static org.saynotobugs.confidence.quality.Core.has;


class FailSafeTest
{
    @Test
    void testNoException()
    {
        assertThat(new FailSafe<String, String>(t -> "fail", x -> x),
            has(fs -> fs.value("123"), equalTo("123")));
    }


    @Test
    void testException()
    {
        assertThat(new FailSafe<String, String>(Throwable::getMessage, x -> {throw new IllegalArgumentException("xyz");}),
            has(fs -> fs.value("123"), equalTo("xyz")));
    }
}
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

package org.saynotobugs.confidence;

import org.saynotobugs.confidence.scribe.StringBuilderScribe;


public final class Assertion
{
    public static <T> void assertThat(T candidate, Quality<? super T> quality) throws AssertionError
    {
        Assessment assessment = quality.assessmentOf(candidate);
        if (!assessment.isSuccess())
        {
            Scribe ex = new StringBuilderScribe("  ");
            quality.description().describeTo(ex);
            Scribe diff = new StringBuilderScribe("  ");
            assessment.description().describeTo(diff);
            throw new AssertionError(
                "Expected:" + System.lineSeparator() + System.lineSeparator()
                    + ex + System.lineSeparator() + System.lineSeparator() +
                    "Actual:   " + System.lineSeparator() + System.lineSeparator() + diff + System.lineSeparator());
        }
    }
}

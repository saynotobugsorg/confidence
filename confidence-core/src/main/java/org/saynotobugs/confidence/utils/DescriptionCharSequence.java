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

import org.dmfs.jems2.charsequence.DelegatingCharSequence;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.scribe.StringBuilderScribe;


public final class DescriptionCharSequence extends DelegatingCharSequence
{
    public DescriptionCharSequence(Description delegate)
    {
        super(charSequenceOf(delegate));
    }


    private static String charSequenceOf(Description description)
    {
        StringBuilderScribe scribe = new StringBuilderScribe("  ");
        description.describeTo(scribe);
        return scribe.toString();
    }
}

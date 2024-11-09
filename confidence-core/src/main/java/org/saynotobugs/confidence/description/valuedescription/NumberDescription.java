/*
 * Copyright 2022 dmfs GmbH
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

package org.saynotobugs.confidence.description.valuedescription;

import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Scribe;


/**
 * The {@link Description} of a {@link Number} value.
 */
public final class NumberDescription implements Description
{
    private final Number mNumber;

    public NumberDescription(Number number)
    {
        mNumber = number;
    }

    @Override
    public void describeTo(Scribe scribe)
    {
        scribe.append(mNumber.toString());
        if (mNumber instanceof Long)
        {
            scribe.append("l");
        }
        if (mNumber instanceof Float)
        {
            scribe.append("f");
        }
        if (mNumber instanceof Double)
        {
            scribe.append("d");
        }
    }
}

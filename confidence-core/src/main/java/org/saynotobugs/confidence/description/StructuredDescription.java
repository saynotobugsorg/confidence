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

package org.saynotobugs.confidence.description;

import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Scribe;
import org.saynotobugs.confidence.utils.Intermittent;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


/**
 * A generic {@link Description} for structured values.
 */
public final class StructuredDescription implements Description
{
    private final Description mEntry;
    private final Description mDelimiter;
    private final Description mExit;
    private final Iterable<? extends Description> mValue;


    public StructuredDescription(CharSequence delimiter, Iterable<? extends Description> value)
    {
        this(EMPTY, new TextDescription(delimiter), EMPTY, value);
    }


    public StructuredDescription(Description delimiter, Iterable<? extends Description> value)
    {
        this(EMPTY, delimiter, EMPTY, value);
    }


    public StructuredDescription(String entry, String delimiter, String exit, Iterable<? extends Description> value)
    {
        this(new TextDescription(entry), new TextDescription(delimiter), new TextDescription(exit), value);
    }


    public StructuredDescription(Description entry, Description delimiter, Description exit, Iterable<? extends Description> value)
    {
        mEntry = entry;
        mDelimiter = delimiter;
        mExit = exit;
        mValue = value;
    }


    @Override
    public void describeTo(Scribe scribe)
    {
        Scribe s = scribe.indented();
        new Intermittent<Description>(
            () -> mEntry.describeTo(scribe),
            () -> mDelimiter.describeTo(s),
            () -> mExit.describeTo(scribe),
            e -> e.describeTo(s)).process(mValue);
    }
}

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

package org.saynotobugs.confidence.assessment;

import org.dmfs.jems2.iterable.Frozen;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.optional.First;
import org.dmfs.jems2.predicate.Not;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.FailDescription;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


/**
 * A {@link Assessment} that passes when all the delegate {@link Assessment}s pass.
 */
public final class AllPassed implements Assessment
{
    private final Description mEntry;
    private final Description mDelimiter;
    private final Description mExit;
    private final Iterable<Assessment> mResults;


    public AllPassed(Description entry, Description delimiter, Assessment... assessments)
    {
        this(entry, delimiter, EMPTY, new Seq<>(assessments));
    }


    public AllPassed(Description entry, Description delimiter, Description exit, Assessment... assessments)
    {
        this(entry, delimiter, exit, new Seq<>(assessments));
    }


    public AllPassed(Description entry, Description delimiter, Iterable<Assessment> results)
    {
        this(entry, delimiter, EMPTY, results);
    }


    public AllPassed(Description entry, Description delimiter, Description exit, Iterable<Assessment> results)
    {
        mEntry = entry;
        mDelimiter = delimiter;
        mResults = new Frozen<>(results);
        mExit = exit;
    }


    @Override
    public boolean isSuccess()
    {
        return !new First<>(new Not<>(Assessment::isSuccess), mResults).isPresent();
    }


    @Override
    public Description description()
    {
        return isSuccess()
            ? EMPTY
            : new FailDescription(mEntry, mDelimiter, mExit, mResults);

    }

}

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

import org.dmfs.jems2.comparator.By;
import org.dmfs.jems2.iterable.*;
import org.dmfs.jems2.optional.First;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.*;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


/**
 * A {@link Assessment} that passes when any of the delegate {@link Assessment}s passed.
 */
public final class AnyPassed implements Assessment
{
    private final Description mEntry;
    private final Description mDelimiter;
    private final Description mExit;
    private final Iterable<Assessment> mResults;


    public AnyPassed(Description entry, Description delimiter, Assessment... assessments)
    {
        this(entry, delimiter, EMPTY, new Seq<>(assessments));
    }


    public AnyPassed(Description entry, Description delimiter, Description exit, Assessment... assessments)
    {
        this(entry, delimiter, exit, new Seq<>(assessments));
    }


    public AnyPassed(Description entry, Description delimiter, Iterable<Assessment> results)
    {
        this(entry, delimiter, EMPTY, results);
    }


    public AnyPassed(Description entry, Description delimiter, Description exit, Iterable<Assessment> results)
    {
        mEntry = entry;
        mDelimiter = delimiter;
        mResults = new Frozen<>(results);
        mExit = exit;
        isSuccess(); // trigger evaluation
    }


    @Override
    public boolean isSuccess()
    {
        return new First<>(Assessment::isSuccess, mResults).isPresent();
    }


    @Override
    public Description description()
    {
        return isSuccess()
            ? new Block(mEntry, EMPTY, mExit,
            new Seq<>(
                new Composite(
                    new Delimited(NEW_LINE,
                        new Expanded<>(
                            cluster -> !cluster.iterator().next().isSuccess()
                                ? new Seq<>(new Text("..."))
                                : new Mapped<>(Assessment::description, cluster),
                            new Clustered<>(new By<>(Assessment::isSuccess), mResults))))), new Composite(mEntry, mExit))
            : new Block(mEntry, EMPTY, mExit, new Seq<>(new FailDescription(new Composite(mDelimiter, NEW_LINE), mResults)), new Composite(mEntry, mExit));
    }

}

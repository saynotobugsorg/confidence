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

import org.dmfs.jems2.comparator.By;
import org.dmfs.jems2.iterable.Clustered;
import org.dmfs.jems2.iterable.Mapped;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;

import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


/**
 * A {@link Description} that contains the mismatch descriptions of failing {@link Assessment}s.
 * <p>
 * Successful {@link Assessment}s are collapsed into {@code ...}.
 */
public final class FailDescription extends DescriptionComposition
{
    public FailDescription(Description entry, Description delimiter, Description exit, Iterable<Assessment> verdicts)
    {
        super(new Structured(
            entry,
            NEW_LINE,
            exit,
            new Mapped<>(
                cluster -> cluster.iterator().next().isSuccess()
                    ? new Text("...")
                    : new Delimited(delimiter, new Mapped<>(Assessment::description, cluster)),
                new Clustered<>(new By<>(Assessment::isSuccess), verdicts))));
    }
}

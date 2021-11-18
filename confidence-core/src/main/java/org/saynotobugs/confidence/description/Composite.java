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

import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.procedure.ForEach;
import org.saynotobugs.confidence.Description;


/**
 * A {@link Description} composed of other {@link Description}s.
 */
public final class Composite extends DescriptionComposition
{
    /**
     * Creates a {@link Description} of the sequence of the given {@link Description}s.
     */
    public Composite(Description... descriptions)
    {
        this(new Seq<>(descriptions));
    }


    /**
     * Creates a {@link Description} of the given {@link Description} sequence.
     */
    public Composite(Iterable<? extends Description> descriptions)
    {
        super(scribe -> new ForEach<>(descriptions).process(d -> d.describeTo(scribe)));
    }
}

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

package org.saynotobugs.confidence.description;

import org.dmfs.jems2.iterable.Seq;
import org.saynotobugs.confidence.Description;

import static org.saynotobugs.confidence.description.LiteralDescription.SPACE;


/**
 * A {@link Description} that contains other {@link Description}s joined with a single {@link LiteralDescription#SPACE}.
 */
public final class Spaced extends DescriptionComposition
{
    public Spaced(Description... descriptions)
    {
        this(new Seq<>(descriptions));
    }


    public Spaced(Iterable<Description> descriptions)
    {
        super(new Delimited(SPACE, descriptions));
    }
}

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

import org.saynotobugs.confidence.Description;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


/**
 * A generic {@link Description} for structured values.
 */
public final class Structured extends DescriptionComposition
{
    public Structured(CharSequence delimiter, Iterable<? extends Description> value)
    {
        this(EMPTY, new Text(delimiter), EMPTY, value);
    }


    public Structured(Description delimiter, Iterable<? extends Description> value)
    {
        this(EMPTY, delimiter, EMPTY, value);
    }


    public Structured(String entry, String delimiter, String exit, Iterable<? extends Description> value)
    {
        this(new Text(entry), new Text(delimiter), new Text(exit), value);
    }


    public Structured(Description entry, Description delimiter, Description exit, Iterable<? extends Description> value)
    {
        super(new Indented(new Enclosed(entry, new Delimited(delimiter, value), exit)));
    }
}

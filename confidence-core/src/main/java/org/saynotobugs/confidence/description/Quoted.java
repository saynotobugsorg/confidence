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


/**
 * A {@link Description} that's put in some sort of quoting characters.
 */
public final class Quoted extends DescriptionComposition
{

    public Quoted(String quoteString, Description delegate)
    {
        this(quoteString, delegate, quoteString);
    }


    public Quoted(String opening, Description delegate, String closing)
    {
        this(new Text(opening), delegate, new Text(closing));
    }


    public Quoted(Description quoteString, Description delegate)
    {
        this(quoteString, delegate, quoteString);
    }


    public Quoted(Description opening, Description delegate, Description closing)
    {
        super(new Composite(opening, delegate, closing));
    }
}

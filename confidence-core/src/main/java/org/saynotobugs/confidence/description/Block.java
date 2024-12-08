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
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


/**
 * A generic {@link Description} for structured values.
 */
public final class Block extends DescriptionComposition
{
    public Block(String blockPrefix, String elementDelimiter, String blockSuffix, Iterable<? extends Description> elements)
    {
        this(new Text(blockPrefix), new Text(elementDelimiter), new Text(blockSuffix), elements);
    }


    public Block(String blockPrefix, String elementDelimiter, String blockSuffix, Iterable<? extends Description> elements, String emptyFallback)
    {
        this(new Text(blockPrefix), new Text(elementDelimiter), new Text(blockSuffix), elements, new Text(emptyFallback));
    }


    public Block(Description blockPrefix, Description elementDelimiter, Description blockSuffix, Iterable<? extends Description> elements)
    {
        this(blockPrefix, elementDelimiter, blockSuffix, elements, new Composite(blockPrefix, blockSuffix));
    }

    public Block(Description blockPrefix, Description elementDelimiter, Description blockSuffix, Iterable<? extends Description> elements, Description emptyFallback)
    {
        super(new Enclosed(blockPrefix,
            new Indented(
                new Prefixed(NEW_LINE,
                    new Delimited(new Composite(elementDelimiter, NEW_LINE), elements),
                    EMPTY)),
            new Prefixed(NEW_LINE, blockSuffix, EMPTY),
            emptyFallback));
    }
}

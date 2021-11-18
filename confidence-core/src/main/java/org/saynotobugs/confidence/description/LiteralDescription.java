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


/**
 * A {@link Description} that's printed literally. This provides certain control and frequently used punctuation characters.
 */
public final class LiteralDescription extends DescriptionComposition
{
    public static final Description DQUOTES = new LiteralDescription("\"");
    public static final Description COMMA = new LiteralDescription(",");
    public static final Description SPACE = new LiteralDescription(" ");
    public static final Description EMPTY = scribe -> {};
    public static final Description NEW_LINE = Scribe::newLine;
    public static final Description NULL = new LiteralDescription("<null>");
    public static final Description COMMA_NEW_LINE = new Composite(COMMA, NEW_LINE);


    /**
     * This class is not supposed to be used directly.
     */
    private LiteralDescription(CharSequence text)
    {
        super(scribe -> scribe.append(text));
    }
}

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

package org.saynotobugs.confidence.description.valuedescription;

import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.DescriptionComposition;
import org.saynotobugs.confidence.description.Quoted;
import org.saynotobugs.confidence.description.Text;

import java.util.regex.Pattern;

import static org.saynotobugs.confidence.description.LiteralDescription.SLASH;


/**
 * The {@link Description} of a {@link Pattern} value
 */
public final class PatternDescription extends DescriptionComposition
{
    /**
     * Creates a {@link Description} for the given {@link Pattern}.
     */
    public PatternDescription(Pattern value)
    {
        super(new Quoted(SLASH, new Text(value::pattern)));
    }
}

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

import org.eclipse.jdt.annotation.Nullable;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Scribe;
import org.saynotobugs.confidence.utils.ArrayIterable;

import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import static org.saynotobugs.confidence.description.LiteralDescription.NULL;


/**
 * A {@link Description} for all types of values. If possible, it delegates to a concrete {@link Description} for the type of the given value,
 * otherwise it delegates to {@link ToStringDescription}.
 */
public final class Value implements Description
{
    @Nullable
    private final Object value;


    public Value(@Nullable Object value)
    {
        this.value = value;
    }


    @Override
    public void describeTo(Scribe scribe)
    {
        Description description;
        if (value == null)
        {
            description = NULL;
        }
        else if (value instanceof Number)
        {
            description = new NumberDescription((Number) value);
        }
        else if (value instanceof CharSequence)
        {
            description = new CharSequenceDescription((CharSequence) value);
        }
        else if (value instanceof Map.Entry)
        {
            description = new MapEntryDescription((Map.Entry<?, ?>) value);
        }
        else if (value instanceof Set)
        {
            description = new SetDescription((Set<?>) value);
        }
        else if (value instanceof Path)
        {
            // Path is an Iterable of Path, potentially resulting in an infinite loop, hence we treat it seprately here
            description = new ToStringDescription(value);
        }
        else if (value instanceof Iterable)
        {
            description = new IterableDescription((Iterable<?>) value);
        }
        else if (value instanceof Map)
        {
            description = new MapDescription((Map<?, ?>) value);
        }
        else if (value instanceof Optional)
        {
            description = new OptionalDescription((Optional<?>) value);
        }
        else if (value instanceof Description)
        {
            description = new DescriptionDescription((Description) value);
        }
        else if (value instanceof Pattern)
        {
            description = new PatternDescription((Pattern) value);
        }
        else if (value.getClass().isArray())
        {
            description = new IterableDescription(new ArrayIterable(value));
        }
        else
        {
            description = new ToStringDescription(value);
        }
        description.describeTo(scribe);
    }
}

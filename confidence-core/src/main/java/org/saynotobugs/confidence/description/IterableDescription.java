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

import org.dmfs.jems2.iterable.Mapped;
import org.saynotobugs.confidence.Description;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA_NEW_LINE;


/**
 * A {@link Description} of an {@link Iterable} value.
 */
public final class IterableDescription extends DescriptionComposition
{

    private static final Text ENTRY_SEQUENCE = new Text("[ ");
    private static final Text EXIT_SEQUENCE = new Text(" ]");


    public IterableDescription(Iterable<?> value)
    {
        super(new Structured(
            ENTRY_SEQUENCE,
            COMMA_NEW_LINE,
            EXIT_SEQUENCE,
            new Mapped<>(Value::new, value)));
    }
}

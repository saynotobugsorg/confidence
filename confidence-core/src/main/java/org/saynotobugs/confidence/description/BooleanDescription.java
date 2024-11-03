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


/**
 * The {@link Description} of a {@link Boolean} value.
 */
public final class BooleanDescription extends DescriptionComposition
{
    private final static Description TRUE = new Text(Boolean.TRUE.toString());
    private final static Description FALSE = new Text(Boolean.FALSE.toString());

    public BooleanDescription(boolean bool)
    {
        super(bool ? TRUE : FALSE);
    }
}

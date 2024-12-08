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

package org.saynotobugs.confidence.assessment;

import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


/**
 * An unconditional pass {@link Assessment}.
 */
public final class Pass implements Assessment
{
    private final Description mDescription;


    /**
     * @deprecated use {@link Pass#Pass(Description)} and provide a {@link Description}.
     */
    @Deprecated
    public Pass()
    {
        this(EMPTY);
    }

    public Pass(Description description)
    {
        mDescription = description;
    }

    @Override
    public boolean isSuccess()
    {
        return true;
    }


    @Override
    public Description description()
    {
        return mDescription;
    }
}

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

package org.saynotobugs.confidence.assessment;

import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;


/**
 * A {@link Assessment} that prepends any mismatch {@link Description}.
 *
 * @deprecated use {@link DescriptionUpdated} to update pass and fail description.
 */
@Deprecated
public final class FailPrepended extends AssessmentComposition
{
    public FailPrepended(Description prefix, Assessment delegate)
    {
        super(new FailUpdated(original -> new Spaced(prefix, original), delegate));
    }
}

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

import org.dmfs.jems2.Single;
import org.saynotobugs.confidence.Description;


public final class PassIf extends AssessmentComposition
{
    public PassIf(boolean result, Description passDescription, Description failDescription)
    {
        this(result, () -> passDescription, () -> failDescription);
    }


    public PassIf(boolean result, Single<Description> passDescription, Single<Description> failDescription)
    {
        super(result ? new Pass(passDescription.value()) : new Fail(failDescription.value()));
    }
}

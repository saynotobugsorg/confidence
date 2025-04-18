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

import org.dmfs.jems2.Function;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;


public final class DescriptionUpdated implements Assessment
{
    private final Function<? super Description, ? extends Description> mPassDescriptionFunction;
    private final Function<? super Description, ? extends Description> mFailDescriptionFunction;
    private final Assessment mDelegate;


    public DescriptionUpdated(Function<? super Description, ? extends Description> descriptionFunction,
        Assessment delegate)
    {
        this(descriptionFunction, descriptionFunction, delegate);
    }


    public DescriptionUpdated(Function<? super Description, ? extends Description> passDescriptionFunction,
        Function<? super Description, ? extends Description> failDescriptionFunction,
        Assessment delegate)
    {
        mPassDescriptionFunction = passDescriptionFunction;
        mFailDescriptionFunction = failDescriptionFunction;
        mDelegate = delegate;
    }


    @Override
    public boolean isSuccess()
    {
        return mDelegate.isSuccess();
    }


    @Override
    public Description description()
    {
        return isSuccess()
            ? mPassDescriptionFunction.value(mDelegate.description())
            : mFailDescriptionFunction.value(mDelegate.description());
    }

}

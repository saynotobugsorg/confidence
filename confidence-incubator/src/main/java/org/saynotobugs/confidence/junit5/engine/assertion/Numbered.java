/*
 * Copyright 2023 dmfs GmbH
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

package org.saynotobugs.confidence.junit5.engine.assertion;

import org.saynotobugs.confidence.junit5.engine.Assertion;

public final class Numbered implements Assertion
{
    private final int mNumber;
    private final Assertion mDelegate;

    public Numbered(int mNumber, Assertion mDelegate)
    {
        this.mNumber = mNumber;
        this.mDelegate = mDelegate;
    }

    @Override
    public void verify()
    {
        mDelegate.verify();
    }

    @Override
    public String name()
    {
        return "[" + mNumber + "] " + mDelegate.name();
    }
}

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

package org.saynotobugs.confidence.scribe;

import org.saynotobugs.confidence.Scribe;


public final class StringBuilderScribe implements Scribe
{
    private final StringBuilder mStringBuilder;
    private final String mCurrentLinePrefix;
    private final String mIndent;


    public StringBuilderScribe(String indent)
    {
        this(new StringBuilder(), "", indent);
    }


    private StringBuilderScribe(StringBuilder stringBuilder, String currentLinePrefix, String indent)
    {
        mStringBuilder = stringBuilder;
        mCurrentLinePrefix = currentLinePrefix;
        mIndent = indent;
    }


    @Override
    public Scribe indented()
    {
        return new StringBuilderScribe(mStringBuilder, mCurrentLinePrefix + mIndent, mIndent);
    }


    @Override
    public Scribe append(CharSequence charSequence)
    {
        mStringBuilder.append(charSequence);
        return this;
    }


    @Override
    public Scribe newLine()
    {
        mStringBuilder.append(System.lineSeparator());
        mStringBuilder.append(mCurrentLinePrefix);
        return this;
    }


    @Override
    public String toString()
    {
        return mStringBuilder.toString();
    }
}

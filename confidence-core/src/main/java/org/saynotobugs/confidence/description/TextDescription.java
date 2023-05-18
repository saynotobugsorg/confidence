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

package org.saynotobugs.confidence.description;

import org.dmfs.jems2.Single;
import org.saynotobugs.confidence.Description;


/**
 * A plain text {@link Description}.
 * <p>
 * Note that certain control characters and {@code \} will be escaped in the result.
 *
 * @deprecated in favour of {@link Text}
 */
@Deprecated
public final class TextDescription extends DescriptionComposition
{
    public TextDescription(CharSequence text)
    {
        this(text::toString);
    }


    public TextDescription(Single<String> text)
    {
        super(scribe -> scribe.append(text.value()
            .replace("\\", "\\\\")
            .replace("\t", "\\t")
            .replace("\b", "\\b")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\f", "\\f")
            .replace("\"", "\\\"")));
    }
}

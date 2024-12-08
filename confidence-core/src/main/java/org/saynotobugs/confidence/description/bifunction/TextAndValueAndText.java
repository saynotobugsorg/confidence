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

package org.saynotobugs.confidence.description.bifunction;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.function.DelegatingFunction;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;

public final class TextAndValueAndText<T> extends DelegatingFunction<T, Description>
    implements BiFunction<T, Description, Description>
{
    public TextAndValueAndText(String beforeText, String afterText)
    {
        this(new Text(beforeText), new Text(afterText));
    }

    public TextAndValueAndText(Description beforeDescription, Description afterDescription)
    {
        super((value) -> new Spaced(beforeDescription, new Value(value), afterDescription));
    }

    @Override
    public Description value(T t, Description description)
    {
        return value(t);
    }
}

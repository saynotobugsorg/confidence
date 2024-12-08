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

package org.saynotobugs.confidence.utils;

import org.dmfs.jems2.optional.LazyDelegatingOptional;
import org.dmfs.jems2.optional.Present;

import java.util.Iterator;

import static org.dmfs.jems2.optional.Absent.absent;

public final class Last<T> extends LazyDelegatingOptional<T>
{
    public Last(Iterable<? extends T> delegates)
    {
        super(() ->
            {
                Iterator<? extends T> iterator = delegates.iterator();
                while (iterator.hasNext())
                {
                    T next = iterator.next();
                    if (!iterator.hasNext())
                    {
                        return new Present<>(next);
                    }
                }
                return absent();
            }
        );
    }
}

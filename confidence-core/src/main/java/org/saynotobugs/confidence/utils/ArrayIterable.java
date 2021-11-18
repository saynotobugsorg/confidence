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

package org.saynotobugs.confidence.utils;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An Iterable to iterate any array.
 */
public final class ArrayIterable implements Iterable<Object>
{
    private final Object mArray;


    public ArrayIterable(Object array)
    {
        if (!array.getClass().isArray())
        {
            throw new RuntimeException(array + " is not an array");
        }
        mArray = array;
    }


    @Override
    public Iterator<Object> iterator()
    {
        return new Iterator<Object>()
        {
            private int mPos;


            @Override
            public boolean hasNext()
            {
                return mPos < Array.getLength(mArray);
            }


            @Override
            public Object next()
            {
                if (!hasNext())
                {
                    throw new NoSuchElementException();
                }
                return Array.get(mArray, mPos++);
            }
        };
    }
}

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

package org.saynotobugs.confidence.asm.quality;

import org.dmfs.jems2.Function;
import org.objectweb.asm.Type;

import java.lang.Class;

final class ClassFunction implements Function<Type, Class<?>>
{
    @Override
    public Class<?> value(Type type)
    {
        try
        {
            return ClassLoader
                .getSystemClassLoader()
                .loadClass(type.getClassName());
        }
        catch (ClassNotFoundException e)
        {
            throw new RuntimeException("Can't load class " + type.getClassName(), e);
        }
    }
}

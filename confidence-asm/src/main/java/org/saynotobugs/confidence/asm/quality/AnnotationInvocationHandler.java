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

package org.saynotobugs.confidence.asm.quality;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * The {@link Proxy} {@link InvocationHandler} of an {@link java.lang.annotation.Annotation}.
 */
final class AnnotationInvocationHandler implements InvocationHandler
{
    private final String mClassName;
    private final Map<String, Object> mParameter;

    public AnnotationInvocationHandler(String className, Map<String, Object> parameter)
    {
        mClassName = className;
        mParameter = parameter;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects)
    {
        if ("toString".equals(method.getName()))
        {
            // TODO: properly print array parameters
            return mClassName;
        }
        return mParameter.containsKey(method.getName()) ? mParameter.get(method.getName()) : method.getDefaultValue();
    }
}

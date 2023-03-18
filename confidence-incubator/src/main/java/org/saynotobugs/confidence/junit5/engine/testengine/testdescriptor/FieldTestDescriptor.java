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

package org.saynotobugs.confidence.junit5.engine.testengine.testdescriptor;

import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.saynotobugs.confidence.junit5.engine.testengine.Testable;
import org.saynotobugs.confidence.junit5.engine.Verifiable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


/**
 * A {@link TestDescriptor} for {@link Field}s.
 */
public final class FieldTestDescriptor extends AbstractTestDescriptor implements Testable
{
    private final Class<?> javaClass;
    private final Field javaField;


    public FieldTestDescriptor(UniqueId uniqueId, Class<?> javaClass, Field javaField)
    {
        super(uniqueId.append("field", javaField.getName()),
            (javaField.getName().replace("_", " ") + " " + assertion(javaClass, javaField).name()).trim(),
            ClassSource.from(javaClass));
        this.javaClass = javaClass;
        this.javaField = javaField;
    }


    @Override
    public void test(EngineExecutionListener listener)
    {
        assertion(javaClass, javaField).verify();
    }


    private static Verifiable assertion(Class<?> javaClass, Field javaField)
    {
        try
        {
            Constructor<?> constructor = javaClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();
            javaField.setAccessible(true);
            return ((Verifiable) javaField.get(instance));
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            throw new RuntimeException("Can't invoke test " + javaClass.getName() + "." + javaField.getName(), e);
        }
    }


    @Override
    public Type getType()
    {
        return Type.TEST;
    }
}

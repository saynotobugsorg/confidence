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

package org.saynotobugs.confidence.junit5.engine.testdescriptor;

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.procedure.ForEach;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.saynotobugs.confidence.junit5.engine.Testable;
import org.saynotobugs.confidence.junit5.engine.VerifiableGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;


/**
 * A {@link TestDescriptor} for {@link Field}s.
 */
public final class VerifiableGroupTestDescriptor extends AbstractTestDescriptor implements Testable
{
    private final Class<?> javaClass;
    private final Field javaField;


    public VerifiableGroupTestDescriptor(UniqueId uniqueId, Class<?> javaClass, Field javaField)
    {
        super(uniqueId.append("field", javaField.getName()),
            Optional.of(javaField.getName().replace("_", " ")).map(String::trim).filter(s -> s.length() > 0).orElse("TestGroup"),
            ClassSource.from(javaClass));

        new ForEach<>(new Mapped<>(assertion -> new AssertionTestDescriptor(uniqueId, getDisplayName(), assertion), assertions(javaClass, javaField)))
            .process(this::addChild);

        this.javaClass = javaClass;
        this.javaField = javaField;
    }


    @Override
    public void test(EngineExecutionListener listener)
    {
        children.forEach(
            descriptor -> {
                listener.executionStarted(descriptor);
                try
                {
                    ((Testable) descriptor).test(listener);
                    listener.executionFinished(descriptor, TestExecutionResult.successful());
                }
                catch (AssertionError e)
                {
                    listener.executionFinished(descriptor, TestExecutionResult.failed(e));
                }
            }
        );
    }


    private static VerifiableGroup assertions(Class<?> javaClass, Field javaField)
    {
        try
        {
            Constructor<?> constructor = javaClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object instance = constructor.newInstance();
            javaField.setAccessible(true);
            return ((VerifiableGroup) javaField.get(instance));
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            throw new RuntimeException("Can't invoke test " + javaClass.getName() + "." + javaField.getName(), e);
        }
    }


    @Override
    public Type getType()
    {
        return Type.CONTAINER;
    }
}

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

import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.saynotobugs.confidence.junit5.engine.Testable;
import org.saynotobugs.confidence.junit5.engine.Verifiable;
import org.saynotobugs.confidence.junit5.engine.VerifiableGroup;

import java.lang.reflect.Array;
import java.util.Arrays;


/**
 * A {@link TestDescriptor} for container test classes.
 */
public final class ClassTestDescriptor extends AbstractTestDescriptor implements Testable
{
    public ClassTestDescriptor(UniqueId uniqueId, Class<?> javaClass)
    {
        super(uniqueId.append("class", javaClass.getName()),
            javaClass.getSimpleName(),
            ClassSource.from(javaClass));

        Arrays.stream(javaClass.getDeclaredFields())
            .filter(field -> Verifiable.class.isAssignableFrom(field.getType()))
            .map(field -> new FieldTestDescriptor(getUniqueId(), javaClass, field))
            .forEach(this::addChild);

        Arrays.stream(javaClass.getDeclaredFields())
            .filter(field -> Array.newInstance(Verifiable.class, 0).getClass().isAssignableFrom(field.getType()))
            .map(field -> new AssertionArrayTestDescriptor(getUniqueId(), javaClass, field))
            .forEach(this::addChild);

        Arrays.stream(javaClass.getDeclaredFields())
            .filter(field -> VerifiableGroup.class.isAssignableFrom(field.getType()))
            .map(field -> new VerifiableGroupTestDescriptor(getUniqueId(), javaClass, field))
            .forEach(this::addChild);
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


    @Override
    public Type getType()
    {
        return Type.CONTAINER;
    }
}

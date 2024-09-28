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

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.FilePosition;
import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.testengine.Testable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;


/**
 * A {@link TestDescriptor} for {@link Field}s.
 */
public final class FieldTestDescriptor extends AbstractTestDescriptor implements Testable
{
    private final Class<?> javaClass;
    private final Field javaField;


    public FieldTestDescriptor(UniqueId uniqueId, Class<?> javaClass, Optional<CompilationUnit> testSource, Field javaField)
    {
        super(uniqueId.append("field", javaField.getName()),
            (javaField.getName().replace("_", " ")).trim(),
            ClassSource.from(javaClass, getFieldLineNumber(testSource, javaField.getName())));
        this.javaClass = javaClass;
        this.javaField = javaField;
    }


    @Override
    public void test(EngineExecutionListener listener)
    {
        Object instance;
        try
        {
            Constructor<?> constructor = javaClass.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
            javaField.setAccessible(true);
            ((Assertion) javaField.get(instance)).verify();
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


    private static FilePosition getFieldLineNumber(Optional<CompilationUnit> compilationUnit, String fieldName)
    {
        return compilationUnit.flatMap(cu -> cu.findAll(FieldDeclaration.class)
                .stream().flatMap(fieldDeclaration -> fieldDeclaration.getVariables().stream())
                .filter(node -> fieldName.equals(node.getName().asString()))
                .flatMap(node -> node.getBegin().stream())
                .map(begin -> FilePosition.from(begin.line, begin.column))
                .findFirst())
            .orElse(null);
    }
}

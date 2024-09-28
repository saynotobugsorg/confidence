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

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.testengine.Testable;
import org.saynotobugs.confidence.utils.FailSafe;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;


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
            .filter(field -> Assertion.class.isAssignableFrom(field.getType()))
            .map(field -> new FieldTestDescriptor(getUniqueId(), javaClass, testSources(javaClass), field))
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
                catch (Throwable e)
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


    private static Optional<CompilationUnit> testSources(Class<?> clazz)
    {
        return gradleTestSourcePath(clazz)
            .or(() -> naiveTestSourcePath(clazz))
            .flatMap(new FailSafe<>(any -> empty(), path -> new JavaParser().parse(path).getResult()));
    }

    private static Optional<Path> naiveTestSourcePath(Class<?> testClazz)
    {
        return Optional.of(Path.of("src", "main")
                .resolve(Path.of("java", testClazz.getPackage().getName().split("\\.")))
                .resolve(testClazz.getSimpleName() + ".java"))
            .filter(Files::isRegularFile);
    }

    private static Optional<Path> gradleTestSourcePath(Class<?> testClazz)
    {
        return ofNullable(System.getProperty("user.dir"))
            .map(Path::of)
            .flatMap(workingDir ->
                Optional.ofNullable(testClazz.getProtectionDomain().getCodeSource())
                    .map(CodeSource::getLocation)
                    .flatMap(new FailSafe<>(any -> empty(), url -> Optional.of(url.toURI())))
                    .map(source -> workingDir.relativize(Path.of(source)))
                    .map(relativeClassPath ->
                        workingDir.resolve(Path.of("src", relativeClassPath.getName(3).toString())
                            .resolve(Path.of(relativeClassPath.getName(2).toString(), testClazz.getPackageName().split("\\.")))
                            .resolve(testClazz.getSimpleName() + ".java")))
                    .filter(Files::isRegularFile));
    }

}

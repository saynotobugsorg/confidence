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

package org.saynotobugs.confidence.junit5.engine.testengine;

import org.junit.platform.commons.support.AnnotationSupport;
import org.junit.platform.engine.*;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.saynotobugs.confidence.junit5.engine.Testable;
import org.saynotobugs.confidence.junit5.engine.Verifiable;
import org.saynotobugs.confidence.junit5.engine.testdescriptor.ClassTestDescriptor;


public final class ConfidenceEngine implements TestEngine
{

    @Override
    public String getId()
    {
        return "ConfidenceTest";
    }


    @Override
    public TestDescriptor discover(EngineDiscoveryRequest request, UniqueId uniqueId)
    {
        TestDescriptor testDescriptor = new EngineDescriptor(uniqueId, "Test with Confidence");

        request.getSelectorsByType(ClassSelector.class)
            .forEach(selector -> AnnotationSupport.findAnnotatedFields(selector.getJavaClass(), Verifiable.class)
                .stream()
                .findFirst()
                .ifPresent(field -> testDescriptor.addChild(new ClassTestDescriptor(uniqueId, selector.getJavaClass()))));
        return testDescriptor;
    }


    @Override
    public void execute(ExecutionRequest request)
    {
        TestDescriptor engineDescriptor = request.getRootTestDescriptor();
        EngineExecutionListener listener = request.getEngineExecutionListener();
        for (TestDescriptor testDescriptor : engineDescriptor.getChildren())
        {
            if (testDescriptor instanceof Testable)
            {
                // cast it to our own class
                Testable descriptor = (Testable) testDescriptor;
                listener.executionStarted(testDescriptor);
                // here you would add your super-complicated logic of how to actually run the test
                try
                {
                    descriptor.test(listener);
                    listener.executionFinished(testDescriptor, TestExecutionResult.successful());
                }
                catch (Exception e)
                {
                    listener.executionFinished(testDescriptor, TestExecutionResult.failed(e));
                }
            }
        }
    }
}

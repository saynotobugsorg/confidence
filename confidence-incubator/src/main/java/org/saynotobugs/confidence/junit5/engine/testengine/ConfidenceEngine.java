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

import org.junit.platform.engine.*;
import org.junit.platform.engine.discovery.ClassSelector;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.saynotobugs.confidence.junit5.engine.Confidence;
import org.saynotobugs.confidence.junit5.engine.testengine.testdescriptor.ClassTestDescriptor;


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
            .stream()
            .filter(classSelector -> classSelector.getJavaClass().isAnnotationPresent(Confidence.class))
            .forEach(selector -> testDescriptor.addChild(new ClassTestDescriptor(uniqueId, selector.getJavaClass())));
        return testDescriptor;
    }


    @Override
    public void execute(ExecutionRequest request)
    {
        EngineExecutionListener listener = request.getEngineExecutionListener();
        for (TestDescriptor testDescriptor : request.getRootTestDescriptor().getChildren())
        {
            if (testDescriptor instanceof Testable)
            {
                Testable descriptor = (Testable) testDescriptor;
                listener.executionStarted(testDescriptor);
                try
                {
                    descriptor.test(listener);
                    listener.executionFinished(testDescriptor, TestExecutionResult.successful());
                }
                catch (Throwable e)
                {
                    listener.executionFinished(testDescriptor, TestExecutionResult.failed(e));
                }
            }
        }
    }
}

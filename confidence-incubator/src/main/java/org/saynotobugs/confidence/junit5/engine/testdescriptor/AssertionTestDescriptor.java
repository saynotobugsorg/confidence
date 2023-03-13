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
import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.Testable;

import java.lang.reflect.Field;
import java.util.Optional;


/**
 * A {@link TestDescriptor} for {@link Field}s.
 */
public final class AssertionTestDescriptor extends AbstractTestDescriptor implements Testable
{
    private final Assertion mAssertion;


    public AssertionTestDescriptor(UniqueId uniqueId, String nameBase, Assertion assertion)
    {
        super(uniqueId.append("assertion", String.valueOf(assertion.hashCode())),
            nameBase + " " + assertion.name().trim());
        this.mAssertion = assertion;
    }


    @Override
    public void test(EngineExecutionListener listener)
    {
        mAssertion.verify();
    }


    @Override
    public Type getType()
    {
        return Type.TEST;
    }
}

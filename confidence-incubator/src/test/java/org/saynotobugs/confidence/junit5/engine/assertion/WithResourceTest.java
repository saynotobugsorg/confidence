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

package org.saynotobugs.confidence.junit5.engine.assertion;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.Function;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.Resource;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.object.Successfully;
import org.saynotobugs.confidence.quality.object.Throwing;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.saynotobugs.confidence.Assertion.assertThat;


class WithResourceTest
{
    @Test
    void testPassSingleResource() throws Exception
    {
        Object resourceDummy = new Object();
        Resource<Object> mockResource = mock(Resource.class,
            with(Resource::value, returning(resourceDummy)),
            withVoid(Resource::close, doingNothing()));

        assertThat(new WithResource(mockResource,
                mock(Function.class,
                    with(f -> f.value(resourceDummy), returning(mock(Assertion.class,
                        withVoid(Assertion::verify, doingNothing())))))),
            new Successfully<>(new Text("Verifies"), new Text("Failed"), Assertion::verify)
        );

        verify(mockResource).close();
    }


    @Test
    void testFailSingleResource() throws Exception
    {
        Object resourceDummy = new Object();
        Resource<Object> mockResource = mock(Resource.class,
            with(Resource::value, returning(resourceDummy)),
            withVoid(Resource::close, doingNothing()));

        assertThat(new WithResource(mockResource,
                mock(Function.class,
                    with(f -> f.value(any()), returning(mock(Assertion.class,
                        withVoid(Assertion::verify, doingNothing())))),
                    with(f -> f.value(resourceDummy), returning(mock(Assertion.class,
                        withVoid(Assertion::verify, throwing(new AssertionError("failed")))))))),
            new Has<>("failing", verifiable -> verifiable::verify, new Throwing(AssertionError.class))
        );

        verify(mockResource).close();
    }


    @Test
    void testPassDoubleResource() throws Exception
    {
        Object resourceDummy1 = new Object();
        Object resourceDummy2 = new Object();
        Resource<Object> mockResource1 = mock(Resource.class,
            with(Resource::value, returning(resourceDummy1)),
            withVoid(Resource::close, doingNothing()));
        Resource<Object> mockResource2 = mock(Resource.class,
            with(Resource::value, returning(resourceDummy2)),
            withVoid(Resource::close, doingNothing()));

        assertThat(new WithResource(mockResource1, mockResource2,
                mock(BiFunction.class,
                    with(f -> f.value(resourceDummy1, resourceDummy2), returning(mock(Assertion.class,
                        withVoid(Assertion::verify, doingNothing())))))),
            new Successfully<>(new Text("Verifies"), new Text("Failed"), Assertion::verify)
        );

        verify(mockResource1).close();
        verify(mockResource2).close();
    }


    @Test
    void testFailDoubleResource() throws Exception
    {
        Object resourceDummy1 = new Object();
        Object resourceDummy2 = new Object();
        Resource<Object> mockResource1 = mock(Resource.class,
            with(Resource::value, returning(resourceDummy1)),
            withVoid(Resource::close, doingNothing()));
        Resource<Object> mockResource2 = mock(Resource.class,
            with(Resource::value, returning(resourceDummy2)),
            withVoid(Resource::close, doingNothing()));

        assertThat(new WithResource(mockResource1, mockResource2,
                mock(BiFunction.class,
                    with(f -> f.value(any(), any()), returning(mock(Assertion.class,
                        withVoid(Assertion::verify, doingNothing())))),
                    with(f -> f.value(resourceDummy1, resourceDummy2), returning(mock(Assertion.class,
                        withVoid(Assertion::verify, throwing(new AssertionError("failed")))))))),
            new Has<>("failing", verifiable -> verifiable::verify, new Throwing(AssertionError.class))
        );

        verify(mockResource1).close();
        verify(mockResource2).close();
    }

}
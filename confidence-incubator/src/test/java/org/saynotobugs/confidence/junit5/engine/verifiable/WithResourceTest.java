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

package org.saynotobugs.confidence.junit5.engine.verifiable;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.Function;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.junit5.engine.Verifiable;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.quality.object.Successfully;
import org.saynotobugs.confidence.quality.object.Throwing;

import java.io.IOException;

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
        WithResource.Resource<Object> mockResource = mock(WithResource.Resource.class,
            with(WithResource.Resource::value, returning(resourceDummy)),
            withVoid(WithResource.Resource::close, doingNothing()));

        assertThat(new WithResource(() -> mockResource,
                mock(Function.class,
                    with(f -> f.value(resourceDummy), returning(mock(Verifiable.class,
                        withVoid(Verifiable::verify, doingNothing())))))),
            new AllOf<>(
                new Successfully<>(new TextDescription("Verifies"), new TextDescription("Failed"), Verifiable::verify),
                new Has<>("name", Verifiable::name, new EqualTo<>(""))
            )
        );

        verify(mockResource).close();
    }


    @Test
    void testFailSingleResource() throws Exception
    {
        Object resourceDummy = new Object();
        WithResource.Resource<Object> mockResource = mock(WithResource.Resource.class,
            with(WithResource.Resource::value, returning(resourceDummy)),
            withVoid(WithResource.Resource::close, doingNothing()));

        assertThat(new WithResource(() -> mockResource,
                mock(Function.class,
                    with(f -> f.value(any()), returning(mock(Verifiable.class,
                        withVoid(Verifiable::verify, doingNothing())))),
                    with(f -> f.value(resourceDummy), returning(mock(Verifiable.class,
                        withVoid(Verifiable::verify, throwing(new AssertionError("failed")))))))),
            new AllOf<>(
                new Has<>("failing", verifiable -> verifiable::verify, new Throwing(AssertionError.class)),
                new Has<>("name", Verifiable::name, new EqualTo<>(""))
            )
        );

        verify(mockResource).close();
    }


    @Test
    void testFailToCreateResource() throws Exception
    {
        assertThat(new WithResource(() -> {throw new IOException();},
                mock(Function.class,
                    with(f -> f.value(any()), returning(mock(Verifiable.class,
                        withVoid(Verifiable::verify, doingNothing())))))),
            new AllOf<>(
                new Has<>("failing", verifiable -> verifiable::verify, new Throwing(RuntimeException.class)),
                new Has<>("name", Verifiable::name, new EqualTo<>(""))
            )
        );
    }


    @Test
    void testPassDoubleResource() throws Exception
    {
        Object resourceDummy1 = new Object();
        Object resourceDummy2 = new Object();
        WithResource.Resource<Object> mockResource1 = mock(WithResource.Resource.class,
            with(WithResource.Resource::value, returning(resourceDummy1)),
            withVoid(WithResource.Resource::close, doingNothing()));
        WithResource.Resource<Object> mockResource2 = mock(WithResource.Resource.class,
            with(WithResource.Resource::value, returning(resourceDummy2)),
            withVoid(WithResource.Resource::close, doingNothing()));

        assertThat(new WithResource(() -> mockResource1, () -> mockResource2,
                mock(BiFunction.class,
                    with(f -> f.value(resourceDummy1, resourceDummy2), returning(mock(Verifiable.class,
                        withVoid(Verifiable::verify, doingNothing())))))),
            new AllOf<>(
                new Successfully<>(new TextDescription("Verifies"), new TextDescription("Failed"), Verifiable::verify),
                new Has<>("name", Verifiable::name, new EqualTo<>(""))
            )
        );

        verify(mockResource1).close();
        verify(mockResource2).close();
    }


    @Test
    void testFailDoubleResource() throws Exception
    {
        Object resourceDummy1 = new Object();
        Object resourceDummy2 = new Object();
        WithResource.Resource<Object> mockResource1 = mock(WithResource.Resource.class,
            with(WithResource.Resource::value, returning(resourceDummy1)),
            withVoid(WithResource.Resource::close, doingNothing()));
        WithResource.Resource<Object> mockResource2 = mock(WithResource.Resource.class,
            with(WithResource.Resource::value, returning(resourceDummy2)),
            withVoid(WithResource.Resource::close, doingNothing()));

        assertThat(new WithResource(() -> mockResource1, () -> mockResource2,
                mock(BiFunction.class,
                    with(f -> f.value(any(), any()), returning(mock(Verifiable.class,
                        withVoid(Verifiable::verify, doingNothing())))),
                    with(f -> f.value(resourceDummy1, resourceDummy2), returning(mock(Verifiable.class,
                        withVoid(Verifiable::verify, throwing(new AssertionError("failed")))))))),
            new AllOf<>(
                new Has<>("failing", verifiable -> verifiable::verify, new Throwing(AssertionError.class)),
                new Has<>("name", Verifiable::name, new EqualTo<>(""))
            )
        );

        verify(mockResource1).close();
        verify(mockResource2).close();
    }


    @Test
    void testFailToCreateDoubleResource() throws Exception
    {
        Object resourceDummy1 = new Object();
        WithResource.Resource<Object> mockResource1 = mock(WithResource.Resource.class,
            with(WithResource.Resource::value, returning(resourceDummy1)),
            withVoid(WithResource.Resource::close, doingNothing()));

        assertThat(new WithResource(() -> mockResource1, () -> {throw new IOException();},
                mock(BiFunction.class,
                    with(f -> f.value(any(), any()), returning(mock(Verifiable.class,
                        withVoid(Verifiable::verify, doingNothing())))))),
            new AllOf<>(
                new Has<>("failing", verifiable -> verifiable::verify, new Throwing(RuntimeException.class)),
                new Has<>("name", Verifiable::name, new EqualTo<>(""))
            )
        );
        verify(mockResource1).close();
    }

}
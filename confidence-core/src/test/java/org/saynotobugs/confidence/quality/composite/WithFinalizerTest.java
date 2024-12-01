/*
 * Copyright 2022 dmfs GmbH
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

package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.object.SameAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.mockito.Mockito.verify;
import static org.saynotobugs.confidence.Assertion.assertThat;


class WithFinalizerTest
{

    @Test
    void testPass() throws IOException
    {
        Closeable mockClosable = mock(Closeable.class,
            withVoid(Closeable::close, doingNothing()),
            with(Objects::toString, returning("bar")));
        assertThat(new WithFinalizer<>(Closeable::close, new Text("Closable that"), new SameAs<>(mockClosable)),
            new AllOf<>(
                new Passes<>(mockClosable, "Closable that\n  <bar> and\n  was finalized"),
                new HasDescription("Closable that same instance as <bar> and is finalized")
            ));
        verify(mockClosable).close();
    }


    @Test
    void testFail() throws IOException
    {
        Closeable mockClosable = mock(Closeable.class,
            withVoid(Closeable::close, doingNothing()),
            with(Objects::toString, returning("bar")));
        assertThat(new WithFinalizer<>(Closeable::close, new Text("Closable that"), new Not<>(new SameAs<>(mockClosable))),
            new AllOf<>(
                new Fails<>(mockClosable, "Closable that\n  <bar> and\n  ..."),
                new HasDescription("Closable that not same instance as <bar> and is finalized")
            ));
        verify(mockClosable).close();
    }


    @Test
    void testFailClose() throws IOException
    {
        Closeable mockClosable = mock(Closeable.class,
            withVoid(Closeable::close, throwing(new IOException("error"))),
            with(Objects::toString, returning("bar")));
        assertThat(new WithFinalizer<>(Closeable::close, new Text("Closable that"), new SameAs<>(mockClosable)),
            new AllOf<>(
                new Fails<>(mockClosable, "Closable that\n  ... and\n  was throwing <java.io.IOException: error>"),
                new HasDescription("Closable that same instance as <bar> and is finalized")
            ));
        verify(mockClosable).close();
    }


    @Test
    void testFailAndFailClose() throws IOException
    {
        Closeable mockClosable = mock(Closeable.class,
            withVoid(Closeable::close, throwing(new IOException("error"))),
            with(Objects::toString, returning("bar")));
        assertThat(new WithFinalizer<>(Closeable::close, new Text("Closable that"), new Not<>(new SameAs<>(mockClosable))),
            new AllOf<>(
                new Fails<>(mockClosable, "Closable that\n  <bar> and\n  was throwing <java.io.IOException: error>"),
                new HasDescription("Closable that not same instance as <bar> and is finalized")
            ));
        verify(mockClosable).close();
    }
}

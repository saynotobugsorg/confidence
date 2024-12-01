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

package org.saynotobugs.confidence.quality.autoclosable;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Not;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.SameAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.util.Objects;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;


class AutoClosableThatTest
{

    @Test
    void testPass()
    {
        AutoCloseable autoCloseable = mock(AutoCloseable.class,
            withVoid(AutoCloseable::close, doingNothing()),
            with(Objects::toString, returning("foo")));
        assertThat(new AutoClosableThat<>(new Is<>(new SameAs<>(autoCloseable))),
            new AllOf<>(
                new Passes<>(autoCloseable, "AutoClosable that\n  was <foo> and\n  was closed"),
                new HasDescription("AutoClosable that is same instance as <foo> and is closed")
            ));
    }


    @Test
    void testFail()
    {
        AutoCloseable autoCloseable = mock(AutoCloseable.class,
            withVoid(AutoCloseable::close, doingNothing()),
            with(Objects::toString, returning("foo")));
        assertThat(new AutoClosableThat<>(new Is<>(new Not<>(new SameAs<>(autoCloseable)))),
            new AllOf<>(
                new Fails<>(autoCloseable, "AutoClosable that\n  was <foo> and\n  ..."),
                new HasDescription("AutoClosable that is not same instance as <foo> and is closed")
            ));
    }


    @Test
    void testFailClose()
    {
        AutoCloseable autoCloseable = mock(AutoCloseable.class,
            withVoid(AutoCloseable::close, throwing(new IOException())),
            with(Objects::toString, returning("foo")));
        assertThat(new AutoClosableThat<>(new Is<>(new SameAs<>(autoCloseable))),
            new AllOf<>(
                new Fails<>(autoCloseable, "AutoClosable that\n  ... and\n  was closed throwing <java.io.IOException>"),
                new HasDescription("AutoClosable that is same instance as <foo> and is closed")
            ));
    }


    @Test
    void testFailAndFailClose()
    {
        AutoCloseable autoCloseable = mock(AutoCloseable.class,
            withVoid(AutoCloseable::close, throwing(new IOException())),
            with(Objects::toString, returning("foo")));
        assertThat(new AutoClosableThat<>(new Is<>(new Not<>(new SameAs<>(autoCloseable)))),
            new AllOf<>(
                new Fails<>(autoCloseable, "AutoClosable that\n  was <foo> and\n  was closed throwing <java.io.IOException>"),
                new HasDescription("AutoClosable that is not same instance as <foo> and is closed")
            ));
    }
}
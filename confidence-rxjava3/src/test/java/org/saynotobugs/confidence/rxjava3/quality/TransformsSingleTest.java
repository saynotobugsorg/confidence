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

package org.saynotobugs.confidence.rxjava3.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.procedure.Emit;
import org.saynotobugs.confidence.rxjava3.procedure.Error;
import org.saynotobugs.confidence.rxjava3.rxexpectation.CompletesWith;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Errors;
import org.saynotobugs.confidence.rxjava3.transformerteststep.Downstream;
import org.saynotobugs.confidence.rxjava3.transformerteststep.Upstream;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Single;

import static org.saynotobugs.confidence.Assertion.assertThat;


class TransformsSingleTest
{

    @Test
    void testComplete()
    {
        assertThat(new TransformsSingle<>(new Upstream<>(new Emit<>(123)), new Downstream<>(new CompletesWith<>(123))),
            new AllOf<>(
                new Passes<>(scheduler -> Single::hide),
                new Fails<>(scheduler -> upsteam -> upsteam.ambWith(Single.error(new IOException())),
                    "(0) { completed <0> times\n  and\n  emitted <0> items that iterated [ 0: missing <123> ]\n  ... }"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS),
                    "(0) { completed <0> times\n  and\n  emitted <0> items that iterated [ 0: missing <123> ]\n  ... }"),
                new HasDescription(
                    "SingleTransformer that (0) completes exactly once\n    and\n    emits <1> items that iterates [ 0: <123> ]\n    and\n    emits nothing")
            ));
    }


    @Test
    void testDownstreamError()
    {
        assertThat(new TransformsSingle<Integer, Integer>(new Upstream<>(new Emit<>(123)), new Downstream<>(new Errors<>(IOException.class))),
            new AllOf<>(
                new Passes<>(scheduler -> upsteam -> upsteam.ambWith(Single.error(new IOException()))),
                new Fails<>(scheduler -> Single::hide, "(0) had error iterated [ 0: missing instance of <class java.io.IOException> ]"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS),
                    "(0) had error iterated [ 0: missing instance of <class java.io.IOException> ]"),
                new HasDescription("SingleTransformer that (0) has error iterates [ 0: instance of <class java.io.IOException> ]")
            ));
    }


    @Test
    void testUpstreamError()
    {
        assertThat(new TransformsSingle<Integer, Integer>(new Upstream<>(new Error<>(new IOException())), new Downstream<>(new CompletesWith<>(123))),
            new AllOf<>(
                new Passes<>(scheduler -> upsteam -> upsteam.onErrorReturnItem(123)),
                new Fails<>(scheduler -> Single::hide, "(0) { completed <0> times\n  and\n  emitted <0> items that iterated [ 0: missing <123> ]\n  ... }"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS),
                    "(0) { completed <0> times\n  and\n  emitted <0> items that iterated [ 0: missing <123> ]\n  ... }"),
                new HasDescription(
                    "SingleTransformer that (0) completes exactly once\n    and\n    emits <1> items that iterates [ 0: <123> ]\n    and\n    emits nothing")
            ));
    }
}
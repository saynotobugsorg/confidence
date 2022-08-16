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

import static org.junit.jupiter.api.Assertions.*;
import static org.saynotobugs.confidence.Assertion.assertThat;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.procedure.Complete;
import org.saynotobugs.confidence.rxjava3.rxexpectation.Completes;
import org.saynotobugs.confidence.rxjava3.transformerteststep.Downstream;
import org.saynotobugs.confidence.rxjava3.transformerteststep.Upstream;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;


class TransformsFlowableTest
{

    @Test
    void test()
    {
        assertThat(new TransformsFlowable<>(new Upstream<>(new Complete<>()), new Downstream<>(new Completes<>())),
            new AllOf<>(
                new Passes<>(scheduler -> Flowable::hide),
                new Fails<>(scheduler -> upsteam -> upsteam.ambWith(Flowable.error(new IOException())), "(0) { completed <0> times\n  ... }"),
                new Fails<>(scheduler -> upsteam -> upsteam.delay(10, TimeUnit.SECONDS), "(0) { completed <0> times\n  ... }"),
                new HasDescription("FlowableTransformer that (0) completes exactly once\n    and\n    emits nothing")
            ));
    }
}
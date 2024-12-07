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

package org.saynotobugs.confidence.rxjava3.procedure;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.saynotobugs.confidence.Assertion.assertThat;


class CompleteTest
{
    @Test
    void test()
    {
        assertThat(new Complete(),
            new AllOf<>(
                new Passes<RxSubjectAdapter<Object>>(
                    mock(RxSubjectAdapter.class, withVoid(RxSubjectAdapter::onComplete, doingNothing())), "completion"),
                new Fails<RxSubjectAdapter<Object>>(
                    mock(RxSubjectAdapter.class, withVoid(RxSubjectAdapter::onComplete, throwing(new RuntimeException("fail")))),
                    "completion failed with <java.lang.RuntimeException: fail>"),
                new HasDescription("completion")
            ));
    }
}
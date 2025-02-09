/*
 * Copyright 2024 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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


class EmitTest
{
    @Test
    void test()
    {
        assertThat(new Emit<>(1, 2, 3),
            new AllOf<>(
                new Passes<RxSubjectAdapter<Object>>(
                    mock(RxSubjectAdapter.class, withVoid(adapter -> adapter.onNext(1), doingNothing()),
                        withVoid(adapter -> adapter.onNext(2), doingNothing()),
                        withVoid(adapter -> adapter.onNext(3), doingNothing()), with(Object::toString, returning("klklklk"))
                    ),
                    "emissions [1,2,3]"
                ),
                new Fails<RxSubjectAdapter<Object>>(mock(RxSubjectAdapter.class, withVoid(adapter -> adapter.onNext(1), doingNothing()),
                    withVoid(adapter -> adapter.onNext(2), throwing(new RuntimeException("fail"))),
                    withVoid(adapter -> adapter.onNext(3), doingNothing()),
                    with(Object::toString, returning("opopopo"))),
                    "emissions [1,2,3] failed with <java.lang.RuntimeException: fail>"),
                new HasDescription("emissions [1,2,3]")
            ));
    }
}
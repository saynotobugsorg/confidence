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

package org.saynotobugs.confidence.rxjava3.adapters;

import io.reactivex.rxjava3.subjects.PublishSubject;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Not;
import org.saynotobugs.confidence.quality.function.MutatesArgument;
import org.saynotobugs.confidence.quality.grammar.SoIt;
import org.saynotobugs.confidence.quality.grammar.When;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.Satisfies;
import org.saynotobugs.confidence.quality.object.Successfully;

import static org.saynotobugs.confidence.Assertion.assertThat;

class RxTestObserverTest
{
    @Test
    void testDispose()
    {
        assertThat((PublishSubject<Object> observable) -> {
                RxTestObserver<Object> testObserver = new RxTestObserver<>();
                observable.subscribeWith(testObserver);
                return testObserver;
            },
            new AllOf<>(
                // first assert the function above actually subscribes
                new MutatesArgument<>(
                    PublishSubject::create,
                    new SoIt<>(new Satisfies<>(PublishSubject::hasObservers, new Text("hasObserver"))),
                    new When<>(new Anything())),
                new MutatesArgument<>(
                    PublishSubject::create,
                    new SoIt<>(new Not<>(new Satisfies<>(PublishSubject::hasObservers, new Text("hasObserver")))),
                    new When<>(new Successfully<>(new Text("Cancelled"), new Text("Cancelled"), new Text("Cancelled"), (RxTestObserver<Object> o) -> o.cancel()))
                ))
        );
    }

}
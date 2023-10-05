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

package org.saynotobugs.confidence.junit5.engine.assertions;

import org.dmfs.jems2.Function;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Numbered;
import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.Assertions;

public final class Parametrized<T> implements Assertions
{
    private final Iterable<? extends T> parameters;
    public final Function<? super T, ? extends Assertion> assertionFunction;

    public Parametrized(
        Iterable<? extends T> parameters,
        Function<? super T, ? extends Assertion> assertionFunction)
    {
        this.parameters = parameters;
        this.assertionFunction = assertionFunction;
    }

    @Override
    public Iterable<Assertion> assertions()
    {
        return new Mapped<>(
            param -> new org.saynotobugs.confidence.junit5.engine.assertion.Numbered(param.left(), assertionFunction.value(param.right())),
            new Numbered<>(parameters, 1));
    }
}

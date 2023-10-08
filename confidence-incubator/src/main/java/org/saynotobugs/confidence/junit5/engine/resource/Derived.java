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

package org.saynotobugs.confidence.junit5.engine.resource;

import org.dmfs.jems2.FragileBiFunction;
import org.dmfs.jems2.FragileFunction;
import org.dmfs.jems2.FragileProcedure;
import org.dmfs.jems2.pair.ValuePair;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.junit5.engine.Resource;
import org.saynotobugs.confidence.junit5.engine.ResourceComposition;
import org.saynotobugs.confidence.junit5.engine.ResourceHandle;

@StaticFactories(value = "Resources", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class Derived<Derivate> extends ResourceComposition<Derivate>
{
    /**
     * Derivate of an existing {@link Resource}. The resource is derived by applying the given {@link FragileFunction}
     * on the original {@link Resource} value.
     * <p>
     * Use this constructor when the derived {@link Resource} does not need a separate clean-up procedure.
     */
    public <Original> Derived(
        FragileFunction<? super Original, ? extends Derivate, Exception> derivationFunction,
        Resource<Original> delegate)
    {
        this(derivationFunction, delegate, result -> {});
    }

    /**
     * Derivate of an existing {@link Resource}. The resource is derived by applying the given {@link FragileFunction}
     * on the original {@link Resource} value.
     * <p>
     * The given clean-up procedure is executed when the resource is no longer needed.
     */
    public <Original> Derived(
        FragileFunction<? super Original, ? extends Derivate, Exception> derivationFunction,
        Resource<Original> delegate,
        FragileProcedure<Derivate, Exception> cleanUp)
    {
        super(new LazyResource<>(
            delegate,
            handle -> derivationFunction.value(handle.value()),
            (derivate, original) -> {
                try (ResourceHandle<Original> o = original)
                {
                    cleanUp.process(derivate);
                }
            }));
    }

    /**
     * Derivate of two existing {@link Resource}. The resource is derived by applying the given {@link FragileBiFunction}
     * on the original {@link Resource} values.
     * <p>
     * Use this constructor when the derived {@link Resource} does not need a separate clean-up procedure.
     */
    public <Original1, Original2> Derived(
        FragileBiFunction<? super Original1, ? super Original2, ? extends Derivate, Exception> derivationFunction,
        Resource<Original1> delegate1,
        Resource<Original2> delegate2)
    {
        this(derivationFunction, delegate1, delegate2, result -> {});
    }

    /**
     * Derivate of two existing {@link Resource}s. The resource is derived by applying the given {@link FragileBiFunction}
     * on the original {@link Resource} values.
     * <p>
     * The given clean-up procedure is executed when the resource is no longer needed.
     */
    public <Original1, Original2> Derived(
        FragileBiFunction<? super Original1, ? super Original2, ? extends Derivate, Exception> derivationFunction,
        Resource<Original1> delegate1,
        Resource<Original2> delegate2,
        FragileProcedure<Derivate, Exception> cleanUp)
    {
        super(new LazyResource<>(
            () -> new ValuePair<>(delegate1.value(), delegate2.value()),
            handlePair -> derivationFunction.value(handlePair.left().value(), handlePair.right().value()),
            (derivate, handlePair) -> {
                try (ResourceHandle<Original1> o1 = handlePair.left(); ResourceHandle<Original2> o2 = handlePair.right())
                {
                    cleanUp.process(derivate);
                }
            }));
    }
}

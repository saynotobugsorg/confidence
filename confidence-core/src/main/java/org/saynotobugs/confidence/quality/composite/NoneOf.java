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

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Block;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.object.EqualTo;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


@StaticFactories(
    value = "Composite",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class NoneOf<T> implements Quality<T>
{
    private final Iterable<? extends Quality<? super T>> mDelegates;


    @SafeVarargs
    public NoneOf(T... values)
    {
        this(new Mapped<>(EqualTo::new, new Seq<>(values)));
    }


    @SafeVarargs
    public NoneOf(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public NoneOf(Iterable<? extends Quality<? super T>> delegates)
    {
        mDelegates = delegates;
    }


    @Override
    public Assessment assessmentOf(T candidate)
    {
        return new AllPassed(
            new Text("was"), EMPTY, EMPTY,
            new Mapped<>(
                delegate -> {
                    Assessment result = delegate.assessmentOf(candidate);
                    if (result.isSuccess())
                    {
                        return new Fail(result.description());
                    }
                    return new Pass(result.description());
                },
                mDelegates));
    }


    @Override
    public Description description()
    {
        return new Block(new Text("none of"), EMPTY, EMPTY,
            new Mapped<>(Quality::description, mDelegates));
    }
}

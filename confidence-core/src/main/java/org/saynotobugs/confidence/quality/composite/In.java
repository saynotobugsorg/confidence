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
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AnyPassed;
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.description.Block;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.Just;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.description.iterable.Numbered;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.Collection;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


@StaticFactories(
    value = "Composite",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class In<T> extends QualityComposition<T>
{
    @SafeVarargs
    public In(T... delegates)
    {
        this(new Mapped<>(EqualTo::new, new Seq<>(delegates)));
    }


    public In(Collection<? extends T> delegates)
    {
        this(new Mapped<>(EqualTo::new, delegates));
    }


    @SafeVarargs
    public In(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public In(Iterable<? extends Quality<? super T>> delegates)
    {
        super(actual ->
                new DescriptionUpdated(
                    new TextAndOriginal<>(new Spaced(new Value(actual), new Text("in"))),
                    new TextAndOriginal<>(new Spaced(new Value(actual), new Text("not in"))),
                    new AnyPassed(EMPTY,
                        EMPTY,
                        EMPTY,
                        new org.saynotobugs.confidence.assessment.iterable.Numbered(
                            new Mapped<>(
                                d -> new DescriptionUpdated(new Just<>(d.description()),
                                    d.assessmentOf(actual)),
                                delegates)))),
            new Block(new Text("in"), EMPTY, EMPTY, new Numbered(new Mapped<>(Quality::description, delegates))));
    }
}

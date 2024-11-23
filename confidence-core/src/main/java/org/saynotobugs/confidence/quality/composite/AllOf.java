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
import org.dmfs.jems2.single.Collected;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.iterable.Numbered;
import org.saynotobugs.confidence.description.Block;
import org.saynotobugs.confidence.description.Text;

import java.util.ArrayList;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;


@StaticFactories(
    value = "Composite",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class AllOf<T> extends QualityComposition<T>
{
    /**
     * Creates a Matcher that passes if all the given {@link Quality}s match or if no {@link Quality} was given.
     */
    @SafeVarargs
    public AllOf(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    /**
     * Matches if all the given {@link Quality}s match or if no {@link Quality} was given.
     */
    public AllOf(Iterable<? extends Quality<? super T>> delegates)
    {
        super(
            actual -> new AllPassed(
                new Text("all of"),
                EMPTY,
                EMPTY,
                new Numbered(new Collected<>(ArrayList::new, new Mapped<>(d -> d.assessmentOf(actual), delegates)).value())),
            new Block(
                new Text("all of"),
                EMPTY,
                EMPTY,
                new org.saynotobugs.confidence.description.iterable.Numbered(new Mapped<>(Quality::description, delegates))));
    }
}

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
import org.dmfs.jems2.iterable.Numbered;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.optional.First;
import org.dmfs.jems2.predicate.Not;
import org.dmfs.jems2.single.Backed;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.StructuredDescription;
import org.saynotobugs.confidence.description.TextDescription;

import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class AllOfFailingFast<T> extends QualityComposition<T>
{
    /**
     * Creates a Matcher that passes if all the given {@link Quality}s match or if no {@link Quality} was given.
     * In contrast to {@link AllOf} this Matcher stops evaluating matchers when the first {@link Quality} mismatches.
     */
    @SafeVarargs
    public AllOfFailingFast(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    /**
     * Matches if all the given {@link Quality}s match or if no {@link Quality} was given.
     * In contrast to {@link AllOf} this Matcher stops evaluating matchers when the first {@link Quality} mismatches.
     */
    public AllOfFailingFast(Iterable<? extends Quality<? super T>> delegates)
    {
        super(actual ->
                new Backed<>(
                    new First<>(new Not<>(Assessment::isSuccess),
                        new Mapped<>(
                            d -> new FailPrepended(new TextDescription("(" + d.left() + ")"), d.right().assessmentOf(actual)),
                            new Numbered<>(delegates))),
                    new Pass()).value(),
            new StructuredDescription(
                new Composite(NEW_LINE, new TextDescription("and"), NEW_LINE),
                new org.saynotobugs.confidence.description.iterable.Numbered((i, d) -> new Delimited(new TextDescription("(" + i + ")"), d),
                    new Mapped<>(Quality::description, delegates))));
    }
}

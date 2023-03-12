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
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AnyPassed;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.description.*;
import org.saynotobugs.confidence.quality.object.Satisfies;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.saynotobugs.confidence.description.LiteralDescription.COMMA_NEW_LINE;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class In<T> extends QualityComposition<T>
{
    @SafeVarargs
    public In(T... delegates)
    {
        this(asList(delegates));
    }


    public In(Collection<? extends T> delegates)
    {
        super(
            new Satisfies<>(delegates::contains,
                actual -> new Spaced(new ValueDescription(actual), new TextDescription("not in"), new IterableDescription(delegates)),
                new Spaced(new TextDescription("in"), new IterableDescription(delegates))));
    }


    @SafeVarargs
    public In(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public In(Iterable<? extends Quality<? super T>> delegates)
    {
        super(actual -> new AnyPassed(
                new Spaced(new ValueDescription(actual), new TextDescription("not in { ")),
                COMMA_NEW_LINE,
                new TextDescription(" }"),
                new Mapped<>(d -> new FailUpdated(m -> d.description(), d.assessmentOf(actual)), delegates)),
            new Spaced(
                new TextDescription("in"),
                new StructuredDescription(new TextDescription("{ "),
                    COMMA_NEW_LINE,
                    new TextDescription(" }"),
                    new Mapped<>(Quality::description, delegates))));
    }
}

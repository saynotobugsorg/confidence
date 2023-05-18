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

package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Numbered;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA_NEW_LINE;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class Each<T> extends QualityComposition<Iterable<T>>
{

    @SafeVarargs
    public Each(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public Each(Iterable<? extends Quality<? super T>> delegates)
    {
        this(new AllOf<>(delegates));
    }


    public Each(Quality<? super T> delegate)
    {
        super(actual -> new AllPassed(new Text("elements ["), COMMA_NEW_LINE, new Text("]"),
                new Mapped<>(
                    numberedVerdict -> new FailPrepended(new Text(numberedVerdict.left() + ": "), numberedVerdict.right()),
                    new Numbered<>(new Mapped<>(delegate::assessmentOf, actual)))),
            new Spaced(new Text("each element"), delegate.description()));
    }
}

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

package org.saynotobugs.confidence.quality.composite;

import org.dmfs.jems2.iterable.Seq;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.Structured;
import org.saynotobugs.confidence.description.Text;

import static org.saynotobugs.confidence.description.LiteralDescription.EMPTY;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


@StaticFactories(
    value = "Composite",
    packageName = "org.saynotobugs.confidence.core.quality")
public final class Both<T> extends QualityComposition<T>
{
    /**
     * A {@link Quality} of an object satisfying two other {@link Quality}s.
     * <p>
     * This is similar to {@link AllOf}, except that it takes exactly two delegates in exchange for a simpler
     * description.
     */
    public Both(Quality<? super T> delegate1, Quality<? super T> delegate2)
    {
        super(actual ->
                new AllPassed(new Text("both,"), new Text(" and"), delegate1.assessmentOf(actual), delegate2.assessmentOf(actual)),
            new Structured(new Composite(new Text("both,"), NEW_LINE), new Composite(new Text(" and"), NEW_LINE), EMPTY, new Seq<>(delegate1.description(), delegate2.description())));
    }
}

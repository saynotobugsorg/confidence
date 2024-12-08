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

package org.saynotobugs.confidence.json.quality;

import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.single.Collected;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.description.Block;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import java.util.ArrayList;

/**
 * Internal helper, like {@link org.saynotobugs.confidence.quality.composite.AllOf}, but with configurable texts.
 */
final class Conjunction<T> extends QualityComposition<T>
{
    /**
     * Matches if all the given {@link Quality}s match or if no {@link Quality} was given.
     */
    public Conjunction(Description prefix, Description delimiter, Description suffix, Iterable<? extends Quality<? super T>> delegates, Description emptyFallback)
    {
        super(actual -> new AllPassed(
                prefix,
                delimiter,
                suffix,
                new Collected<>(ArrayList::new, new Mapped<>(d -> d.assessmentOf(actual), delegates)).value()),
            new Block(
                prefix,
                delimiter,
                suffix,
                new Mapped<>(Quality::description, delegates),
                emptyFallback));
    }
}

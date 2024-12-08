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

package org.saynotobugs.confidence.quality.object;

import org.dmfs.jems2.Generator;
import org.dmfs.jems2.Procedure;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;


@StaticFactories(
    value = "Object",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class MutatedBy<T> implements Quality<Generator<T>>
{
    private final Description mValueDescription;
    private final Procedure<? super T> mMutator;
    private final Quality<? super T> mValueQuality;


    public MutatedBy(Description valueDescription, Procedure<? super T> mutator, Quality<? super T> valueQuality)
    {
        mValueDescription = valueDescription;
        mMutator = mutator;
        mValueQuality = valueQuality;
    }


    @Override
    public Assessment assessmentOf(Generator<T> candidate)
    {
        T value = candidate.next();
        mMutator.process(value);
        return new DescriptionUpdated(new TextAndOriginal<>(new Spaced(new Text("mutated by"), mValueDescription)),
            mValueQuality.assessmentOf(value));
    }


    @Override
    public Description description()
    {
        return new Spaced(new Text("mutated by"), mValueDescription, mValueQuality.description());
    }
}

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

package org.saynotobugs.confidence.test.quality;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.PassIf;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.object.Anything;


@StaticFactories(
    value = "Quality",
    deprecates = @DeprecatedFactories(value = "Test"))
public final class Passes<T> implements Quality<Quality<? super T>>
{
    private final T mPassingValue;
    private final Quality<? super Description> mPassDescriptionQuality;

    public Passes(T passingValue)
    {
        this(passingValue, new Anything());
    }


    public Passes(T passingValue, String passDescription)
    {
        this(passingValue, new DescribesAs(passDescription));
    }

    public Passes(T passingValue, Quality<? super Description> passDescriptionQuality)
    {
        mPassingValue = passingValue;
        mPassDescriptionQuality = passDescriptionQuality;
    }


    @Override
    public Assessment assessmentOf(Quality<? super T> candidate)
    {
        Assessment result = candidate.assessmentOf(mPassingValue);
        return new PassIf(
            result.isSuccess() && mPassDescriptionQuality.assessmentOf(result.description()).isSuccess(),
            new Spaced(new Text("passed"), new Value(mPassingValue),
                new Text("with description"), new Value(mPassDescriptionQuality.description())),
            new Spaced(result.isSuccess() ? new Text("passed") : new Text("failed"), new Value(mPassingValue),
                new Text("with description"), new Value(result.description()))
        );
    }


    @Override
    public Description description()
    {
        return new Spaced(
            new Text("passes"),
            new Value(mPassingValue),
            new Text("with desciption"),
            new Value(mPassDescriptionQuality.description()));
    }
}

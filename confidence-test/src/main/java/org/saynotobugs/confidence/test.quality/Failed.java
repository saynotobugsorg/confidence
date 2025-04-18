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
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.composite.QualityComposition;


@StaticFactories(
    value = "Assessment",
    deprecates = @DeprecatedFactories(value = "Test"))
public final class Failed extends QualityComposition<Assessment>
{
    public Failed(Quality<? super Description> mismatchDescription)
    {
        super(actual -> actual.isSuccess()
                ? new Fail(new Text("passed"))
                : new DescriptionUpdated(
                new TextAndOriginal<>("failed with description"),
                mismatchDescription.assessmentOf(actual.description())),
            new Spaced(new Text("failed with"), mismatchDescription.description()));
    }
}

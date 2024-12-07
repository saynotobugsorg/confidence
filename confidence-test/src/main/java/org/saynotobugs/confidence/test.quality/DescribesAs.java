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

package org.saynotobugs.confidence.test.quality;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.Scribe;
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.DescriptionDescription;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.scribe.StringBuilderScribe;

import java.util.regex.Pattern;


@StaticFactories(
    value = "Description",
    deprecates = @DeprecatedFactories(value = "Test"))
public final class DescribesAs implements Quality<Description>
{
    private final Quality<? super String> mDelegate;


    public DescribesAs(String description)
    {
        this(new EqualTo<>(description));
    }


    public DescribesAs(Pattern description)
    {
        this(new MatchesPattern(description));
    }


    public DescribesAs(Quality<? super String> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Assessment assessmentOf(Description candidate)
    {
        Scribe sink = new StringBuilderScribe("  ");
        candidate.describeTo(sink);
        return new DescriptionUpdated(mismatch -> new Composite(new Text("described as"), new DescriptionDescription(mismatch)),
            mDelegate.assessmentOf(sink.toString()));
    }


    @Override
    public Description description()
    {
        return new Composite(new Text("describes as"), new DescriptionDescription(mDelegate.description()));
    }
}

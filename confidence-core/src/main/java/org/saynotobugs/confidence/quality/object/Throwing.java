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

package org.saynotobugs.confidence.quality.object;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.TextDescription;


@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
public final class Throwing implements Quality<Throwing.Breakable>
{
    public interface Breakable
    {
        void run() throws Throwable;
    }


    private final Quality<? super Throwable> mDelegate;


    public Throwing(Class<? extends Throwable> exception)
    {
        this(new InstanceOf<>(exception));
    }


    public Throwing(Quality<? super Throwable> delegate)
    {
        mDelegate = delegate;
    }


    @Override
    public Assessment assessmentOf(Throwing.Breakable candidate)
    {
        try
        {
            candidate.run();
            return new Fail(new Spaced(new TextDescription("not throwing"), mDelegate.description()));
        }
        catch (Throwable e)
        {
            return new FailPrepended(new TextDescription("throwing"), mDelegate.assessmentOf(e));
        }
    }


    @Override
    public Description description()
    {
        return new Spaced(new TextDescription("throwing"), mDelegate.description());
    }
}

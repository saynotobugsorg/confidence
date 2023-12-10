/*
 * Copyright 2023 dmfs GmbH
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

package org.saynotobugs.confidence.quality.file;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.FailPrepended;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@StaticFactories(value = "File", packageName = "org.saynotobugs.confidence.quality")
public final class ContainsText implements Quality<File>
{
    private final Charset mCharset;
    private final Quality<? super CharSequence> mDelegate;

    public ContainsText(String text)
    {
        this(new EqualTo<>(text));
    }

    public ContainsText(Quality<? super CharSequence> mDelegate)
    {
        this(StandardCharsets.UTF_8, mDelegate);
    }

    public ContainsText(Charset mCharset, Quality<? super CharSequence> mDelegate)
    {
        this.mCharset = mCharset;
        this.mDelegate = mDelegate;
    }

    @Override
    public Assessment assessmentOf(File candidate)
    {
        try (Reader reader = new InputStreamReader(
            Files.newInputStream(candidate.toPath()),
            mCharset))
        {
            StringBuilder builder = new StringBuilder((int) candidate.length());
            char[] buffer = new char[10240];
            int read;
            while ((read = reader.read(buffer)) >= 0)
            {
                builder.append(buffer, 0, read);
            }
            return new FailPrepended(
                new Spaced(
                    new Text("contained"),
                    new Value(mCharset.name()),
                    new Text("text")), mDelegate.assessmentOf(builder.toString()));
        }
        catch (IOException exception)
        {
            return new Fail(new Spaced(new Text("threw"), new Value(exception), new Text("while reading")));
        }
    }

    @Override
    public Description description()
    {
        return new Spaced(
            new Text("contains"),
            new Value(mCharset.name()),
            new Text("text"),
            mDelegate.description());
    }
}

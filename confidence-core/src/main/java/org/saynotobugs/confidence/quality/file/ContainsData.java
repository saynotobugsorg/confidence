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

package org.saynotobugs.confidence.quality.file;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.DescriptionUpdated;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@StaticFactories(
    value = "File",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "File", packageName = "org.saynotobugs.confidence.quality"))
public final class ContainsData implements Quality<File>
{
    private final Quality<? super byte[]> mDelegate;

    public ContainsData(byte[] data)
    {
        this(new EqualTo<>(data));
    }

    public ContainsData(Quality<? super byte[]> delegate)
    {
        mDelegate = delegate;
    }

    @Override
    public Assessment assessmentOf(File candidate)
    {
        try (DataInputStream reader = new DataInputStream(new FileInputStream(candidate)))
        {
            byte[] buffer = new byte[(int) candidate.length()];
            reader.readFully(buffer);
            return new DescriptionUpdated(
                new TextAndOriginal<>(new Text("contained data")),
                mDelegate.assessmentOf(buffer));
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
            new Text("contains data"),
            mDelegate.description());
    }
}

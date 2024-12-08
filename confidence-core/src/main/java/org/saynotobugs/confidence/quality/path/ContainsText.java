/*
 * Copyright 2024 dmfs GmbH
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

package org.saynotobugs.confidence.quality.path;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@StaticFactories(
    value = "Path",
    packageName = "org.saynotobugs.confidence.core.quality")
public final class ContainsText extends QualityComposition<Path>
{
    public ContainsText(String text)
    {
        this(new EqualTo<>(text));
    }

    public ContainsText(Quality<? super CharSequence> mDelegate)
    {
        this(StandardCharsets.UTF_8, mDelegate);
    }

    public ContainsText(Charset charset, Quality<? super CharSequence> delegate)
    {
        super(new Has<>(
            new Text("contains"),
            new Text("contained"),
            path -> new String(Files.readAllBytes(path), charset),
            new DescribedAs<>(
                new TextAndOriginal<>(new Spaced(new Value(charset.name()), new Text("text"))),
                delegate)));
    }
}

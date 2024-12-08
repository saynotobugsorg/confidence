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


import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.grammar.That;

import java.io.File;

@StaticFactories(
    value = "File",
    packageName = "org.saynotobugs.confidence.core.quality")
public final class ContainsFile extends QualityComposition<File>
{
    public ContainsFile(String child)
    {
        this(child, new That<>(new Exists()));
    }

    public ContainsFile(String child, Quality<? super File> delegate)
    {
        super(new Has<>(
            new TextAndOriginal<>(new Spaced(new Text("contains"), new Value(child))),
            new TextAndOriginal<>(new Spaced(new Text("contained"), new Value(child))),
            file -> new File(file, child),
            delegate));
    }
}

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
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Satisfies;

import java.nio.file.Files;
import java.nio.file.Path;

@StaticFactories(
    value = "Path",
    packageName = "org.saynotobugs.confidence.core.quality")
public final class Writeable extends QualityComposition<Path>
{
    public Writeable()
    {
        super(new Satisfies<>(Files::isWritable, file -> new Text("not writeable"), new Text("writeable")));
    }
}

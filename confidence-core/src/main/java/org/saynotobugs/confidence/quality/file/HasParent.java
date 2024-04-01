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

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.io.File;

@StaticFactories(
    value = "File",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "File", packageName = "org.saynotobugs.confidence.quality"))
public final class HasParent extends QualityComposition<File>
{
    public HasParent(File parent)
    {
        this(new EqualTo<>(parent));
    }

    public HasParent(Quality<? super File> parentQuality)
    {
        super(new Has<>("parent", File::getParentFile, parentQuality));
    }
}

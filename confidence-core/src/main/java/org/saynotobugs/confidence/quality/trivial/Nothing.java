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

package org.saynotobugs.confidence.quality.trivial;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;


/**
 * A {@link Quality} that never matches.
 * <p>
 * This may be useful for testing {@link Quality}s.
 *
 * @deprecated use {@link org.saynotobugs.confidence.quality.object.Nothing} instead.
 */
@StaticFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality")
@Deprecated
public final class Nothing extends QualityComposition<Object>
{
    /**
     * Creates a {@link Quality} that never matches.
     */
    public Nothing()
    {
        super(actual -> new Fail(new Value(actual)), new Text("<nothing>"));
    }
}

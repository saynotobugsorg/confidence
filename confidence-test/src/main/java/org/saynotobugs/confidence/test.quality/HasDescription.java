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
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import java.util.regex.Pattern;


@StaticFactories(
    value = "Quality",
    deprecates = @DeprecatedFactories(value = "Test"))
public final class HasDescription extends QualityComposition<Quality<?>>
{

    public HasDescription(String expectation)
    {
        this(new DescribesAs(expectation));
    }


    public HasDescription(Pattern expectation)
    {
        this(new DescribesAs(expectation));
    }


    public HasDescription(Quality<? super org.saynotobugs.confidence.Description> delegate)
    {
        super(new Has<>(new Text("description"), new Text("description"), Quality::description, delegate));
    }
}

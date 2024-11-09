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

package org.saynotobugs.confidence.quality.charsequence;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.description.valuedescription.CharSequenceDescription;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.valuedescription.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Satisfies;

import java.util.regex.Pattern;


@StaticFactories(
    value = "CharSequence",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class ContainsPattern extends QualityComposition<CharSequence>
{
    public ContainsPattern(String pattern)
    {
        this(Pattern.compile(pattern));
    }


    public ContainsPattern(Pattern pattern)
    {
        super(new Satisfies<>(
            actual -> pattern.matcher(actual).find(),
            actual -> new Spaced(new CharSequenceDescription(actual), new Text("did not contain pattern"), new Value(pattern)),
            new Spaced(new Text("contains pattern"), new Value(pattern))));
    }
}

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

package org.saynotobugs.confidence.quality.object;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginal;
import org.saynotobugs.confidence.description.bifunction.TextAndOriginalAndText;
import org.saynotobugs.confidence.quality.composite.DescribedAs;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

@StaticFactories(
    value = "Object",
    packageName = "org.saynotobugs.confidence.core.quality")
public final class HashCodeEquals extends QualityComposition<Object>
{

    public HashCodeEquals(int hashCode)
    {
        super(new Has<>("hashCode", Object::hashCode, new EqualTo<>(hashCode)));
    }

    public HashCodeEquals(Object referenceObject)
    {
        super(
            new DescribedAs<>(
                new TextAndOriginalAndText<>(new Text("had hashCode"), new Spaced(new Text("like"), new Value(referenceObject))),
                new TextAndOriginal<>("had hashCode"),
                new TextAndOriginalAndText<>(new Text("has hashCode"), new Spaced(new Text("like"), new Value(referenceObject))),
                new Has<>(Object::hashCode, new EqualTo<>(referenceObject.hashCode()))));
    }
}

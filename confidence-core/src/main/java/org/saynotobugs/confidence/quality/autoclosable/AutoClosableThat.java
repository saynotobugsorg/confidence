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

package org.saynotobugs.confidence.quality.autoclosable;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.composite.WithFinalizer;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.object.Successfully;


/**
 * A {@link Quality} of an {@link AutoCloseable} object. It delegates to another {@link Quality} and calls {@link AutoCloseable#close()} afterwards.
 */
@StaticFactories(
    value = "AutoClosable",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class AutoClosableThat<T extends AutoCloseable> extends QualityComposition<T>
{
    public AutoClosableThat(Quality<? super T> delegate)
    {
        super(new WithFinalizer<>(
            new Is<>(
                new Successfully<>(
                    new Text("closed"),
                    new Text("closed throwing"),
                    new Text("closed"),
                    AutoCloseable::close)),
            new Text("AutoClosable that"),
            delegate
        ));
    }
}

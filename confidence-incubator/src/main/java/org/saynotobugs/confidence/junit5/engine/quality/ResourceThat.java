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

package org.saynotobugs.confidence.junit5.engine.quality;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.junit5.engine.Resource;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import static org.dmfs.jems2.confidence.Jems2.hasValue;
import static org.saynotobugs.confidence.quality.Core.allOf;
import static org.saynotobugs.confidence.quality.Core.autoClosableThat;

@StaticFactories(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class ResourceThat<T> extends QualityComposition<Resource<T>>
{
    public ResourceThat(Quality<? super T> delegate, Quality<? super T> cleanUpDelegate)
    {
        super(allOf(
            autoClosableThat(hasValue(delegate)),
            hasValue(cleanUpDelegate)));
    }
}

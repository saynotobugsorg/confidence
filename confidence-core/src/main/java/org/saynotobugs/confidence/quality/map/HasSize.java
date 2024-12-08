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

package org.saynotobugs.confidence.quality.map;

import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.Map;


@StaticFactories(
    value = "Map",
    packageName = "org.saynotobugs.confidence.core.quality")
public final class HasSize<K, V> extends QualityComposition<Map<K, V>>
{
    /**
     * Creates a {@link Quality} that matches if size of the {@link Map} under test is equal to the given value.
     * <pre>
     *     assertThat(Map.of("key1", "value1"), hasSize(1));
     * </pre>
     */
    public HasSize(int elementCount)
    {
        this(new EqualTo<>(elementCount));
    }


    /**
     * Creates a {@link Quality} that matches if the size {@link Map} under test matches the given {@link Quality}.
     * <pre>
     *     assertThat(Map.of("key1", "value1"), hasSize(lessThan(4)));
     * </pre>
     */
    public HasSize(Quality<? super Integer> delegate)
    {
        super(new Has<>("size", Map::size, delegate));
    }
}

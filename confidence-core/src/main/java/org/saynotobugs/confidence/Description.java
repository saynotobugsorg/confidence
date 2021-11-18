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

package org.saynotobugs.confidence;

import org.dmfs.srcless.annotations.composable.Composable;


/**
 * Instances of this type know how to write a description using the given {@link Scribe}.
 */
@Composable(packageName = "org.saynotobugs.confidence.description")
public interface Description
{
    /**
     * Writes this {@link Description} to the given {@link Scribe}.
     */
    void describeTo(Scribe scribe);
}

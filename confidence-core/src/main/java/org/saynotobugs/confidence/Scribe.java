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

package org.saynotobugs.confidence;

public interface Scribe
{
    /**
     * Returns a new {@link Scribe} that adds an indentation to each new line.
     */
    Scribe indented();

    /**
     * Writes the given {@link CharSequence} and returns this instance.
     */
    Scribe append(CharSequence charSequence);

    /**
     * Starts a new line with the current indentation and returns this instance.
     */
    Scribe newLine();
}

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

package org.saynotobugs.confidence.description;

import java.util.Optional;


/**
 * The Description of an {@link Optional} value.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public final class OptionalDescription extends DescriptionComposition
{
    public OptionalDescription(Optional<?> value)
    {
        super(
            value.isPresent()
                ? new Composite(new Text("<present "), new Value(value.get()), new Text(">"))
                : new Text("<empty>")
        );
    }
}

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

package org.saynotobugs.confidence.quality.path;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.nio.file.Path;

import static org.saynotobugs.confidence.Assertion.assertThat;

class HasParentTest
{
    @Test
    void testWithQuality()
    {
        assertThat(new HasParent(new HasName("path")),
            new AllOf<>(
                new Passes<>(Path.of("path", "someFile"), "had parent had name \"path\""),
                new Fails<>(Path.of("otherPath", "otherFile"), "had parent had name \"otherPath\""),
                new HasDescription("has parent has name \"path\"")));
    }

    @Test
    void testWithFile()
    {
        assertThat(new HasParent(Path.of("path")),
            new AllOf<>(
                new Passes<>(Path.of("path", "someFile"), "had parent <path>"),
                new Fails<>(Path.of("otherPath", "otherFile"), "had parent <otherPath>"),
                new HasDescription("has parent <path>")));
    }
}
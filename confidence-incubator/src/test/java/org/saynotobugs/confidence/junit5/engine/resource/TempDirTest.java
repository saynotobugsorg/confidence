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

package org.saynotobugs.confidence.junit5.engine.resource;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.junit5.engine.quality.ResourceThat;

import java.io.File;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.Composite.*;
import static org.saynotobugs.confidence.core.quality.File.*;
import static org.saynotobugs.confidence.core.quality.Grammar.is;


class TempDirTest
{
    @Test
    void testEmpty()
    {
        assertThat(TempDir::new,
            new ResourceThat<>(
                100, is(aDirectory()),
                not(exists()))
        );
    }


    @Test
    void testWithContent()
    {
        assertThat(TempDir::new,
            new ResourceThat<>(
                // TODO, this can't be achieved with `mutatedBy` because that tests
                // a generator of new instances. also we don't really mutate the file instance
                // We need a better way to express this
                100,
                has(
                    "creating a temporary file",
                    (File f) -> {
                        File x = new File(f, "abc");
                        x.createNewFile();
                        return f;
                    },
                    allOf(
                        is(aDirectory()),
                        has((File d) -> new File(d, "abc"), is(aFile())))),
                not(exists()))
        );
    }

}
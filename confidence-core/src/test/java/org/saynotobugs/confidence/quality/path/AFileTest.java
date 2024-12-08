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
import org.junit.jupiter.api.io.TempDir;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.saynotobugs.confidence.Assertion.assertThat;

class AFileTest
{
    @TempDir
    Path tempDir;

    @Test
    void test() throws IOException
    {
        Path file = tempDir.resolve("somefile");
        Files.createFile(file);
        assertThat(new AFile(),
            new AllOf<>(
                new Passes<>(file, new DescribesAs(new MatchesPattern("file <.*/somefile>"))),
                new Fails<>(tempDir, "not a file"),
                new Fails<>(tempDir.resolve("nonExistentFile"), "not a file"),
                new HasDescription("a file")));
    }
}
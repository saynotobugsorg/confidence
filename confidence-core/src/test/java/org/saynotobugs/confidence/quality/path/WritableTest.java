/*
 * Copyright 2024 dmfs GmbH
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

package org.saynotobugs.confidence.quality.path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.saynotobugs.confidence.Assertion.assertThat;

class WritableTest
{
    @TempDir
    Path tempDir;

    @Test
    void test() throws IOException
    {
        Path writeableFile = tempDir.resolve("writeableFile");
        Files.createFile(writeableFile);
        Path unwriteableFile = tempDir.resolve("unwriteableFile");
        Files.createFile(unwriteableFile);
        unwriteableFile.toFile().setWritable(false);
        assertThat(new Writeable(),
            new AllOf<>(
                new Passes<>(tempDir, writeableFile),
                new Fails<>(unwriteableFile, "not writeable"),
                new Fails<>(tempDir.resolve("nonExistentFile"), "not writeable"),
                new HasDescription("writeable")));
    }
}
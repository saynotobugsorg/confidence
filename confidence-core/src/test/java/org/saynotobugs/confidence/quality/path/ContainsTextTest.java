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
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ContainsTextTest
{
    @TempDir
    Path tempDir;

    @Test
    void testWithQuality() throws IOException
    {
        Path emptyFile = tempDir.resolve("somefile");
        Files.createFile(emptyFile);
        Path file10Bytes = tempDir.resolve("fileWithSize10");
        Files.createFile(file10Bytes);
        try (Writer fileWriter = new FileWriter(file10Bytes.toFile()))
        {
            fileWriter.write("0123456789");
        }
        assertThat(new ContainsText(UTF_8, new EqualTo<>("0123456789")),
            new AllOf<>(
                new Passes<>(file10Bytes, "contained \"0123456789\""),
                new Fails<>(tempDir, new DescribesAs(new MatchesPattern("threw <java.io.IOException: [^>]+>"))),
                new Fails<>(emptyFile, "contained \"UTF-8\" text \"\""),
                new HasDescription("contains \"UTF-8\" text \"0123456789\"")
            ));
    }

    @Test
    void testWithPrimitive() throws IOException
    {
        Path emptyFile = tempDir.resolve("somefile");
        Files.createFile(emptyFile);
        Path file10Bytes = tempDir.resolve("fileWithSize10");
        Files.createFile(file10Bytes);
        try (Writer fileWriter = new FileWriter(file10Bytes.toFile()))
        {
            fileWriter.write("0123456789");
        }
        assertThat(new ContainsText("0123456789"),
            new AllOf<>(
                new Passes<>(file10Bytes, "contained \"0123456789\""),
                new Fails<>(tempDir, new DescribesAs(new MatchesPattern("threw <java.io.IOException: [^>]+>"))),
                new Fails<>(emptyFile, "contained \"UTF-8\" text \"\""),
                new HasDescription("contains \"UTF-8\" text \"0123456789\"")
            ));
    }
}
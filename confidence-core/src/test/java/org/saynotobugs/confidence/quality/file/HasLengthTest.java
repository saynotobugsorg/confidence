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

package org.saynotobugs.confidence.quality.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.saynotobugs.confidence.Assertion.assertThat;

class HasLengthTest
{
    @TempDir
    File tempDir;

    @Test
    void testWithQuality() throws IOException
    {
        File emptyFile = new File(tempDir, "somefile");
        emptyFile.createNewFile();
        File file10Bytes = new File(tempDir, "fileWithSize10");
        file10Bytes.createNewFile();
        try (Writer fileWriter = new FileWriter(file10Bytes))
        {
            fileWriter.write("0123456789");
        }
        assertThat(new HasLength(new EqualTo<>(10L)),
            new AllOf<>(
                new Passes<>(file10Bytes, "had length 10l"),
                new Fails<>(tempDir, new DescribesAs(new MatchesPattern("had length \\d+l"))),
                new Fails<>(emptyFile, "had length 0l"),
                new HasDescription("has length 10l")
            ));
    }

    @Test
    void testWithPrimitive() throws IOException
    {
        File emptyFile = new File(tempDir, "somefile");
        emptyFile.createNewFile();
        File file10Bytes = new File(tempDir, "fileWithSize10");
        file10Bytes.createNewFile();
        try (Writer fileWriter = new FileWriter(file10Bytes))
        {
            fileWriter.write("0123456789");
        }
        assertThat(new HasLength(10),
            new AllOf<>(
                new Passes<>(file10Bytes, "had length 10l"),
                new Fails<>(tempDir, new DescribesAs(new MatchesPattern("had length \\d+l"))),
                new Fails<>(emptyFile, "had length 0l"),
                new HasDescription("has length 10l")
            ));
    }

}
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

package org.saynotobugs.confidence.json.quality;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static org.saynotobugs.confidence.Assertion.assertThat;

class JsonFileOfTest
{
    @TempDir
    File mTempDir;

    @Test
    void test() throws IOException
    {
        File jsonFile = new File(mTempDir, "1.json");
        jsonFile.createNewFile();
        try (Writer writer = new FileWriter(jsonFile))
        {
            writer.write("{\"abc\": \"xyz\"}");
        }

        File nonJsonFile = new File(mTempDir, "1.md");
        nonJsonFile.createNewFile();
        try (Writer writer = new FileWriter(nonJsonFile))
        {
            writer.write("# header\n\nparagraph");
        }

        File nonFile = new File(mTempDir, "nonFile");


        assertThat(new JsonFileOf(new Object(new Anything())),
            new AllOf<>(
                new Passes<>(jsonFile, "contained \"UTF-8\" text JSON {\n" +
                    "  <{\\\"abc\\\":\\\"xyz\\\"}>\n" +
                    "}"),
                new Fails<>(nonJsonFile, "contained \"UTF-8\" text JSON not an object"),
                new Fails<>(nonFile, new DescribesAs(new MatchesPattern("threw <java.nio.file.NoSuchFileException.*"))),
                new HasDescription("contains \"UTF-8\" text JSON {\n  <anything>\n}")));
    }

}
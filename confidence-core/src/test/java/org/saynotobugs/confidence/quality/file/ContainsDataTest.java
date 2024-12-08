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

package org.saynotobugs.confidence.quality.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ContainsDataTest
{
    @TempDir
    File dir;

    @Test
    void test() throws IOException
    {
        File passingFile = new File(dir, "passes");
        Files.write(passingFile.toPath(), new byte[] { 1, 2, 3 });
        File emptyFile = new File(dir, "empty");
        emptyFile.createNewFile();

        assertThat(new ContainsData(new byte[] { 1, 2, 3 }),
            new AllOf<>(
                new Passes<>(passingFile, "contained data array that iterated [\n  0: 1\n  1: 2\n  2: 3\n]"),
                new Fails<>(emptyFile, "contained data array that iterated [\n  0: missing 1\n  1: missing 2\n  2: missing 3\n]"),
                new Fails<>(new File(dir, "nonexistent"), new DescribesAs(new MatchesPattern("threw <java.io.FileNotFoundException: .*> while reading"))),
                new HasDescription("contains data [\n  1\n  2\n  3\n]")));
    }

}
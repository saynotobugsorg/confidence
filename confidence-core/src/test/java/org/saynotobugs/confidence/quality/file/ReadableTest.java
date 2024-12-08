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

import static org.saynotobugs.confidence.Assertion.assertThat;

class ReadableTest
{
    @TempDir
    File tempDir;

    @Test
    void test() throws IOException
    {
        File readableFile = new File(tempDir, "readableFile");
        readableFile.createNewFile();
        readableFile.setReadable(true);
        File unreadableFile = new File(tempDir, "unreadableFile");
        unreadableFile.createNewFile();
        unreadableFile.setReadable(false);
        assertThat(new Readable(),
            new AllOf<>(
                new Passes<>(tempDir, new DescribesAs(new MatchesPattern("readable </.*>"))),
                new Passes<>(readableFile, new DescribesAs(new MatchesPattern("readable </.*>"))),
                new Fails<>(unreadableFile, "not readable"),
                new Fails<>(new File(tempDir, "nonExistentFile"), "not readable"),
                new HasDescription("readable")));
    }
}
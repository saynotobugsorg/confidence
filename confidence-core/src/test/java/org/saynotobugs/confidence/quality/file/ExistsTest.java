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
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.File;
import java.io.IOException;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ExistsTest
{
    @TempDir
    File tempDir;

    @Test
    void test() throws IOException
    {
        File file = new File(tempDir, "somefile");
        file.createNewFile();
        assertThat(new Exists(),
            new AllOf<>(
                new Passes<>(tempDir, "existed"),
                new Passes<>(file, "existed"),
                new Fails<>(new File(tempDir, "nonExistentFile"), "did not exist"),
                new HasDescription("exists")));
    }
}
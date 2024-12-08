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

package org.saynotobugs.confidence.junit5.engine.procedure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.dmfs.jems2.confidence.Jems2.procedureThatAffects;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.File.*;
import static org.saynotobugs.confidence.core.quality.Grammar.soIt;
import static org.saynotobugs.confidence.core.quality.Grammar.that;
import static org.saynotobugs.confidence.core.quality.Object.hasToString;

class WithFileTest
{
    @TempDir
    File dir;

    @Test
    void testEmptyFile()
    {
        assertThat(new WithFile("somefile"),
            procedureThatAffects(() -> dir, soIt(
                containsFile("somefile"))));
    }


    @Test
    void testFileWithUtf8String()
    {
        assertThat(new WithFile("somefile", "utf-8 contentöäü@ſ€"),
            procedureThatAffects(() -> dir, soIt(
                containsFile("somefile", that(containsText("utf-8 contentöäü@ſ€"))))));
    }

    @Test
    void testFileWithLatin1String()
    {
        assertThat(new WithFile("somefile", "latin-1-text-öäü", ISO_8859_1),
            procedureThatAffects(() -> dir, soIt(
                containsFile("somefile", that(containsText(ISO_8859_1, hasToString("latin-1-text-öäü")))))));
    }


    @Test
    void testFileWithByteArray()
    {
        assertThat(new WithFile("somefile", new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }),
            procedureThatAffects(() -> dir, soIt(
                containsFile("somefile", that(containsData(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }))))));
    }

}
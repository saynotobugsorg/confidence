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

package org.saynotobugs.confidence.junit5.engine.environment;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.TextDescription;

import java.io.File;

import static org.dmfs.jems2.confidence.Jems2.hasValue;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;


class TempDirTest
{
    @Test
    void testEmpty()
    {
        assertThat(new TempDir(),
            // todo: create and use Resource Quality to test the resource for correct cleanup
            hasValue(
                allOf(
                    autoClosableThat(hasValue(satisfies(File::isDirectory, new TextDescription("is Directory")))),
                    hasValue(not(satisfies(File::exists, new TextDescription("exists"))))))
        );
    }


    @Test
    void testWithContent()
    {
        assertThat(new TempDir(),
            // todo: create and use Resource Quality to test the resource for correct cleanup
            hasValue(
                allOf(
                    hasValue(has("file abc", f -> {
                        File x = new File(f, "abc");
                        x.createNewFile();
                        return x;
                    }, satisfies(File::isFile, new TextDescription("is file")))),
                    autoClosableThat(hasValue(satisfies(File::isDirectory, new TextDescription("is Directory")))),
                    hasValue(not(satisfies(File::exists, new TextDescription("exists"))))))
        );
    }

}
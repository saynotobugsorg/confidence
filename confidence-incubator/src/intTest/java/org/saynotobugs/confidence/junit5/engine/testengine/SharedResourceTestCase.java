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

package org.saynotobugs.confidence.junit5.engine.testengine;

import org.dmfs.jems2.Generator;
import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.Confidence;
import org.saynotobugs.confidence.junit5.engine.Resource;
import org.saynotobugs.confidence.junit5.engine.resource.Derived;
import org.saynotobugs.confidence.junit5.engine.resource.Initialized;
import org.saynotobugs.confidence.junit5.engine.resource.SystemOut;
import org.saynotobugs.confidence.junit5.engine.resource.TempDir;

import java.io.File;

import static org.saynotobugs.confidence.junit5.engine.ConfidenceEngine.*;
import static org.saynotobugs.confidence.quality.Core.*;
import static org.saynotobugs.confidence.quality.File.hasLength;
import static org.saynotobugs.confidence.quality.File.*;


@Confidence
public final class SharedResourceTestCase
{
    // a temporary directory for the test
    Resource<File> testDir = new TempDir();

    // an empty file inside the temporary directory above
    Resource<File> testFile = new Initialized<>(File::createNewFile, new Derived<>(d -> new File(d, "xzy"), testDir));

    Assertion test_with_multiple_resources = withResources(testDir, testFile,
        (directory, file) -> assertionThat(file, is(allOf(
            is(aFile()),
            hasLength(0),
            hasParent(directory)))));

    Assertion testSystemOut = withResource(new SystemOut(),
        systemOut ->
            assertionThat((Generator<String> s) -> System.out.println("Hello"),
                is(consumerThatAffects(
                    () -> systemOut,
                    soIt(has("system out", Generator::next, containsPattern("Hello")))
                ))));

}
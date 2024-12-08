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

package org.saynotobugs.confidence.junit5.engine.testengine;

import org.saynotobugs.confidence.junit5.engine.Assertion;
import org.saynotobugs.confidence.junit5.engine.Confidence;
import org.saynotobugs.confidence.quality.object.HasToString;

import static org.saynotobugs.confidence.junit5.engine.ConfidenceEngine.assertionThat;


@Confidence
public final class MultipleAssertionFailAndPassTestCase
{
    Assertion passing_test = assertionThat("abc", new HasToString("abc"));
    Assertion failingTest = assertionThat("abc", new HasToString("abcd"));
    Assertion throwing_test = () -> {throw new RuntimeException();};
}
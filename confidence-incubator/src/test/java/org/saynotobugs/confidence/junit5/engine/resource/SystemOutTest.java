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

package org.saynotobugs.confidence.junit5.engine.resource;

import org.dmfs.jems2.Generator;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.junit5.engine.quality.ResourceThat;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.CharSequence.emptyCharSequence;
import static org.saynotobugs.confidence.core.quality.Composite.allOf;
import static org.saynotobugs.confidence.core.quality.Composite.has;
import static org.saynotobugs.confidence.core.quality.Object.equalTo;
import static org.saynotobugs.confidence.core.quality.Object.successfully;


class SystemOutTest
{
    @Test
    void test()
    {
        assertThat(SystemOut::new,
            new ResourceThat<Generator<String>>(
                1, allOf(
                has("generated", Generator::next, emptyCharSequence()),
                successfully(new Text("Wrote to System.out"), new Text("Wrote to System.out"), new Text("Writes to System.out"),
                    (Generator<?> systemOut) -> System.out.print("Hello Test")),
                has("generated", Generator::next, equalTo("Hello Test"))
            ),
                allOf(
                    has("generated", Generator::next, equalTo("Hello Test")),
                    successfully(new Text("Wrote to System.out"), new Text("Wrote to System.out"), new Text("Writes to System.out"),
                        (Generator<?> systemOut) -> System.out.print("More Output")),
                    // no further changes expected
                    has("generated", Generator::next, equalTo("Hello Test"))
                )));
    }

}
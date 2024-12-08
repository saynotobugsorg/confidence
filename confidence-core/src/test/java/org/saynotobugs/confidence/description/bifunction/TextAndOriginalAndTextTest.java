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

package org.saynotobugs.confidence.description.bifunction;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;

class TextAndOriginalAndTextTest
{
    @Test
    void testStringCtor()
    {
        assertThat(new TextAndOriginalAndText<Integer>("text1", "text2"),
            new AllOf<>(
                new Has<>(f -> f.value(new Text("abc")), new DescribesAs("text1 abc text2")),
                new Has<>(f -> f.value(678, new Text("abc")), new DescribesAs("text1 abc text2"))));
    }

    @Test
    void testDescriptionCtor()
    {
        assertThat(new TextAndOriginalAndText<Integer>(new Text("text1"), new Text("text2")),
            new AllOf<>(
                new Has<>(f -> f.value(new Text("abc")), new DescribesAs("text1 abc text2")),
                new Has<>(f -> f.value(678, new Text("abc")), new DescribesAs("text1 abc text2"))));
    }
}
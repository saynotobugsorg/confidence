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

package org.saynotobugs.confidence.rxjava3.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.IOException;

import static org.saynotobugs.confidence.Assertion.assertThat;


class SuppliesTest
{

    @Test
    void test()
    {
        assertThat(new Supplies<>(123),
            new AllOf<>(
                new Passes<>(() -> 123),
                new Fails<>(() -> null, "supplied <null>"),
                new Fails<>(() -> 1234, "supplied <1234>"),
                new Fails<>(() -> {throw new IOException("fail");}, "threw <java.io.IOException: fail>"),
                new HasDescription("supplies <123>")
            ));
    }

}
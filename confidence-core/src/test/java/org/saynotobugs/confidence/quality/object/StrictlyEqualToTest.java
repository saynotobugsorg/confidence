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

package org.saynotobugs.confidence.quality.object;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.allOf;

class StrictlyEqualToTest
{
    @Test
    void testSingle()
    {
        assertThat(new StrictlyEqualTo<>("123"),
            allOf(
                new Passes<>("123"),
                new Fails<Object>("456", "{ ...\n  was not equal to \"123\"\n  and\n  was not symmetric\n  and\n  had hashCode <51669> }"),
                new Fails<>(new Object()
                {
                    @Override
                    public boolean equals(Object obj)
                    {
                        return "123".equals(obj);
                    }

                    @Override
                    public int hashCode()
                    {
                        return "123".hashCode();
                    }

                    @Override
                    public String toString()
                    {
                        return "fakeObject1";
                    }
                }, "{ was not reflexive\n  ...\n  was not equal to \"123\"\n  ... }"),
                new Fails<>(new Object()
                {
                    @Override
                    public boolean equals(Object obj)
                    {
                        return "123".equals(obj) || obj == null;
                    }

                    @Override
                    public int hashCode()
                    {
                        return "456".hashCode();
                    }

                    @Override
                    public String toString()
                    {
                        return "fakeObject2";
                    }
                }, "{ was not reflexive\n  and\n  was equal to null\n  and\n  was not equal to \"123\"\n  ...\n  had hashCode <51669> }"),
                new Fails<>(new Object()
                {
                    @Override
                    public boolean equals(Object obj)
                    {
                        return "123".equals(obj) || obj == this;
                    }

                    @Override
                    public int hashCode()
                    {
                        return "123".hashCode();
                    }

                    @Override
                    public String toString()
                    {
                        return "fakeObject3";
                    }
                }, "{ ...\n  was not equal to \"123\"\n  ... }"),
                new HasDescription("{ is reflexive\n  and\n  is not equal to null\n  and\n  is equal to \"123\"\n  and\n  is symmetric\n  and\n  has hashCode <48690> like \"123\" }")
            ));
    }
}
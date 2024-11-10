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
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class StrictlyEqualToTest
{
    @Test
    void testSingle()
    {
        assertThat(new StrictlyEqualTo<>("123"),
            new AllOf<>(
                new Passes<>("123"),
                new Fails<Object>("456", "all of\n  ...\n  2: was not equal to \"123\"\n  3: was not symmetric\n  4: had hashCode 51669"),
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
                }, "all of\n  0: was not reflexive\n  ...\n  2: was not equal to \"123\"\n  ..."),
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
                }, "all of\n  0: was not reflexive\n  1: was equal to null\n  2: was not equal to \"123\"\n  ...\n  4: had hashCode 51669"),
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
                }, "all of\n  ...\n  2: was not equal to \"123\"\n  ..."),
                new HasDescription("all of\n  0: is reflexive\n  1: is not equal to null\n  2: is equal to \"123\"\n  3: is symmetric\n  4: has hashCode 48690 like \"123\"")
            ));
    }
}
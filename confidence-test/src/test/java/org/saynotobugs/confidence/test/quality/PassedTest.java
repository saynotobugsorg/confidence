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

package org.saynotobugs.confidence.test.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOf;

import static org.saynotobugs.confidence.Assertion.assertThat;


class PassedTest
{
    @Test
    void testWithString()
    {
        assertThat(new Passed("abc"),
            new AllOf<>(
                new Passes<>(new Pass(new Text("abc")), "both,\n" +
                    "  passes and\n" +
                    "  had description described as\n" +
                    "    ----\n" +
                    "    \"abc\"\n" +
                    "    ----"),
                new Fails<Assessment>(new Assessment()
                {
                    @Override
                    public boolean isSuccess()
                    {
                        return true;
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("oops");
                    }
                }, "both,\n  ... and\n  had description described as\n    ----\n    \"oops\"\n    ----"),
                new Fails<>(new Fail(new Text("failed")), "both,\n  failed and\n  had description described as\n    ----\n    \"failed\"\n    ----"),
                new HasDescription("both,\n  passes and\n  has description describes as\n    ----\n    \"abc\"\n    ----")
            ));
    }

    @Test
    void testWithQuality()
    {
        assertThat(new Passed(new DescribesAs("abc")),
            new AllOf<>(
                new Passes<>(new Pass(new Text("abc")), "both,\n" +
                    "  passes and\n" +
                    "  had description described as\n" +
                    "    ----\n" +
                    "    \"abc\"\n" +
                    "    ----"),
                new Fails<Assessment>(new Assessment()
                {
                    @Override
                    public boolean isSuccess()
                    {
                        return true;
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("oops");
                    }
                }, "both,\n  ... and\n  had description described as\n    ----\n    \"oops\"\n    ----"),
                new Fails<>(new Fail(new Text("failed")), "both,\n  failed and\n  had description described as\n    ----\n    \"failed\"\n    ----"),
                new HasDescription("both,\n  passes and\n  has description describes as\n    ----\n    \"abc\"\n    ----")
            ));
    }

}
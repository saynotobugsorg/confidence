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
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOf;

import static org.saynotobugs.confidence.Assertion.assertThat;


class PassesTest
{

    @Test
    void testWithDescription()
    {
        assertThat(new Passes<>(1, "1"),
            new AllOf<>(
                new Passes<Quality<Integer>>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass(new Text("1"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                }, "passed 1 with description \n" +
                    "  ----\n" +
                    "  describes as\n" +
                    "    ----\n" +
                    "    \"1\"\n" +
                    "    ----\n" +
                    "  ----"),
                new Fails<>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass(new Text("incorrect"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                },
                    "passed 1 with description \n  ----\n  incorrect\n  ----"),
                new Fails<>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Fail(new Text("failed"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                },
                    "failed 1 with description \n  ----\n  failed\n  ----"),
                new HasDescription("passes 1 with desciption \n  ----\n  describes as\n    ----\n    \"1\"\n    ----\n  ----")
            ));
    }

    @Test
    void testWithoutDescription()
    {
        assertThat(new Passes<>(1),
            new AllOf<>(
                new Passes<Quality<Integer>>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass(new Text("1"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                }, "passed 1 with description \n" +
                    "  ----\n" +
                    "  <anything>\n" +
                    "  ----"),

                new Fails<>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Fail(new Text("failed"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                },
                    "failed 1 with description \n  ----\n  failed\n  ----"),
                new HasDescription("passes 1 with desciption \n  ----\n  <anything>\n  ----")
            ));
    }

}
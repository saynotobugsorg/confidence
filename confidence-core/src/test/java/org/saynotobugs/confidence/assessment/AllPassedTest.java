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

package org.saynotobugs.confidence.assessment;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Failed;
import org.saynotobugs.confidence.test.quality.Passed;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AllPassedTest
{
    @Test
    public void testPass()
    {
        assertThat(new AllPassed(new Text(":"), new Text(","), new Pass(new Text("pass1")), new Pass(new Text("pass2"))),
            new Passed(new DescribesAs(":\n  pass1,\n  pass2")));
    }


    @Test
    public void testFail()
    {
        assertThat(new AllPassed(new Text(":"), new Text(","), new Pass(new Text("pass1")), new Fail(new Text("fail"))),
            new Is<>(new Failed(new DescribesAs(":\n  ...,\n  fail"))));
    }


    @Test
    public void testMultipleFail()
    {
        assertThat(new AllPassed(new Text(":"), new Text(","), new Fail(new Text("fail1")), new Pass(new Text("pass1")),
                new Fail(new Text("fail2")), new Fail(new Text("fail3"))),
            new Is<>(new Failed(new DescribesAs(":\n  fail1,\n  ...,\n  fail2,\n  fail3"))));
    }


    @Test
    public void testMultipleFail2()
    {
        assertThat(
            new AllPassed(new Text("["), new Text(","), new Text("]"), new Fail(new Text("fail1")), new Pass(new Text("pass1")),
                new Fail(new Text("fail2")), new Fail(new Text("fail3"))),
            new Is<>(new Failed(new DescribesAs("[\n  fail1,\n  ...,\n  fail2,\n  fail3\n]"))));
    }
}
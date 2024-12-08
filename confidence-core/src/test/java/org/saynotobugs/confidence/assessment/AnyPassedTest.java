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

import org.dmfs.jems2.iterable.Seq;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Failed;
import org.saynotobugs.confidence.test.quality.Passed;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AnyPassedTest
{

    @Test
    void testOnePass()
    {
        assertThat(new AnyPassed(new Text(":"), new Text(","), new Pass(new Text("pass1"))),
            new Passed(":\n  pass1"));
    }


    @Test
    void testMultiplePass()
    {
        assertThat(new AnyPassed(new Text(":"), new Text(","),
                new Pass(new Text("pass1")), new Pass(new Text("pass2")), new Pass(new Text("pass3"))),
            new Passed(":\n  pass1\n  pass2\n  pass3"));
    }


    @Test
    void testOneFail()
    {
        assertThat(new AnyPassed(new Text("any of"), new Text(","), new Fail(new Text("fail"))),
            new Failed(new DescribesAs("any of\n  fail")));
    }


    @Test
    void testOneFailOnePass()
    {
        assertThat(new AnyPassed(new Text("any of"), new Text(","), new Fail(new Text("fail")), new Pass(new Text("pass1"))),
            new Passed("any of\n  ...\n  pass1"));
    }


    @Test
    void testMultipleFail()
    {
        assertThat(new AnyPassed(new Text("any of"), new Text(","), new Fail(new Text("f1")), new Fail(new Text("f2")),
                new Fail(new Text("f3"))),
            new Failed(new DescribesAs("any of\n  f1,\n  f2,\n  f3")));
    }


    @Test
    void testMultipleFailWithExitTest()
    {
        assertThat(new AnyPassed(new Text("any of ["), new Text(","), new Text("]"), new Fail(new Text("f1")),
                new Fail(new Text("f2")),
                new Fail(new Text("f3"))),
            new Failed(new DescribesAs("any of [\n  f1,\n  f2,\n  f3\n]")));
    }


    @Test
    void testItrerableNoExitCtor()
    {
        assertThat(new AnyPassed(new Text("any of"), new Text(","), new Seq<>(new Fail(new Text("f1")), new Fail(new Text("f2")),
                new Fail(new Text("f3")))),
            new Failed(new DescribesAs("any of\n  f1,\n  f2,\n  f3")));
    }
}
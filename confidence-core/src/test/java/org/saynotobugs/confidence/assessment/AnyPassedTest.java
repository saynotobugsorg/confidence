package org.saynotobugs.confidence.assessment;

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
        assertThat(new AnyPassed(new Text("e"), new Text("x"), new Pass()),
            new Passed());
    }


    @Test
    void testMultiplePass()
    {
        assertThat(new AnyPassed(new Text("e"), new Text("x"), new Pass(), new Pass(), new Pass()),
            new Passed());
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
        assertThat(new AnyPassed(new Text("any of"), new Text("m"), new Fail(new Text("fail")), new Pass()),
            new Passed());
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
}
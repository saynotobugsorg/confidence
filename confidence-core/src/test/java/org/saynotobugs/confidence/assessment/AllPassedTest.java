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
        assertThat(new AllPassed(new Text("e"), new Text("x"), new Pass(), new Pass()),
            new Passed());
    }


    @Test
    public void testFail()
    {
        assertThat(new AllPassed(new Text(":"), new Text(","), new Pass(), new Fail(new Text("fail"))),
            new Is<>(new Failed(new DescribesAs(":\n  ...,\n  fail"))));
    }


    @Test
    public void testMultipleFail()
    {
        assertThat(new AllPassed(new Text(":"), new Text(","), new Fail(new Text("fail1")), new Pass(),
                new Fail(new Text("fail2")), new Fail(new Text("fail3"))),
            new Is<>(new Failed(new DescribesAs(":\n  fail1,\n  ...,\n  fail2,\n  fail3"))));
    }


    @Test
    public void testMultipleFail2()
    {
        assertThat(
            new AllPassed(new Text("["), new Text(","), new Text("]"), new Fail(new Text("fail1")), new Pass(),
                new Fail(new Text("fail2")), new Fail(new Text("fail3"))),
            new Is<>(new Failed(new DescribesAs("[\n  fail1,\n  ...,\n  fail2,\n  fail3\n]"))));
    }
}
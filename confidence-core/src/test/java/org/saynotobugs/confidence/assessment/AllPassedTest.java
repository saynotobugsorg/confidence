package org.saynotobugs.confidence.assessment;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.TextDescription;
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
        assertThat(new AllPassed(new TextDescription("e"), new TextDescription("x"), new Pass(), new Pass()),
            new Passed());
    }


    @Test
    public void testFail()
    {
        assertThat(new AllPassed(new TextDescription("e"), new TextDescription("x"), new Pass(), new Fail(new TextDescription("fail"))),
            new Is<>(new Failed(new DescribesAs("e...\n  fail"))));
    }


    @Test
    public void testMultipleFail()
    {
        assertThat(new AllPassed(new TextDescription("e"), new TextDescription("x"), new Fail(new TextDescription("fail1")), new Pass(),
                new Fail(new TextDescription("fail2")), new Fail(new TextDescription("fail3"))),
            new Is<>(new Failed(new DescribesAs("efail1\n  ...\n  fail2xfail3"))));
    }


    @Test
    public void testMultipleFail2()
    {
        assertThat(
            new AllPassed(new TextDescription("e"), new TextDescription("x"), new TextDescription("<"), new Fail(new TextDescription("fail1")), new Pass(),
                new Fail(new TextDescription("fail2")), new Fail(new TextDescription("fail3"))),
            new Is<>(new Failed(new DescribesAs("efail1\n  ...\n  fail2xfail3<"))));
    }
}
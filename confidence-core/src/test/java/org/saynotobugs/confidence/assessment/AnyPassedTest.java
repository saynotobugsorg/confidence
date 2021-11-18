package org.saynotobugs.confidence.assessment;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Failed;
import org.saynotobugs.confidence.test.quality.Passed;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AnyPassedTest
{

    @Test
    void testOnePass()
    {
        assertThat(new AnyPassed(new TextDescription("e"), new TextDescription("x"), new Pass()),
            new Passed());
    }


    @Test
    void testMultiplePass()
    {
        assertThat(new AnyPassed(new TextDescription("e"), new TextDescription("x"), new Pass(), new Pass(), new Pass()),
            new Passed());
    }


    @Test
    void testOneFail()
    {
        assertThat(new AnyPassed(new TextDescription("e"), new TextDescription("x"), new Fail(new TextDescription("faaail"))),
            new Failed(new DescribesAs("efaaail")));
    }


    @Test
    void testOneFailOnePass()
    {
        assertThat(new AnyPassed(new TextDescription("e"), new TextDescription("x"), new Fail(new TextDescription("faaail")), new Pass()),
            new Passed());
    }


    @Test
    void testMultipleFail()
    {
        assertThat(new AnyPassed(new TextDescription("e"), new TextDescription("x"), new Fail(new TextDescription("f1")), new Fail(new TextDescription("f2")),
                new Fail(new TextDescription("f3"))),
            new Failed(new DescribesAs("ef1xf2xf3")));
    }


    @Test
    void testMultipleFailWithExitTest()
    {
        assertThat(new AnyPassed(new TextDescription("e"), new TextDescription("x"), new TextDescription("--"), new Fail(new TextDescription("f1")),
                new Fail(new TextDescription("f2")),
                new Fail(new TextDescription("f3"))),
            new Failed(new DescribesAs("ef1xf2xf3--")));
    }
}
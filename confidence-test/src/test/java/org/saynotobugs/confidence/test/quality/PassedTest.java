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
    void test()
    {
        assertThat(new Passed(new DescribesAs("abc")),
            new AllOf<>(
                new Passes<>(new Pass(new Text("abc")), "abc"),
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
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
    void test()
    {
        assertThat(new Passes<>(1, 2, 3),
            new AllOf<>(
                new Passes<Quality<Integer>>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass();
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                }),
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
                    "matched\n  1 mismatched with \n    ----\n    failed\n    ----\n  and\n  2 mismatched with \n    ----\n    failed\n    ----\n  and\n  3 mismatched with \n    ----\n    failed\n    ----"),
                new HasDescription("matches 1\n  and\n  2\n  and\n  3")
            ));
    }

}
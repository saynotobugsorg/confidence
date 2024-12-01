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
        assertThat(new Passes<>(1, "1"),
            new AllOf<>(
                new Passes<Quality<Integer>>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass(new Text("123"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                }, "passed 1"),
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
                    "failed 1"),
                new HasDescription("passes 1")
            ));
    }

}
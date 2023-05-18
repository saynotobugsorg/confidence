package org.saynotobugs.confidence.test.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOf;

import static org.saynotobugs.confidence.Assertion.assertThat;


class FailsTest
{
    @Test
    void test()
    {
        assertThat(new Fails<>(123),
            new AllOf<>(
                new Passes<Quality<Integer>>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Fail(new Text("xyz"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("expects");
                    }
                }),
                new Fails<>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass();
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("pass");
                    }
                }, "<123> matched pass"),
                new HasDescription("mismatches <123> with diff <anything>")
            ));
    }


    @Test
    void testWithDescription()
    {
        assertThat(new Fails<>(123, "mismatch"),
            new AllOf<>(
                new Passes<Quality<Integer>>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Fail(new Text("mismatch"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("expects");
                    }
                }),
                new Fails<>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Fail(new Text("abc"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("pass");
                    }
                }, "<123> mismatched with diff described as\n  ----\n  \"abc\"\n  ----"),
                new Fails<>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass();
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("pass");
                    }
                }, "<123> matched pass"),
                new HasDescription("mismatches <123> with diff describes as\n  ----\n  \"mismatch\"\n  ----")
            ));
    }
}
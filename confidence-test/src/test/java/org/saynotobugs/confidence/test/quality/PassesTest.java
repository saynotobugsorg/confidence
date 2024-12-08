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
    void testWithDescription()
    {
        assertThat(new Passes<>(1, "1"),
            new AllOf<>(
                new Passes<Quality<Integer>>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass(new Text("1"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                }, "passed 1 with description \n" +
                    "  ----\n" +
                    "  describes as\n" +
                    "    ----\n" +
                    "    \"1\"\n" +
                    "    ----\n" +
                    "  ----"),
                new Fails<>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass(new Text("incorrect"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                },
                    "passed 1 with description \n  ----\n  incorrect\n  ----"),
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
                    "failed 1 with description \n  ----\n  failed\n  ----"),
                new HasDescription("passes 1 with desciption \n  ----\n  describes as\n    ----\n    \"1\"\n    ----\n  ----")
            ));
    }

    @Test
    void testWithoutDescription()
    {
        assertThat(new Passes<>(1),
            new AllOf<>(
                new Passes<Quality<Integer>>(new Quality<Integer>()
                {
                    @Override
                    public Assessment assessmentOf(Integer candidate)
                    {
                        return new Pass(new Text("1"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("passes");
                    }
                }, "passed 1 with description \n" +
                    "  ----\n" +
                    "  <anything>\n" +
                    "  ----"),

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
                    "failed 1 with description \n  ----\n  failed\n  ----"),
                new HasDescription("passes 1 with desciption \n  ----\n  <anything>\n  ----")
            ));
    }

}
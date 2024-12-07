package org.saynotobugs.confidence.test.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOf;

import java.util.regex.Pattern;

import static org.saynotobugs.confidence.Assertion.assertThat;


class HasDescriptionTest
{

    @Test
    void test()
    {
        assertThat(new HasDescription("abc"),
            new AllOf<>(
                new Passes<>(new Quality<Object>()
                {
                    @Override
                    public Assessment assessmentOf(Object candidate)
                    {
                        return new Pass(new Text("abd"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("abc");
                    }
                }, "description described as\n" +
                    "  ----\n" +
                    "  \"abc\"\n" +
                    "  ----"),
                new Fails<Quality<Object>>(new Quality<Object>()
                {
                    @Override
                    public Assessment assessmentOf(Object candidate)
                    {
                        return new Pass(new Text("anything"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("123");
                    }
                }, "description described as\n  ----\n  \"123\"\n  ----"),
                new HasDescription("description describes as\n  ----\n  \"abc\"\n  ----")
            ));
    }


    @Test
    void testPattern()
    {
        assertThat(new HasDescription(Pattern.compile("\\dabc\\d")),
            new AllOf<>(
                new Passes<>(new Quality<Object>()
                {
                    @Override
                    public Assessment assessmentOf(Object candidate)
                    {
                        return new Pass(new Text("abc"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("1abc2");
                    }
                }, "description described as\n" +
                    "  ----\n" +
                    "  matches pattern /\\\\dabc\\\\d/\n" +
                    "  ----"),
                new Fails<Quality<Object>>(new Quality<Object>()
                {
                    @Override
                    public Assessment assessmentOf(Object candidate)
                    {
                        return new Pass(new Text("abc"));
                    }


                    @Override
                    public org.saynotobugs.confidence.Description description()
                    {
                        return new Text("123");
                    }
                }, "description described as\n  ----\n  \"123\" mismatched pattern /\\\\dabc\\\\d/\n  ----"),
                new HasDescription("description describes as\n  ----\n  matches pattern /\\\\dabc\\\\d/\n  ----")
            ));
    }

}
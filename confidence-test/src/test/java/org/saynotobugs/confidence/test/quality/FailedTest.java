package org.saynotobugs.confidence.test.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.assessment.Pass;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;

import static org.saynotobugs.confidence.Assertion.assertThat;


class FailedTest
{

    @Test
    void test()
    {
        assertThat(new Failed(new EqualTo<>("<123>")),
            new AllOf<>(
                // currently, we can't match the matching case due to the recursive description
                //    new Matches<>(new Fail(new TextDescription("mismatched with description \n  ----\n  <123>\n  ----"))),
                new Fails<>(new Pass(), "passed")));
    }

}
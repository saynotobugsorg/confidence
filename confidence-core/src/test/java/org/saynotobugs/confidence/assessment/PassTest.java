package org.saynotobugs.confidence.assessment;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.object.Satisfies;
import org.saynotobugs.confidence.test.quality.DescribesAs;

import static org.saynotobugs.confidence.Assertion.assertThat;

class PassTest
{
    @Test
    void test()
    {
        assertThat(new Pass(new Text("pass")),
            new AllOf<>(
                new Satisfies<>(Assessment::isSuccess, new Text("passes")),
                new Has<>(Assessment::description, new DescribesAs("pass"))
            ));
    }

}
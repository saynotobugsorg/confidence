package org.saynotobugs.confidence.quality.optional;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.Optional;

import static org.saynotobugs.confidence.Assertion.assertThat;


class AbsentTest
{

    @Test
    void test()
    {
        assertThat(new Absent(),
            new AllOf<>(
                new Passes<Optional<Integer>>(Optional.empty()),
                new Fails<>(Optional.of(123), "<present <123>>"),
                new HasDescription("<absent>")
            ));
    }

}
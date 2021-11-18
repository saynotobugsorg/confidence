package org.saynotobugs.confidence.quality.comparator;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.Comparator;

import static java.util.Comparator.naturalOrder;
import static org.saynotobugs.confidence.Assertion.assertThat;


class ImposesOrderOfTest
{

    @Test
    void test()
    {
        assertThat(new ImposesOrderOf<>(1, 2, 3, 4, 5),
            new AllOf<>(
                new Passes<Comparator<Integer>>(naturalOrder()),
                // construct broken comparators
                new Fails<Comparator<Integer>>((l, r) -> l.equals(2) && r.equals(3) ? 1 : l - r,
                    "Comparator ...\n  compared elements <2> at index 1 and <3> at index 2 incorrectly to <1>\n  ..."),
                new Fails<Comparator<Integer>>((l, r) -> l.equals(3) && r.equals(2) ? -1 : l - r,
                    "Comparator ...\n  compared elements <3> at index 2 and <2> at index 1 incorrectly to <-1>\n  ..."),
                new Fails<Comparator<Integer>>((l, r) -> l.equals(2) && r.equals(2) ? 1 : l - r,
                    "Comparator ...\n  compared elements <2> at index 1 and <2> at index 1 incorrectly to <1>\n  ..."),
                new Fails<Comparator<Integer>>((l, r) -> l < 2 ? 1 : l - r,
                    "Comparator compared elements <1> at index 0 and <1> at index 0 incorrectly to <1>,\n" +
                        "  compared elements <1> at index 0 and <2> at index 1 incorrectly to <1>,\n" +
                        "  compared elements <1> at index 0 and <3> at index 2 incorrectly to <1>,\n" +
                        "  compared elements <1> at index 0 and <4> at index 3 incorrectly to <1>,\n" +
                        "  compared elements <1> at index 0 and <5> at index 4 incorrectly to <1>\n  ..."),
                new HasDescription("imposes the following order: <1>,\n  <2>,\n  <3>,\n  <4>,\n  <5>")));
    }

}
package org.saynotobugs.confidence.quality.iterable;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;
import org.saynotobugs.confidence.test.quality.PassesPostMutation;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class EachTest
{
    @Test
    void testSimpleCtor()
    {
        assertThat(new Each<>(new LessThan<>(3)),
            new AllOf<>(
                new Passes<>(asList(0, 1, 2), asList(0, 0, 0), emptyList()),
                new Fails<>(asList(1, 4, 2), "elements [...\n  1:  4\n  ...]"),
                new HasDescription("each element less than 3")
            ));
    }


    @Test
    void testMultipleCtor()
    {
        assertThat(new Each<>(new LessThan<>(3), new GreaterThan<>(0)),
            new AllOf<>(
                new Passes<Iterable<Integer>>(asList(1, 1, 2), asList(2, 2, 2), emptyList()),
                new Fails<Iterable<Integer>>(asList(0, 4, 2), "elements [0:  { ...\n    0 },\n  1:  { 4\n    ... }\n  ...]"),
                new Fails<Iterable<Integer>>(asList(1, 4, 2), "elements [...\n  1:  { 4\n    ... }\n  ...]"),
                new HasDescription("each element { less than 3\n  and\n  greater than 0 }")
            ));
    }


    @Test
    void testOneValueWithPostMutation()
    {
        assertThat(new Each<>(new EqualTo<>(1)),
            new PassesPostMutation<>(() -> new ArrayList<>(singletonList(1)), new Text("adding value 2"), list -> list.add(2)));
    }
}
package org.saynotobugs.confidence.quality.collection;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.Collection;
import java.util.HashSet;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.saynotobugs.confidence.Assertion.assertThat;


class HasSizeTest
{

    @Test
    void testIntCtor()
    {
        assertThat(new HasSize(3),
            new AllOf<>(
                new Passes<>(asList(1, 2, 3), "had size 3"),
                new Passes<>(new HashSet<>(asList("a", "b", "c")), "had size 3"),
                new Fails<>(emptyList(), "had size 0"),
                new Fails<Collection<?>>(asList(1, 2), "had size 2"),
                new Fails<Collection<?>>(asList(1, 2, 3, 4), "had size 4"),
                new HasDescription("has size 3")));
    }


    @Test
    void testMatcherCtor()
    {
        assertThat(new HasSize(new LessThan<>(4)),
            new AllOf<>(
                new Passes<>(asList(1, 2, 3), "had size 3"),
                new Passes<>(new HashSet<>(asList("a", "b")), "had size 2"),
                new Passes<>(emptyList(), "had size 0"),
                new Fails<>(asList(1, 2, 3, 4), "had size 4"),
                new Fails<Collection<?>>(asList(1, 2, 3, 4, 5), "had size 5"),
                new HasDescription("has size less than 4")));
    }

}
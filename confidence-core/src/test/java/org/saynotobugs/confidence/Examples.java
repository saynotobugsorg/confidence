package org.saynotobugs.confidence;

import org.dmfs.jems2.iterable.Seq;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.assessment.PassIf;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.NumberDescription;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.quality.comparable.GreaterThan;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.compat.Hamcrest;
import org.saynotobugs.confidence.quality.composite.*;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.quality.iterable.Contains;
import org.saynotobugs.confidence.quality.iterable.Each;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.quality.iterable.IteratesInAnyOrder;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.quality.object.HasToString;
import org.saynotobugs.confidence.quality.optional.Present;
import org.saynotobugs.confidence.quality.supplier.Supplies;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.contains;


@Disabled
public final class Examples
{
    @Test
    void testSimple()
    {
        assertThat(asList(1, 2, 10, 3, 4), contains(1, 32, 11, 4));
    }


    @Test
    void testEqualTo()
    {
        assertThat(1, new EqualTo<>(2));
    }


    @Test
    void testEqualToArray()
    {
        assertThat(new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, new int[] { 6, 7, 8, 9 } },
            new EqualTo<>(new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, new int[] { 6, 7, 8 } }));
        assertThat(new Integer[][] { new Integer[] { 1, 2, 3 }, new Integer[] { 4, 5, 6 }, new Integer[] { 6, 7, 8 } },
            new EqualTo<>(new Integer[][] { new Integer[] { 1, 2, 3 }, new Integer[] { 4, 5, 6 }, new Integer[] { 6, 7, 8 } }));
        assertThat(new Integer[][] { new Integer[] { 1, 2, 3 }, new Integer[] { 4, 55, 6 }, new Integer[] { 6, 7, 88 } },
            new EqualTo<>(new Integer[][] { new Integer[] { 1, 2, 3 }, new Integer[] { 4, 5, 6 }, new Integer[] { 6, 7, 8 } }));
    }


    @Test
    void testLessThan()
    {
        assertThat(10, new LessThan<>(2));
    }


    @Test
    void testIterableLessThan()
    {
        assertThat(new Seq<>(1, 2, 10, 3, 4), new Iterates<>(new LessThan<>(5), new LessThan<>(5), new LessThan<>(5), new LessThan<>(5), new LessThan<>(5)));
    }


    @Test
    void testEachLessThan()
    {
        assertThat(new Seq<>(1, 2, 10, 3, 4), new Each<>(new LessThan<>(5)));
    }


    @Test
    void testIterable()
    {
        assertThat(asList(1, 2, 3, 4, 5, 6, 7), new Iterates<>(1, 2, 5, 6, 8, 9));
    }


    @Test
    void testContainsOne()
    {
        assertThat(new Seq<>(1, 2, 10, 3, 4), new Contains<>(15));
    }


    @Test
    void testContainsMany()
    {
        assertThat(new Seq<>(1, 2, 10, 3, 4), new Contains<>(1, 32, 11, 4));
    }


    @Test
    void testIterableOfOptionals()
    {
        assertThat(new Seq<>(of(1), of(2), of(3), empty()), new Iterates<>(new Present<>(1), new Present<>(2), new Present<>(4)));
    }


    @Test
    void testIn()
    {
        assertThat(3, new Is<>(new In<>(4, 5, 6)));
    }


    @Test
    void testInMatchers()
    {
        assertThat(3, new Is<>(new In<>(new LessThan<>(2), new GreaterThan<>(6), new EqualTo<>(5))));
    }


    @Test
    void testInCollection()
    {
        assertThat(3, new Is<>(new In<>(asList(4, 5, 6))));
    }


    @Test
    void testNotIn()
    {
        assertThat(3, new Is<>(new Not<>(new In<>(4, 3, 6))));
    }


    @Test
    void containsInAnyOrder()
    {
        assertThat(new Seq<>(3, 5, 6, 3), new IteratesInAnyOrder<>(6, 3, 5, 11));
    }


    @Test
    void testNestedIterable()
    {
        assertThat(new Seq<>(new Seq<>(1, 2, 3), new Seq<>(4, 4, 5), new Seq<>(7, 8, 9)),
            new Iterates<>(new Iterates<>(1, 2, 3), new Iterates<>(4, 5, 6), new Iterates<>(7, 8, 9)));
    }


    @Test
    void testHaving()
    {
        assertThat(123, new Has<>("hashcode", Objects::hashCode, new EqualTo<>(125)));
    }


    @Test
    void testHaving2()
    {
        assertThat(123, new Has<>(new TextDescription("has hash"), new TextDescription("had hash"), Objects::hashCode, new EqualTo<>(125)));
    }


    @Test
    void testHamcrestCompat()
    {
        assertThat(new Seq<>(1, 2, 5, 10, 11), new Hamcrest<>(Matchers.contains(1, 2, 3, 10, 12)));
    }


    @Test
    void testSupplies()
    {
        assertThat(() -> new Seq<>(1, 2, 5), new Supplies<>(new Iterates<>(1, 2, 3)));
    }


    @Test
    void testMatches()
    {
        assertThat(new Quality<String>()
        {
            @Override
            public Assessment assessmentOf(String candidate)
            {
                return new PassIf(candidate.length() == 3, new Delimited(
                    new TextDescription("String length was"),
                    new NumberDescription(candidate.length())));
            }


            @Override
            public Description description()
            {
                return new TextDescription("String with length 3");
            }
        }, new Passes<>(
            "123",
            "456",
            "7654",
            "12"
        ));
    }


    @Test
    void testMismatches()
    {
        assertThat(new Quality<String>()
                   {
                       @Override
                       public Assessment assessmentOf(String candidate)
                       {
                           return new PassIf(candidate.length() == 3, new Delimited(
                               new TextDescription("String length was"),
                               new NumberDescription(candidate.length())));
                       }


                       @Override
                       public Description description()
                       {
                           return new TextDescription("String with length 3");
                       }
                   },
            new AllOf<>(
                new Fails<>("1235"),
                new Fails<>("45634"),
                new Fails<>("765"),
                new Fails<>("1234", "String length was <3>"),
                new Fails<>("122"),
                new Fails<>("seeewe")
            ));
    }


    @Test
    void testHasToString()
    {
        assertThat("123", new HasToString(new EqualTo<>("123")));
        assertThat("124", new HasToString("123"));
    }


    @Test
    void parallel()
    {
        assertThat(new AtomicInteger(0), new Parallel<>(1000,
            new Has<>("modulo 50", i -> i.incrementAndGet() % 50, new LessThan<>(48))));
    }


    @Test
    void testNotAnyOf()
    {
        assertThat(new Seq<>(1, 2, 10, 11, 4),
            new Each<>(new Not<>(new AnyOf<>(new EqualTo<>(77), new EqualTo<>(10), new EqualTo<>(22), new GreaterThan<>(9)))));
    }


    @Test
    void testNoneOf()
    {
        assertThat(new Seq<>(1, 2, 10, 11, 4), new Each<>(new NoneOf<>(new EqualTo<>(77), new EqualTo<>(10), new EqualTo<>(22), new GreaterThan<>(9))));
    }


    @Test
    void testAnyOf()
    {
        assertThat(new Seq<>(1, 2, 10, 11, 4),
            new Each<>(new AnyOf<>(new EqualTo<>(77), new EqualTo<>(12), new EqualTo<>(22), new GreaterThan<>(99))));
    }


    @Test
    void testEachIn()
    {
        assertThat(new Seq<>(1, 2, 10, 11, 4),
            new Each<>(new In<>(new EqualTo<>(77), new EqualTo<>(10), new EqualTo<>(22), new GreaterThan<>(9))));
    }

}

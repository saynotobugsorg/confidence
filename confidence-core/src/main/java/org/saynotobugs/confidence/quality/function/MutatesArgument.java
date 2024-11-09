package org.saynotobugs.confidence.quality.function;

import org.dmfs.jems2.Generator;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.description.LiteralDescription;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.valuedescription.Value;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * {@link Quality} of a non-pure {@link Function} that mutates its argument when called.
 * <h2>Examples</h2>
 * <p>
 * The following test ensures the function {@code list->list:add} adds an element to a given {@link List} and returns
 * {@code true} when the list content has been changed.
 * <pre>{@code
 * assertThat(list -> list::add,
 *     mutatesArgument(
 *         ArrayList::new,
 *         soIt(iterates("a")),
 *         when(maps("a", to(true)))));
 * }</pre>
 * <p>
 * A common use case is testing mutation of Constructor arguments of mutable classes.
 * Consider a {@code ListAppender} {@link Consumer} that appends any value to the given list.
 *
 * <pre>{@code
 * assertThat(list -> new ListAppender(list),
 *     mutatesArgument(
 *         ArrayList::new,
 *         soIt(iterates("a")),
 *         when(consumerThatAccepts("a"))));
 * }</pre>
 */
@StaticFactories(
    value = "Function",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class MutatesArgument<Argument, Type> implements Quality<Function<Argument, Type>>
{
    private final Generator<? extends Argument> mArgumentGenerator;
    private final Quality<? super Argument> mArgumentQuality;
    private final Quality<? super Type> mDelegateQuality;


    public MutatesArgument(
        Generator<? extends Argument> argumentGenerator,
        Quality<? super Argument> argumentQuality,
        Quality<? super Type> delegateQuality)
    {
        mArgumentGenerator = argumentGenerator;
        mArgumentQuality = argumentQuality;
        mDelegateQuality = delegateQuality;
    }


    @Override
    public Assessment assessmentOf(Function<Argument, Type> candidate)
    {
        Argument argument = mArgumentGenerator.next();
        Assessment delegateAssessment = mDelegateQuality.assessmentOf(candidate.apply(argument));
        return new AllPassed(
            LiteralDescription.EMPTY,
            LiteralDescription.SPACE,
            mArgumentQuality.assessmentOf(argument),
            delegateAssessment
        );
    }

    @Override
    public Description description()
    {
        return new Spaced(
            new Text("mutates argument"),
            new Value(mArgumentGenerator.next()),
            mArgumentQuality.description(),
            mDelegateQuality.description()
        );
    }
}

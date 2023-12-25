package org.saynotobugs.confidence.rxjava3;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.Duration.ofSeconds;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;
import static org.saynotobugs.confidence.rxjava3.RxJava3.when;
import static org.saynotobugs.confidence.rxjava3.RxJava3.*;


public final class Examples
{
    @Test
    void testInterval()
    {
        assertThat((TestScheduler scheduler) -> Flowable.interval(10, SECONDS, scheduler).take(10),
            is(publisherThat(
                immediately(emitsNothing()),
                within(ofSeconds(10), emits(0L)),
                within(ofSeconds(10), emits(1L)),
                within(ofSeconds(10), emits(2L)),
                within(ofSeconds(10), emits(3L)),
                within(ofSeconds(10), emits(4L)),
                within(ofSeconds(10), emits(5L)),
                within(ofSeconds(10), emits(6L)),
                within(ofSeconds(10), emits(7L)),
                within(ofSeconds(10), emits(8L)),
                within(ofSeconds(10), emits(9L)),
                immediately(completes())
            )));
    }


    @Test
    void testTrigger()
    {
        AtomicInteger value = new AtomicInteger(5);
        assertThat((TestScheduler scheduler) -> Flowable.interval(10, SECONDS, scheduler).map(i -> value.get()).take(10),
            is(publisherThat(
                immediately(emitsNothing()),
                within(ofSeconds(10), emits(5)),
                within(ofSeconds(10), emits(5)),
                within(ofSeconds(10), emits(5)),
                within(ofSeconds(10), emits(5)),
                when("Value is set to 10", () -> value.set(10),
                    within(ofSeconds(10), emits(10))),
                within(ofSeconds(10), emits(10)),
                within(ofSeconds(10), emits(10)),
                within(ofSeconds(10), emits(10)),
                within(ofSeconds(10), emits(10)),
                within(ofSeconds(10), emits(10)),
                immediately(completes())
            )));
    }


    @Test
    void testMaybeError()
    {
        assertThat((TestScheduler scheduler) -> Maybe.error(IOException::new).delay(10, SECONDS, scheduler),
            is(maybeThat(immediately(errors(IOException.class)))));
    }


    @Test
    void testMaybeDelayedError()
    {
        assertThat((TestScheduler scheduler) -> Maybe.empty().delay(10, SECONDS, scheduler).switchIfEmpty(Maybe.error(IOException::new)),
            is(maybeThat(
                immediately(isAlive()),
                within(ofSeconds(10), errors(IOException.class)))));
    }


    @Test
    void testFlowableTransformer()
    {
        assertThat((TestScheduler scheduler) -> (Flowable<Integer> upstream) -> upstream.delay(10, SECONDS, scheduler),
            transformsFlowable(
                upstream(emit(1, 2, 3)),
                downstream(
                    within(ofSeconds(9), emitsNothing()),
                    within(ofSeconds(10), emits(1)),
                    within(ofSeconds(10), emits(2)),
                    within(ofSeconds(10), emits(3)),
                    isAlive()),
                upstream(complete()),
                downstream(within(ofSeconds(10), completes()))
            ));
    }


    @Test
    void testObservableTransformer()
    {
        assertThat((TestScheduler scheduler) -> (Observable<Integer> upstream) -> upstream.delay(10, SECONDS, scheduler),
            transformsObservable(
                upstream(emit(1, 2, 3)),
                downstream(
                    within(ofSeconds(9), emitsNothing()),
                    within(ofSeconds(10), emits(1)),
                    within(ofSeconds(10), emits(2)),
                    within(ofSeconds(10), emits(3)),
                    isAlive()),
                upstream(complete()),
                downstream(within(ofSeconds(10), completes()))
            ));
    }


    @Test
    void testTrivialFlowableTransformer()
    {
        assertThat((Flowable<Integer> upstream) -> upstream,
            unscheduled(transformsFlowable(
                upstream(emit(1, 2, 3)),
                downstream(emits(1, 2, 3)),
                upstream(complete()),
                downstream(immediately(completes()))
            )));
    }


    @Test
    void testFlowableTransformerWithError()
    {
        assertThat((Flowable<Integer> upstream) -> upstream,
            unscheduled(transformsFlowable(
                upstream(emit(1, 2, 3)),
                downstream(emits(1, 2, 3)),
                upstream(error(IOException::new)),
                downstream(immediately(errors(instanceOf(IOException.class))))
            )));
    }


    @Test
    void testSingleTransformer()
    {
        assertThat((TestScheduler scheduler) -> (Single<Integer> upstream) -> upstream.delay(10, SECONDS, scheduler),
            allOf(
                transformsSingle(
                    upstream(emit(123)),
                    downstream(within(ofSeconds(9), emitsNothing())),
                    downstream(within(ofSeconds(10), completesWith(123)))),
                RxJava3.<Integer, Integer>transformsSingle(
                    upstream(error(IOException::new)),
                    downstream(immediately(errors(IOException.class))))));
    }


    @Test
    void testMaybeTransformer()
    {
        assertThat((TestScheduler scheduler) -> (Maybe<Integer> upstream) -> upstream.delay(10, SECONDS, scheduler),
            allOf(
                transformsMaybe(
                    upstream(completeWith(123)),
                    downstream(within(ofSeconds(9), emitsNothing())),
                    downstream(within(ofSeconds(10), completesWith(123)))),
                RxJava3.<Integer, Integer>transformsMaybe(
                    upstream(complete()),
                    downstream(
                        within(ofSeconds(9), isAlive()),
                        within(ofSeconds(10), completes()),
                        hasNoFurtherValues())),
                RxJava3.<Integer, Integer>transformsMaybe(
                    upstream(error(IOException::new)),
                    downstream(immediately(errors(IOException.class))))));
    }


    @Test
    void testCompletableTransformer()
    {
        assertThat((TestScheduler scheduler) -> (Completable upstream) -> upstream.delay(10, SECONDS, scheduler),
            allOf(
                transformsCompletable(
                    upstream(complete()),
                    downstream(within(ofSeconds(9), isAlive())),
                    downstream(within(ofSeconds(10), completes()))),
                transformsCompletable(
                    upstream(error(IOException::new)),
                    downstream(immediately(errors(IOException.class))))));
    }


    @Test
    void testCancelledFlowableTransformer()
    {
        assertThat((Flowable<Integer> upstream) -> upstream,
            unscheduled(transformsFlowable(
                upstream(emit(1, 2, 3)),
                downstream(emits(1, 2, 3)),
                downstream(disposal()),
                upstream(not(hasSubscribers()))
            )));
    }

}

package org.saynotobugs.confidence.rxjava3.function;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Scheduler;
import org.dmfs.jems2.bifunction.DelegatingBiFunction;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;
import org.saynotobugs.confidence.rxjava3.adapters.EmitterAdapter;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class At<T> extends DelegatingBiFunction<Scheduler, Flowable<T>, Flowable<T>>
{

    /**
     * Schedules the given event at the given absolute time.
     */
    public At(Instant instant, Quality<? super RxSubjectAdapter<T>> delegate)
    {
        this(instant.toEpochMilli(), delegate);
    }


    /**
     * Schedules an event at the given timestamp.
     */
    public At(long timestamp, Quality<? super RxSubjectAdapter<T>> delegate)
    {
        super((scheduler, flowable) ->
            flowable.mergeWith(
                Completable.timer(timestamp - scheduler.now(TimeUnit.MILLISECONDS), TimeUnit.MILLISECONDS, scheduler)
                    .andThen(Flowable.create(emitter -> {
                            delegate.assessmentOf(new EmitterAdapter<>(emitter));
                            emitter.onComplete();
                        },
                        BackpressureStrategy.BUFFER))));
    }

}

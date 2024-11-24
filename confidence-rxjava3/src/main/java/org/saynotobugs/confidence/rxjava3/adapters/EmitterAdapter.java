package org.saynotobugs.confidence.rxjava3.adapters;

import io.reactivex.rxjava3.core.Emitter;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;

public final class EmitterAdapter<T> implements RxSubjectAdapter<T>
{
    private final Emitter<T> mEmitter;

    public EmitterAdapter(Emitter<T> emitter)
    {
        mEmitter = emitter;
    }

    @Override
    public void onNext(T next)
    {
        mEmitter.onNext(next);
    }

    @Override
    public void onComplete()
    {
        mEmitter.onComplete();
    }

    @Override
    public void onError(Throwable error)
    {
        mEmitter.onError(error);
    }

    @Override
    public boolean hasSubscribers()
    {
        return false; // we don't really know
    }

}

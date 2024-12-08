package org.saynotobugs.confidence.utils;

import org.dmfs.jems2.optional.LazyDelegatingOptional;
import org.dmfs.jems2.optional.Present;

import java.util.Iterator;

import static org.dmfs.jems2.optional.Absent.absent;

public final class Last<T> extends LazyDelegatingOptional<T>
{
    public Last(Iterable<? extends T> delegates)
    {
        super(() ->
            {
                Iterator<? extends T> iterator = delegates.iterator();
                while (iterator.hasNext())
                {
                    T next = iterator.next();
                    if (!iterator.hasNext())
                    {
                        return new Present<>(next);
                    }
                }
                return absent();
            }
        );
    }
}

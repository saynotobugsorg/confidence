package org.saynotobugs.confidence.utils;

import org.dmfs.jems2.Optional;
import org.dmfs.jems2.optional.LazyDelegatingOptional;
import org.dmfs.jems2.optional.Present;

import static org.dmfs.jems2.optional.Absent.absent;

public final class Last<T> extends LazyDelegatingOptional<T>
{
    public Last(Iterable<? extends T> delegates)
    {
        super(() ->
            {
                Optional<T> last = absent();
                for (T value : delegates)
                {
                    last = new Present<>(value);
                }
                return last;
            }
        );
    }
}

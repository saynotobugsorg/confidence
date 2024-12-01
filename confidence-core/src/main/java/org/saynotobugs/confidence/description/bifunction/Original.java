package org.saynotobugs.confidence.description.bifunction;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.function.DelegatingFunction;
import org.saynotobugs.confidence.Description;

public final class Original<T> extends DelegatingFunction<Description, Description>
    implements BiFunction<T, Description, Description>
{
    public Original()
    {
        super((description) -> description);
    }

    @Override
    public Description value(T t, Description description)
    {
        return this.value(description);
    }
}

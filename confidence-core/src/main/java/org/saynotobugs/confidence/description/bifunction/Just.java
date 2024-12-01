package org.saynotobugs.confidence.description.bifunction;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.function.DelegatingFunction;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Text;

public final class Just<T> extends DelegatingFunction<Description, Description>
    implements BiFunction<T, Description, Description>
{
    public Just(Description delegate)
    {
        super((description) -> delegate);
    }

    public Just(String text)
    {
        super((description) -> new Text(text));
    }

    @Override
    public Description value(T t, Description description)
    {
        return this.value(description);
    }
}

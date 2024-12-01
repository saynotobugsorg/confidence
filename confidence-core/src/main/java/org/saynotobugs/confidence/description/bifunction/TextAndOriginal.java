package org.saynotobugs.confidence.description.bifunction;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.function.DelegatingFunction;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;

public final class TextAndOriginal<T> extends DelegatingFunction<Description, Description>
    implements BiFunction<T, Description, Description>
{
    public TextAndOriginal(String text)
    {
        this(new Text(text));
    }

    public TextAndOriginal(Description text)
    {
        super((description) -> new Spaced(text, description));
    }

    @Override
    public Description value(T t, Description description)
    {
        return value(description);
    }
}

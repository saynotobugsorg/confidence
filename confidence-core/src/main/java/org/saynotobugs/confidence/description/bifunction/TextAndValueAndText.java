package org.saynotobugs.confidence.description.bifunction;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.function.DelegatingFunction;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;

public final class TextAndValueAndText<T> extends DelegatingFunction<T, Description>
    implements BiFunction<T, Description, Description>
{
    public TextAndValueAndText(String beforeText, String afterText)
    {
        this(new Text(beforeText), new Text(afterText));
    }

    public TextAndValueAndText(Description beforeDescription, Description afterDescription)
    {
        super((value) -> new Spaced(beforeDescription, new Value(value), afterDescription));
    }

    @Override
    public Description value(T t, Description description)
    {
        return value(t);
    }
}

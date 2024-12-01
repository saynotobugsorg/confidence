package org.saynotobugs.confidence.description.bifunction;

import org.dmfs.jems2.BiFunction;
import org.dmfs.jems2.function.DelegatingFunction;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;

public final class TextAndOriginalAndText<T> extends DelegatingFunction<Description, Description>
    implements BiFunction<T, Description, Description>
{
    public TextAndOriginalAndText(String beforeText, String afterText)
    {
        this(new Text(beforeText), new Text(afterText));
    }

    public TextAndOriginalAndText(Description beforeText, Description afterText)
    {
        super((description) -> new Spaced(beforeText, description, afterText));
    }

    @Override
    public Description value(T t, Description description)
    {
        return value(description);
    }
}

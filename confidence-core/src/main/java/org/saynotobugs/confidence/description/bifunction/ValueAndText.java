package org.saynotobugs.confidence.description.bifunction;

import org.dmfs.jems2.bifunction.DelegatingBiFunction;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;

public final class ValueAndText<T> extends DelegatingBiFunction<T, Description, Description>
{
    public ValueAndText(String text)
    {
        this(new Text(text));
    }

    public ValueAndText(Description text)
    {
        super((value, description) -> new Spaced(new Value(value), text));
    }
}

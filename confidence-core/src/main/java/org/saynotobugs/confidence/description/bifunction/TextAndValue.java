package org.saynotobugs.confidence.description.bifunction;

import org.dmfs.jems2.Function;
import org.dmfs.jems2.bifunction.DelegatingBiFunction;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;

public final class TextAndValue<T> extends DelegatingBiFunction<T, Description, Description>
{

    public TextAndValue(String text)
    {
        this(text, Value::new);
    }

    public TextAndValue(String text, Function<? super T, Description> valueDescription)
    {
        super((value, description) -> new Spaced(new Text(text), valueDescription.value(value)));
    }
}

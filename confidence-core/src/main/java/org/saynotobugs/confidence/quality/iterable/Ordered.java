/*
 * Copyright 2023 dmfs GmbH
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.AllPassed;
import org.saynotobugs.confidence.assessment.PassIf;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.valuedescription.Value;
import org.saynotobugs.confidence.quality.composite.QualityComposition;

import java.util.Comparator;
import java.util.Iterator;

import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;

@StaticFactories(
    value = "Iterable",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class Ordered<T> extends QualityComposition<Iterable<T>>
{
    /**
     * A {@link Quality} that describes an {@link Iterable} that iterates elements in the
     * order imposed by the given {@link Comparator}.
     */
    public Ordered(Comparator<? super T> comparator)
    {
        this("", comparator);
    }

    /**
     * A {@link Quality} that describes an {@link Iterable} that iterates elements in the
     * order imposed by the given {@link Comparator}.
     * <p>
     * This constructor also takes a description of the {@link Comparator}.
     */
    public Ordered(String description, Comparator<? super T> comparator)
    {
        super(actual -> new AllPassed(new Text("["), NEW_LINE, new Text("]"), () -> new Iterator<Assessment>()
            {
                private final Iterator<T> mDelegate = actual.iterator();
                private T mCurrent = mDelegate.hasNext() ? mDelegate.next() : null;
                private int mIndex = 0;

                @Override
                public boolean hasNext()
                {
                    return mDelegate.hasNext();
                }

                @Override
                public Assessment next()
                {
                    T next = mDelegate.next();
                    Assessment result = new PassIf(comparator.compare(mCurrent, next) <= 0,
                        new Spaced(
                            new Text(mIndex + ":"),
                            new Value(mCurrent),
                            new Text(">"),
                            new Text(++mIndex + ":"),
                            new Value(next)));
                    mCurrent = next;
                    return result;
                }
            }),
            new Text("ordered" + (description.isEmpty() ? "" : " " + description)));
    }
}

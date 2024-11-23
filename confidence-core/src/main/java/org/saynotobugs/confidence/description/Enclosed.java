/*
 * Copyright 2022 dmfs GmbH
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

package org.saynotobugs.confidence.description;

import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Scribe;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * A {@link Description} that encloses a delegate in given openening an closing values. Alternatively a dedicated
 * empty value can be written if the delegate {@link Description} is empty.
 *
 * <h2>Example</h2>
 * <pre>{@code
 * new Enclosed("[", delegate, "]", "ø");
 * }</pre>
 * Results in a description that contains the delegate in {@code [...]} if non-empty and just {@code ø} otherwise.
 */
public final class Enclosed implements Description
{
    private final Description mPrefix;
    private final Description mSuffix;
    private final Description mEmptyFallback;
    private final Description mDelegate;


    public Enclosed(String prefix, Description delegate, String suffix)
    {
        this(new Text(prefix), delegate, new Text(suffix));
    }


    public Enclosed(String prefix, Description delegate, String suffix, String emptyFallback)
    {
        this(new Text(prefix), delegate, new Text(suffix), new Text(emptyFallback));
    }


    public Enclosed(Description prefix, Description delegate, Description suffix)
    {
        this(prefix, delegate, suffix, new Composite(prefix, suffix));
    }

    public Enclosed(Description prefix, Description delegate, Description suffix, Description emptyFallback)
    {
        mPrefix = prefix;
        mSuffix = suffix;
        mEmptyFallback = emptyFallback;
        mDelegate = delegate;
    }


    @Override
    public void describeTo(Scribe scribe)
    {
        AtomicBoolean prefixWritten = new AtomicBoolean();
        Prefixed prefixed = new Prefixed(scribe, () -> mPrefix.describeTo(scribe), prefixWritten);
        mDelegate.describeTo(prefixed);
        if (prefixWritten.get())
        {
            mSuffix.describeTo(scribe);
        }
        else
        {
            mEmptyFallback.describeTo(scribe);
        }
    }

    /**
     * A Scribe that writes a prefix before (and only) if anything else is written.
     */
    private static final class Prefixed implements Scribe
    {
        private final Scribe mDelegate;
        private final Runnable mWritePrefix;
        private final AtomicBoolean mPrefixWritten;

        private Prefixed(Scribe delegate, Runnable writePrefix, AtomicBoolean prefixWritten)
        {
            mDelegate = delegate;
            mWritePrefix = writePrefix;
            mPrefixWritten = prefixWritten;
        }


        @Override
        public Scribe indented()
        {
            return new Prefixed(mDelegate.indented(), mWritePrefix, mPrefixWritten);
        }

        @Override
        public Scribe append(CharSequence charSequence)
        {
            writePrefix();
            mDelegate.append(charSequence);
            return this;
        }

        @Override
        public Scribe newLine()
        {
            writePrefix();
            mDelegate.newLine();
            return this;
        }

        private void writePrefix()
        {
            if (mPrefixWritten.compareAndSet(false, true))
            {
                mWritePrefix.run();
            }
        }
    }
}

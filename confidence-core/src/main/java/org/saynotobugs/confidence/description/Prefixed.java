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
 * A {@link Description} that puts another {@link Description} in front of a delegtate. Unlike {@link Composite}, which
 * can do that, this also takes an alterntive value in case the delegate {@link Description} is empty.
 *
 * <h2>Non-Empty Delegate Example</h2>
 * <pre>{@code
 * new Prefixed(NEW_LINE, new Text("abc"), EMPTY);
 * }</pre>
 * results in {@code "\nabc"}.
 *
 * <h2>Empty Delegate Example</h2>
 * <pre>{@code
 * new Prefixed(NEW_LINE, EMPTY, EMPTY);
 * }</pre>
 * results in nothing being appended to the description (not even an empty String).
 */
public final class Prefixed implements Description
{
    private final Description mPrefix;
    private final Description mEmpty;
    private final Description mDelegate;

    public Prefixed(String prefix, Description delegate, String emptyDelegatePrefix)
    {
        this(new Text(prefix), delegate, new Text(emptyDelegatePrefix));
    }

    public Prefixed(Description prefix, Description delegate, Description emptyDelegatePrefix)
    {
        mPrefix = prefix;
        mEmpty = emptyDelegatePrefix;
        mDelegate = delegate;
    }


    @Override
    public void describeTo(Scribe scribe)
    {
        AtomicBoolean prefixWritten = new AtomicBoolean();
        PrefixedScribe prefixedScribe = new PrefixedScribe(scribe, () -> mPrefix.describeTo(scribe), prefixWritten);
        mDelegate.describeTo(prefixedScribe);
        if (!prefixWritten.get())
        {
            mEmpty.describeTo(scribe);
        }
    }

    /**
     * A Scribe that writes a prefix before (and only) if anything else is written.
     */
    private static final class PrefixedScribe implements Scribe
    {
        private final Scribe mDelegate;
        private final Runnable mWritePrefix;
        private final AtomicBoolean mPrefixWritten;

        private PrefixedScribe(Scribe delegate, Runnable writePrefix, AtomicBoolean prefixWritten)
        {
            mDelegate = delegate;
            mWritePrefix = writePrefix;
            mPrefixWritten = prefixWritten;
        }


        @Override
        public Scribe indented()
        {
            return new PrefixedScribe(mDelegate.indented(), mWritePrefix, mPrefixWritten);
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

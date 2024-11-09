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
 * new PrefixedScribe(NEW_LINE, delegate, EMPTY);
 * }</pre>
 */
public final class Prefixed implements Description
{
    private final Description mPrefix;
    private final Description mEmpty;
    private final Description mDelegate;


    public Prefixed(String prefix, Description delegate)
    {
        this(new Text(prefix), delegate);
    }


    public Prefixed(String prefix, Description delegate, String empty)
    {
        this(new Text(prefix), delegate, new Text(empty));
    }


    public Prefixed(Description prefix, Description delegate)
    {
        this(prefix, delegate, prefix);
    }

    public Prefixed(Description prefix, Description delegate, Description empty)
    {
        mPrefix = prefix;
        mEmpty = empty;
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

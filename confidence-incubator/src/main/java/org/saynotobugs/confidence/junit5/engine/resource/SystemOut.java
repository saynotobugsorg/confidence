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

package org.saynotobugs.confidence.junit5.engine.resource;

import org.dmfs.jems2.Generator;
import org.dmfs.jems2.Single;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.junit5.engine.Resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;


/**
 * A{@link Resource} that provides everything written to {@link System#out until closed}.
 */
@StaticFactories(value = "Resources", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class SystemOut implements Resource<Generator<String>>
{

    private Single<? extends Generator<String>> mResourceGenerator;
    private Runnable mCleanUp = () -> {};

    public SystemOut()
    {
        mResourceGenerator = () -> {
            synchronized (this)
            {
                PrintStream original = System.out;
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(new TeeStream(out, original));
                System.setOut(ps);

                mResourceGenerator = () ->
                    () -> {
                        ps.flush();
                        return out.toString();
                    };
                mCleanUp = () -> {
                    System.setOut(original);
                    ps.close();
                };
                return mResourceGenerator.value();
            }
        };
    }


    @Override
    public Generator<String> value()
    {
        return mResourceGenerator.value();
    }

    @Override
    public void close()
    {
        mCleanUp.run();
    }


    /**
     * An {@link OutputStream} that delegates every method call to two other {@link OutputStream}s.
     */
    private static final class TeeStream extends OutputStream
    {

        private final OutputStream mDelegate;
        private final OutputStream mOriginal;


        private TeeStream(OutputStream delegate, OutputStream original)
        {
            mDelegate = delegate;
            mOriginal = original;
        }


        @Override
        public void write(int i) throws IOException
        {
            mDelegate.write(i);
            mOriginal.write(i);
        }


        @Override
        public void flush() throws IOException
        {
            mDelegate.flush();
            mOriginal.flush();
        }


        @Override
        public void close() throws IOException
        {
            mDelegate.close();
            // don't close original output
        }
    }
}

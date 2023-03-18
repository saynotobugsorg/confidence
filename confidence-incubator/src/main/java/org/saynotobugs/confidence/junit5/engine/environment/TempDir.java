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

package org.saynotobugs.confidence.junit5.engine.environment;

import org.dmfs.jems2.Fragile;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.junit5.engine.verifiable.WithResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@StaticFactories(value = "ConfidenceEngine", packageName = "org.saynotobugs.confidence.junit5.engine")
public final class TempDir implements Fragile<WithResource.Resource<File>, Exception>
{

    private final String mPrefix;


    public TempDir()
    {
        this("temp");
    }


    public TempDir(String prefix)
    {
        mPrefix = prefix;
    }


    @Override
    public WithResource.Resource<File> value() throws Exception
    {
        File dir = Files.createTempDirectory(mPrefix).toFile();
        return new WithResource.Resource<File>()
        {
            @Override
            public void close() throws IOException
            {
                delete(dir);
            }


            @Override
            public File value()
            {
                return dir;
            }

        };
    }


    private static void delete(File file) throws IOException
    {
        File[] files = file.listFiles();
        if (files != null)
        {
            for (File f : files)
            {
                delete(f);
            }
        }
        Files.deleteIfExists(file.toPath());
    }
}

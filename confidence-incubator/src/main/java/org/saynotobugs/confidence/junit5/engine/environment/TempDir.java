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
import org.saynotobugs.confidence.junit5.engine.verifiable.Given;

import java.io.File;
import java.nio.file.Files;


public final class TempDir implements Fragile<Given.Resource<File>, Exception>
{
    @Override
    public Given.Resource<File> value() throws Exception
    {
        File dir = Files.createTempDirectory("temp").toFile();
        return new Given.Resource<File>()
        {
            @Override
            public void close()
            {
                dir.delete();
            }


            @Override
            public File value()
            {
                return dir;
            }
        };
    }
}

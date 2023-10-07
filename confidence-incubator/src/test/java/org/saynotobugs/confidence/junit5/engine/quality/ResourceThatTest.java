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

package org.saynotobugs.confidence.junit5.engine.quality;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.junit5.engine.Resource;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.emptyIterable;
import static org.saynotobugs.confidence.quality.Core.iterates;

class ResourceThatTest
{
    @Test
    void test()
    {
        assertThat(new ResourceThat<>(iterates("1", "2"), emptyIterable()),
            new AllOf<>(
                new Passes<Resource<List<String>>>(new Resource<List<String>>()
                {
                    private final List<String> resource = new ArrayList<>(asList("1", "2"));

                    @Override
                    public List<String> value()
                    {
                        return resource;
                    }

                    @Override
                    public void close()
                    {
                        resource.clear();
                    }
                }),
                new Fails<Resource<List<String>>>(new Resource<List<String>>()
                {
                    private final List<String> resource = new ArrayList<>(asList("1", "2"));

                    @Override
                    public List<String> value()
                    {
                        return resource;
                    }

                    @Override
                    public void close()
                    {
                        // this doesn't clean up
                    }
                },
                    "{ ...\n  had value [ \"1\",\n    \"2\" ] }"),
                new Fails<Resource<List<String>>>(new Resource<List<String>>()
                {
                    private final List<String> resource = new ArrayList<>(asList("1"));

                    @Override
                    public List<String> value()
                    {
                        return resource;
                    }

                    @Override
                    public void close()
                    {
                        resource.clear();
                    }
                },
                    "{ AutoClosable that had value iterated [ ...\n      1: missing \"2\" ]\n    ...\n  ... }"),
                new Fails<Resource<List<String>>>(new Resource<List<String>>()
                {
                    private final List<String> resource = new ArrayList<>(asList("1"));

                    @Override
                    public List<String> value()
                    {
                        return resource;
                    }

                    @Override
                    public void close()
                    {
                        // this doesn't clean up
                    }
                },
                    "{ AutoClosable that had value iterated [ ...\n      1: missing \"2\" ]\n    ...\n  and\n  had value [ \"1\" ] }"),
                new HasDescription("AutoClosable that has value iterates [ 0: \"1\",\n    1: \"2\" ] and is closed\n  and\n  has value <empty>")
            )
        );
    }

}
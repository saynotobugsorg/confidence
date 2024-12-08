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
import org.saynotobugs.confidence.junit5.engine.ResourceHandle;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.iterable.EmptyIterable;
import org.saynotobugs.confidence.quality.iterable.Iterates;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.saynotobugs.confidence.Assertion.assertThat;

class ResourceThatTest
{
    @Test
    void test()
    {
        assertThat(new ResourceThat<>(1, new Iterates<>("1", "2"), new EmptyIterable()),
            new AllOf<>(
                new Passes<>(() -> new Resource<List<String>>()
                {
                    private final List<String> resource = new ArrayList<>(asList("1", "2"));

                    @Override
                    public ResourceHandle<List<String>> value()
                    {
                        return new ResourceHandle<List<String>>()
                        {
                            @Override
                            public void close()
                            {
                                resource.clear();
                            }

                            @Override
                            public List<String> value()
                            {
                                return resource;
                            }
                        };
                    }
                },
                    "all of\n" +
                        "  0: had single use all of\n" +
                        "    0: AutoClosable that\n" +
                        "      had value iterated [\n" +
                        "        0: \"1\"\n" +
                        "        1: \"2\"\n" +
                        "      ] and\n" +
                        "      was closed\n" +
                        "    1: had value []\n" +
                        "  1: had parallel use all of\n" +
                        "    0: executions\n" +
                        "      #0 in thread pool-1-thread-1 elements [\n" +
                        "        0: had value []\n" +
                        "      ]\n" +
                        "    1: elements [\n" +
                        "      0: AutoClosable that\n" +
                        "        had value iterated [\n" +
                        "          0: \"1\"\n" +
                        "          1: \"2\"\n" +
                        "        ] and\n" +
                        "        was closed\n" +
                        "    ]\n" +
                        "    2: elements [\n" +
                        "      0: had value []\n" +
                        "    ]"),
                new Fails<>(() -> new Resource<List<String>>()
                {
                    private final List<String> resource = new ArrayList<>(asList("1", "2"));

                    @Override
                    public ResourceHandle<List<String>> value()
                    {
                        return new ResourceHandle<List<String>>()
                        {
                            @Override
                            public void close()
                            {
                                // does not clean up
                            }

                            @Override
                            public List<String> value()
                            {
                                return resource;
                            }
                        };
                    }
                },
                    "all of\n  0: had single use all of\n    ...\n    1: had value [ \"1\", \"2\" ]\n  1: had parallel use all of\n    ...\n    2: elements [\n      0: had value [ \"1\", \"2\" ]\n    ]"),
                new Fails<>(() -> new Resource<List<String>>()
                {
                    private final List<String> resource = new ArrayList<>(asList("1"));


                    @Override
                    public ResourceHandle<List<String>> value()
                    {
                        return new ResourceHandle<List<String>>()
                        {
                            @Override
                            public void close()
                            {
                                resource.clear();
                            }

                            @Override
                            public List<String> value()
                            {
                                return resource;
                            }
                        };
                    }
                },
                    "all of\n  0: had single use all of\n    0: AutoClosable that\n      had value iterated [\n        ...\n        1: missing \"2\"\n      ] and\n      ...\n    ...\n  1: had parallel use all of\n    ...\n    1: elements [\n      0: AutoClosable that\n        had value iterated [\n          ...\n          1: missing \"2\"\n        ] and\n        ...\n    ]\n    ..."),
                new Fails<>(() -> new Resource<List<String>>()
                {
                    private final List<String> resource = new ArrayList<>(asList("1"));

                    @Override
                    public ResourceHandle<List<String>> value()
                    {
                        return new ResourceHandle<List<String>>()
                        {
                            @Override
                            public void close()
                            {
                                // does not clean up
                            }

                            @Override
                            public List<String> value()
                            {
                                return resource;
                            }
                        };
                    }
                },
                    "all of\n  0: had single use all of\n    0: AutoClosable that\n      had value iterated [\n        ...\n        1: missing \"2\"\n      ] and\n      ...\n    1: had value [ \"1\" ]\n  1: had parallel use all of\n    ...\n    1: elements [\n      0: AutoClosable that\n        had value iterated [\n          ...\n          1: missing \"2\"\n        ] and\n        ...\n    ]\n    2: elements [\n      0: had value [ \"1\" ]\n    ]"),
                new HasDescription("all of\n  0: has single use all of\n    0: AutoClosable that has value iterates [\n      0: \"1\"\n      1: \"2\"\n    ] and is closed\n    1: has value []\n  1: has parallel use all of\n    0: running 1 parallel execution, each element has value <anything>\n    1: each element AutoClosable that has value iterates [\n      0: \"1\"\n      1: \"2\"\n    ] and is closed\n    2: each element has value []")
            )
        );
    }
}
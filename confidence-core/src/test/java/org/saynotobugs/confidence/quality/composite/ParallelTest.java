/*
 * Copyright 2024 dmfs GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.comparable.LessThan;
import org.saynotobugs.confidence.quality.object.Anything;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.quality.supplier.Supplies;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.concurrent.atomic.AtomicInteger;

import static org.saynotobugs.confidence.Assertion.assertThat;


class ParallelTest
{
    @Test
    void test()
    {
        AtomicInteger integer = new AtomicInteger(0);

        assertThat(new Parallel<>(new Supplies<>(new LessThan<>(999))),
            new AllOf<>(
                new Passes<>(() -> 99, new Anything(/* TODO, how to imrove this? */)),
                new Passes<>(() -> integer.incrementAndGet() % 999, new Anything(/* TODO, how to imrove this? */)),
                new Fails<>(
                    () -> integer.incrementAndGet() % 1000,
                    new DescribesAs(new MatchesPattern("executions\\R  ...\\R  .+ supplied 999\\R  ..."))),
                new Fails<>(
                    () -> {
                        if (integer.incrementAndGet() % 999 == 0)
                        {
                            throw new RuntimeException("error");
                        }
                        else
                        {
                            return 0;
                        }
                    },
                    new DescribesAs(new MatchesPattern("executions\\R  ...\\R  .+ <java.lang.RuntimeException: error>\\R  ..."))),
                new HasDescription("running 1000 parallel execution, supplies less than 999")
            ));
    }


    @Test
    void testError()
    {
        assertThat(new Parallel<>(2, new EqualTo<>(new Object()
            {
                @Override
                public boolean equals(Object obj)
                {
                    throw new IllegalArgumentException("error");
                }
            })),
            new AllOf<>(
                new Fails<>(
                    new Object()
                    {
                        @Override
                        public boolean equals(Object obj)
                        {
                            throw new IllegalArgumentException("error");
                        }
                    },
                    new DescribesAs(new MatchesPattern(
                        "executions\\n  #0 in thread .* <java.lang.IllegalArgumentException: error>\\n  #1 in thread .* <java.lang.IllegalArgumentException: error>"))),
                new HasDescription(new DescribesAs(new MatchesPattern("running 2 parallel execution, .*")))
            ));
    }
}
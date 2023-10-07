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

package org.saynotobugs.confidence.junit5.engine.testengine;

import org.junit.jupiter.api.Test;
import org.junit.platform.testkit.engine.EngineTestKit;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;


class ConfidenceEngineTest
{
    @Test
    void testAllPass()
    {
        EngineTestKit
            .engine("ConfidenceTest")
            .selectors(selectClass(AllAssertionPassTestCase.class))
            .execute()
            .testEvents()
            .assertStatistics(stats -> stats
                .started(3)
                .succeeded(3)
                .failed(0)
            );
    }


    @Test
    void testMultipleFailPass()
    {
        EngineTestKit
            .engine("ConfidenceTest")
            .selectors(selectClass(MultipleAssertionFailAndPassTestCase.class))
            .execute()
            .testEvents()
            .assertStatistics(stats -> stats
                .started(3)
                .succeeded(1)
                .aborted(0)
                .failed(2)
            );
    }


    @Test
    void testOnePass()
    {
        EngineTestKit
            .engine("ConfidenceTest")
            .selectors(selectClass(SingleAssertionPassTestCase.class))
            .execute()
            .testEvents()
            .assertStatistics(stats -> stats
                .started(1)
                .succeeded(1)
                .failed(0)
            );
    }


    @Test
    void testOneFail()
    {
        EngineTestKit
            .engine("ConfidenceTest")
            .selectors(selectClass(SingleAssertionFailTestCase.class))
            .execute()
            .testEvents()
            .assertStatistics(stats -> stats
                .started(1)
                .succeeded(0)
                .failed(1)
            );
    }


    @Test
    void testWithResources()
    {
        EngineTestKit
            .engine("ConfidenceTest")
            .selectors(selectClass(SharedResourceTestCase.class))
            .execute()
            .testEvents()
            .assertStatistics(stats -> stats
                .started(2)
                .succeeded(2)
                .failed(0)
            );
    }
}
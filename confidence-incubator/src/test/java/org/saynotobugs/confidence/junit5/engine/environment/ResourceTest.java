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
import org.dmfs.jems2.FragileProcedure;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.junit5.engine.verifiable.WithResource;

import static org.dmfs.jems2.mockito.Mock.*;
import static org.mockito.Mockito.verify;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.quality.Core.*;


class ResourceTest
{
    @Test
    void test() throws Exception
    {
        Object object = new Object();
        Fragile<Object, Exception> resource = () -> object;
        FragileProcedure<Object, Exception> cleanUp = mock(FragileProcedure.class,
            withVoid(p -> p.process(object), doingNothing()));
        assertThat(new Resource<>(resource, cleanUp),
            has("resource", Fragile::value,
                allOf(
                    has("resource", WithResource.Resource::value, is(object)),
                    successfully(new Text("closes"), new Text("did not close"), WithResource.Resource::close)
                ))
        );

        verify(cleanUp).process(object);
    }

}
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

package org.saynotobugs.confidence.rxjava3.procedure;

import io.reactivex.rxjava3.processors.PublishProcessor;
import io.reactivex.rxjava3.subjects.CompletableSubject;
import io.reactivex.rxjava3.subjects.MaybeSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.SingleSubject;
import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.rxjava3.adapters.*;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import static org.saynotobugs.confidence.Assertion.assertThat;

class HasSubscribersTest
{
    @Test
    void testSingleSubject()
    {
        SingleSubject<Object> singleSubject = SingleSubject.create();
        singleSubject.test();
        assertThat(new HasSubscribers(),
            new AllOf<>(
                new Passes<>(new SingleSubjectAdapter<>(singleSubject), "has subscribers"),
                new Fails<>(new SingleSubjectAdapter<>(SingleSubject.create()), "had no subscribers"),
                new HasDescription("has subscribers")
            ));
    }

    @Test
    void testPublishSubject()
    {
        PublishSubject<Object> publishSubject = PublishSubject.create();
        publishSubject.test();
        assertThat(new HasSubscribers(),
            new AllOf<>(
                new Passes<>(new PublishSubjectAdapter<>(publishSubject), "has subscribers"),
                new Fails<>(new PublishSubjectAdapter<>(PublishSubject.create()), "had no subscribers"),
                new HasDescription("has subscribers")
            ));
    }


    @Test
    void testPublishProcessor()
    {
        PublishProcessor<Object> publishProcessor = PublishProcessor.create();
        publishProcessor.test();
        assertThat(new HasSubscribers(),
            new AllOf<>(
                new Passes<>(new PublishProcessorAdapter<>(publishProcessor), "has subscribers"),
                new Fails<>(new PublishProcessorAdapter<>(PublishProcessor.create()), "had no subscribers"),
                new HasDescription("has subscribers")
            ));
    }


    @Test
    void testMaybeSubject()
    {
        MaybeSubject<Object> maybeSubject = MaybeSubject.create();
        maybeSubject.test();
        assertThat(new HasSubscribers(),
            new AllOf<>(
                new Passes<>(new MaybeSubjectAdapter<>(maybeSubject), "has subscribers"),
                new Fails<>(new MaybeSubjectAdapter<>(MaybeSubject.create()), "had no subscribers"),
                new HasDescription("has subscribers")
            ));
    }


    @Test
    void testCompletableSubject()
    {
        CompletableSubject completableSubject = CompletableSubject.create();
        completableSubject.test();
        assertThat(new HasSubscribers(),
            new AllOf<>(
                new Passes<>(new CompletableSubjectAdapter<>(completableSubject), "has subscribers"),
                new Fails<>(new CompletableSubjectAdapter<>(CompletableSubject.create()), "had no subscribers"),
                new HasDescription("has subscribers")
            ));
    }
}
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

package org.saynotobugs.confidence.rxjava3;

import org.reactivestreams.Subscriber;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;


/**
 * A generalization of the interfaces of a {@link Subscriber}, a {@link Observer}, a {@link SingleObserver}, a {@link MaybeObserver} and
 * a {@link CompletableObserver} without the respective onSubscribe methods.
 * <p>
 * This allows us to use the same tools to test all of them.
 */
public interface RxSubjectAdapter<T>
{
    void onNext(T next);

    void onComplete();

    void onError(Throwable error);
}

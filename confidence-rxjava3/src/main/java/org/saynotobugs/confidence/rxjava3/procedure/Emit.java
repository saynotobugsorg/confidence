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

package org.saynotobugs.confidence.rxjava3.procedure;

import org.dmfs.jems2.Procedure;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.procedure.Batch;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.StructuredDescription;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.description.ValueDescription;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.object.Successfully;
import org.saynotobugs.confidence.rxjava3.RxSubjectAdapter;

import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.MaybeSource;
import io.reactivex.rxjava3.core.SingleSource;


@StaticFactories(value = "RxJava3", packageName = "org.saynotobugs.confidence.rxjava3")
public final class Emit<Up> extends QualityComposition<RxSubjectAdapter<? super Up>>
{

    /**
     * Creates a {@link Procedure} causes the upstream subject to emit the given value(s).
     * <p>
     * Note, this has no effect on {@link CompletableSource}s because they don't have any values.
     * <p>
     * {@link SingleSource}s and {@link MaybeSource}s can only emit a single value and trying to emit more than one value will
     * cause an Exception to be thrown. Also note that {@link SingleSource}s and {@link MaybeSource}s automatically complete when
     * a value is emitted.
     */
    @SafeVarargs
    public Emit(Up... emissions)
    {
        this(new Seq<>(emissions));
    }


    /**
     * Creates a {@link Procedure} causes the upstream subject to emit the given value(s).
     * <p>
     * Note, this has no effect on {@link CompletableSource}s because they don't have any values.
     * <p>
     * {@link SingleSource}s and {@link MaybeSource}s can only emit a single value and trying to emit more than one value will
     * cause an Exception to be thrown. Also note that {@link SingleSource}s and {@link MaybeSource}s automatically complete when
     * a value is emitted.
     */
    public Emit(Iterable<Up> emissions)
    {
        super(new Successfully<>(
            new Spaced(new TextDescription("emissions"), new StructuredDescription("[", ",", "]", new Mapped<>(ValueDescription::new, emissions))),
            new Spaced(new TextDescription("emissions"),
                new StructuredDescription("[", ",", "]", new Mapped<>(ValueDescription::new, emissions)),
                new TextDescription("failed with")),
            rxSubjectAdapter -> new Batch<>(rxSubjectAdapter::onNext).process(emissions)));
    }
}

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

package org.saynotobugs.confidence.quality.iterable;

import org.dmfs.jems2.iterable.Expanded;
import org.dmfs.jems2.iterable.Mapped;
import org.dmfs.jems2.iterable.Seq;
import org.dmfs.jems2.iterable.Sieved;
import org.dmfs.jems2.procedure.ForEach;
import org.dmfs.jems2.single.Collected;
import org.dmfs.srcless.annotations.staticfactory.DeprecatedFactories;
import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Assessment;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.assessment.Fail;
import org.saynotobugs.confidence.assessment.FailUpdated;
import org.saynotobugs.confidence.assessment.PassIf;
import org.saynotobugs.confidence.description.Composite;
import org.saynotobugs.confidence.description.Structured;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.object.EqualTo;

import java.util.*;

import static org.saynotobugs.confidence.description.LiteralDescription.COMMA_NEW_LINE;
import static org.saynotobugs.confidence.description.LiteralDescription.NEW_LINE;


@StaticFactories(
    value = "Iterable",
    packageName = "org.saynotobugs.confidence.core.quality",
    deprecates = @DeprecatedFactories(value = "Core", packageName = "org.saynotobugs.confidence.quality"))
public final class IteratesInAnyOrder<T> implements Quality<Iterable<T>>
{
    private final Iterable<? extends Quality<? super T>> mDelegates;


    @SafeVarargs
    public IteratesInAnyOrder(T... delegate)
    {
        this(new Mapped<>(EqualTo::new, new Seq<>(delegate)));
    }


    @SafeVarargs
    public IteratesInAnyOrder(Quality<? super T>... delegates)
    {
        this(new Seq<>(delegates));
    }


    public IteratesInAnyOrder(Iterable<? extends Quality<? super T>> delegates)
    {
        mDelegates = delegates;
    }


    @Override
    public Assessment assessmentOf(Iterable<T> candidate)
    {
        List<T> values = new Collected<>(ArrayList::new, candidate).value();
        List<? extends Quality<? super T>> delegates = new Collected<>(ArrayList::new, mDelegates).value();

        Map<T, Map<Quality<? super T>, Boolean>> cache = new HashMap<>();
        Assessment assessment = resolve(values, delegates, cache);

        Set<T> additionals = new HashSet<>(cache.keySet());
        new ForEach<>(
            new Mapped<>(
                Map.Entry::getKey,
                new Sieved<>(e -> e.getValue().containsValue(true), cache.entrySet()))).process(additionals::remove);

        Set<? extends Quality<? super T>> unmatched = new HashSet<>(delegates);
        new ForEach<>(
            new Mapped<>(
                Map.Entry::getKey,
                new Sieved<>(
                    Map.Entry::getValue,
                    new Expanded<>(Map::entrySet, cache.values())))).process(unmatched::remove);

        if (!assessment.isSuccess() && unmatched.isEmpty() && additionals.isEmpty())
        {
            if (values.size() != delegates.size())
            {
                return new Fail(new Composite(new Value(candidate),
                    new Text(" has " + (values.size() > delegates.size() ? "more" : "fewer") + " elements than "),
                    new Structured(new Text("["), COMMA_NEW_LINE, new Text("]"),
                        new Mapped<>(Quality::description, delegates))));
            }
            return new Fail(new Composite(new Text("No permutation of "),
                new Structured(new Text("["), COMMA_NEW_LINE, new Text("]"),
                    new Mapped<>(Quality::description, delegates)),
                new Text(" matched "),
                new Value(candidate)));
        }
        return new FailUpdated(description -> new Structured(NEW_LINE, new Seq<>(
            new Structured(
                new Text("iterated also ["),
                COMMA_NEW_LINE,
                new Text("]"),
                new Mapped<>(Value::new, additionals)),
            new Structured(
                new Text("did not iterate ["),
                COMMA_NEW_LINE,
                new Text("]"),
                new Mapped<>(Quality::description, unmatched)))),
            assessment);
    }


    @Override
    public Description description()
    {
        return new Structured(
            new Text("iterates in any order ["),
            COMMA_NEW_LINE,
            new Text("]"),
            new Mapped<>(Quality::description, mDelegates));
    }


    private static <T> Assessment resolve(
        List<T> candidates,
        List<? extends Quality<? super T>> qualities,
        Map<T, Map<Quality<? super T>, Boolean>> cache)
    {
        if (candidates.size() == 0 || qualities.size() == 0)
        {
            return new PassIf(candidates.size() == qualities.size(), new Text(""));
        }
        for (int i = 0; i < candidates.size(); ++i)
        {
            for (int j = 0; j < qualities.size(); j++)
            {
                if (assess(candidates.get(i), qualities.get((j)), cache))
                {
                    List<T> can2 = new ArrayList<>(candidates);
                    can2.remove(i);
                    List<? extends Quality<? super T>> quals = new ArrayList<>(qualities);
                    quals.remove(j);
                    Assessment r = resolve(can2, quals, cache);
                    if (r.isSuccess())
                    {
                        return r;
                    }
                }
            }
        }
        return new Fail(new Text(""));
    }


    @SuppressWarnings("NewApi")
    private static <T> Boolean assess(T candidate, Quality<? super T> quality, Map<T, Map<Quality<? super T>, Boolean>> cache)
    {
        return cache.computeIfAbsent(candidate, i -> new HashMap<>())
            .computeIfAbsent(quality, i -> quality.assessmentOf(candidate).isSuccess());
    }
}

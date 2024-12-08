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
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.util.function.Supplier;

import static org.saynotobugs.confidence.Assertion.assertThat;


class HasTest
{
    @Test
    void testPlain()
    {
        assertThat(new Has<>(Object::toString, new EqualTo<>("123")),
            new AllOf<>(
                new Passes<Object>("123", "\"123\""),
                new Passes<>(123, "\"123\""),
                new Fails<>(124, "\"124\""),
                new HasDescription("\"123\"")));
    }


    @Test
    void testPlainValue()
    {
        assertThat(new Has<>(Object::toString, "123"),
            new AllOf<>(
                new Passes<Object>("123", "\"123\""),
                new Passes<>(123, "\"123\""),
                new Fails<>(124, "\"124\""),
                new HasDescription("\"123\"")));
    }


    @Test
    void testWithFeature()
    {
        assertThat(new Has<>("toString", Object::toString, new EqualTo<>("123")),
            new AllOf<>(
                new Passes<Object>("123", "had toString \"123\""),
                new Passes<>(123, "had toString \"123\""),
                new Fails<>(124, "had toString \"124\""),
                new HasDescription("has toString \"123\"")));
    }


    @Test
    void testValueWithFeature()
    {
        assertThat(new Has<>("toString", Object::toString, "123"),
            new AllOf<>(
                new Passes<Object>("123", "had toString \"123\""),
                new Passes<>(123, "had toString \"123\""),
                new Fails<>(124, "had toString \"124\""),
                new HasDescription("has toString \"123\"")));
    }


    @Test
    void testWithDescriptionFunction()
    {
        assertThat(new Has<>((Description pass) -> new Spaced(pass, new Text("present")),
                (Description fail) -> new Spaced(fail, new Text("past")),
                Object::toString,
                new EqualTo<>("123")),
            new AllOf<>(
                new Passes<Object>("123", "\"123\" past"),
                new Passes<>(123, "\"123\" past"),
                new Fails<>(124, "\"124\" past"),
                new HasDescription("\"123\" present")));
    }


    @Test
    void testDescriptions()
    {
        assertThat(new Has<>(
                new Text("present"),
                new Text("past"),
                Object::toString,
                new EqualTo<>("123")),
            new AllOf<>(
                new Passes<Object>("123", "past \"123\""),
                new Passes<>(123, "past \"123\""),
                new Fails<>(124, "past \"124\""),
                new HasDescription("present \"123\"")));
    }


    @Test
    void testThrowing()
    {
        assertThat(new Has<>(
                description -> new Spaced(new Text("present"), description),
                description -> new Spaced(new Text("past"), description),
                throwable -> new Spaced(new Text("yikes"), new Value(throwable)),
                Supplier::get,
                new EqualTo<>("123")),
            new AllOf<>(
                new Passes<Supplier<String>>(() -> "123", "past \"123\""),
                new Fails<>(() -> "124", "past \"124\""),
                new Fails<>(() -> {throw new RuntimeException("error");}, "yikes <java.lang.RuntimeException: error>"),
                new HasDescription("present \"123\"")));
    }
}
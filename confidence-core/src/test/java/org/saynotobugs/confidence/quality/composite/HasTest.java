package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.valuedescription.Value;
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
                new Passes<>("123", 123),
                new Fails<>(124, "\"124\""),
                new HasDescription("\"123\"")));
    }


    @Test
    void testPlainValue()
    {
        assertThat(new Has<>(Object::toString, "123"),
            new AllOf<>(
                new Passes<>("123", 123),
                new Fails<>(124, "\"124\""),
                new HasDescription("\"123\"")));
    }


    @Test
    void testWithFeature()
    {
        assertThat(new Has<>("toString", Object::toString, new EqualTo<>("123")),
            new AllOf<>(
                new Passes<>("123", 123),
                new Fails<>(124, "had toString \"124\""),
                new HasDescription("has toString \"123\"")));
    }


    @Test
    void testValueWithFeature()
    {
        assertThat(new Has<>("toString", Object::toString, "123"),
            new AllOf<>(
                new Passes<>("123", 123),
                new Fails<>(124, "had toString \"124\""),
                new HasDescription("has toString \"123\"")));
    }


    @Test
    void testWithDescriptionFunction()
    {
        assertThat(new Has<>((Description pass) -> new Spaced(pass, new Text("pass")),
                (Description fail) -> new Spaced(fail, new Text("fail")),
                Object::toString,
                new EqualTo<>("123")),
            new AllOf<>(
                new Passes<Object>("123", 123),
                new Fails<>(124, "\"124\" fail"),
                new HasDescription("\"123\" pass")));
    }


    @Test
    void testDescriptions()
    {
        assertThat(new Has<>(
                new Text("match"),
                new Text("mismatch"),
                Object::toString,
                new EqualTo<>("123")),
            new AllOf<>(
                new Passes<>("123", 123),
                new Fails<>(124, "mismatch \"124\""),
                new HasDescription("match \"123\"")));
    }


    @Test
    void testThrowing()
    {
        assertThat(new Has<>(
                description -> new Spaced(new Text("match"), description),
                description -> new Spaced(new Text("mismatch"), description),
                throwable -> new Spaced(new Text("yikes"), new Value(throwable)),
                Supplier::get,
                new EqualTo<>("123")),
            new AllOf<>(
                new Passes<Supplier<String>>(() -> "123"),
                new Fails<>(() -> "124", "mismatch \"124\""),
                new Fails<>(() -> {throw new RuntimeException("error");}, "yikes <java.lang.RuntimeException: error>"),
                new HasDescription("match \"123\"")));
    }
}
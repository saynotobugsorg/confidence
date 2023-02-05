package org.saynotobugs.confidence.quality.composite;

import org.junit.jupiter.api.Test;
import org.saynotobugs.confidence.Description;
import org.saynotobugs.confidence.description.Delimited;
import org.saynotobugs.confidence.description.TextDescription;
import org.saynotobugs.confidence.quality.object.EqualTo;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

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
    void testWithFeature()
    {
        assertThat(new Has<>("toString", Object::toString, new EqualTo<>("123")),
            new AllOf<>(
                new Passes<>("123", 123),
                new Fails<>(124, "had toString \"124\""),
                new HasDescription("has toString \"123\"")));
    }


    @Test
    void testWithDescriptionFunction()
    {
        assertThat(new Has<>((Description pass) -> new Delimited(pass, new TextDescription("pass")),
                (Description fail) -> new Delimited(fail, new TextDescription("fail")),
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
                new TextDescription("match"),
                new TextDescription("mismatch"),
                Object::toString,
                new EqualTo<>("123")),
            new AllOf<>(
                new Passes<>("123", 123),
                new Fails<>(124, "mismatch \"124\""),
                new HasDescription("match \"123\"")));
    }
}
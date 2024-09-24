package org.saynotobugs.confidence.junit5.engine.procedure;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.dmfs.jems2.confidence.Jems2.procedureThatAffects;
import static org.saynotobugs.confidence.Assertion.assertThat;
import static org.saynotobugs.confidence.core.quality.File.*;
import static org.saynotobugs.confidence.core.quality.Grammar.soIt;
import static org.saynotobugs.confidence.core.quality.Grammar.that;
import static org.saynotobugs.confidence.core.quality.Object.hasToString;

class WithFileTest
{
    @TempDir
    File dir;

    @Test
    void testEmptyFile()
    {
        assertThat(new WithFile("somefile"),
            procedureThatAffects(() -> dir, soIt(
                containsFile("somefile"))));
    }


    @Test
    void testFileWithUtf8String()
    {
        assertThat(new WithFile("somefile", "utf-8 contentöäü@ſ€"),
            procedureThatAffects(() -> dir, soIt(
                containsFile("somefile", that(containsText("utf-8 contentöäü@ſ€"))))));
    }

    @Test
    void testFileWithLatin1String()
    {
        assertThat(new WithFile("somefile", "latin-1-text-öäü", ISO_8859_1),
            procedureThatAffects(() -> dir, soIt(
                containsFile("somefile", that(containsText(ISO_8859_1, hasToString("latin-1-text-öäü")))))));
    }


    @Test
    void testFileWithByteArray()
    {
        assertThat(new WithFile("somefile", new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }),
            procedureThatAffects(() -> dir, soIt(
                containsFile("somefile", that(containsData(new byte[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }))))));
    }

}
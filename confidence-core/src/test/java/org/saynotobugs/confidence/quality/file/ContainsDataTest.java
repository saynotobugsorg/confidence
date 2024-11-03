package org.saynotobugs.confidence.quality.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ContainsDataTest
{
    @TempDir
    File dir;

    @Test
    void test() throws IOException
    {
        File passingFile = new File(dir, "passes");
        Files.write(passingFile.toPath(), new byte[] { 1, 2, 3 });
        File emptyFile = new File(dir, "empty");
        emptyFile.createNewFile();

        assertThat(new ContainsData(new byte[] { 1, 2, 3 }),
            new AllOf<>(
                new Passes<>(passingFile),
                new Fails<>(emptyFile, "contained data array that iterated [ 0: missing 1,\n  1: missing 2,\n  2: missing 3 ]"),
                new Fails<>(new File(dir, "nonexistent"), new DescribesAs(new MatchesPattern("threw <java.io.FileNotFoundException: .*> while reading"))),
                new HasDescription("contains data [ 1, 2, 3 ]")));
    }

}
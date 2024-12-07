package org.saynotobugs.confidence.quality.file;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.saynotobugs.confidence.quality.charsequence.MatchesPattern;
import org.saynotobugs.confidence.quality.composite.AllOf;
import org.saynotobugs.confidence.quality.grammar.Is;
import org.saynotobugs.confidence.test.quality.DescribesAs;
import org.saynotobugs.confidence.test.quality.Fails;
import org.saynotobugs.confidence.test.quality.HasDescription;
import org.saynotobugs.confidence.test.quality.Passes;

import java.io.File;
import java.io.IOException;

import static org.saynotobugs.confidence.Assertion.assertThat;

class ContainsFileTest
{
    @TempDir
    File tempDir;

    @Test
    void testFile() throws IOException
    {
        File file = new File(tempDir, "somefile");
        file.createNewFile();
        File dir = new File(tempDir, "someDir");
        dir.mkdir();
        assertThat(new ContainsFile("somefile"),
            new AllOf<>(
                new Passes<>(tempDir, "contained \"somefile\" that existed"),
                new Fails<>(file, "contained \"somefile\" that did not exist"),
                new Fails<>(dir, "contained \"somefile\" that did not exist"),
                new Fails<>(new File(tempDir, "nonExistentFile"), "contained \"somefile\" that did not exist"),
                new HasDescription("contains \"somefile\" that exists")));
    }

    @Test
    void testDir() throws IOException
    {
        File file = new File(tempDir, "somefile");
        file.createNewFile();
        File dir = new File(tempDir, "someDir");
        dir.mkdir();
        assertThat(new ContainsFile("someDir"),
            new AllOf<>(
                new Passes<>(tempDir, "contained \"someDir\" that existed"),
                new Fails<>(file, "contained \"someDir\" that did not exist"),
                new Fails<>(dir, "contained \"someDir\" that did not exist"),
                new Fails<>(new File(tempDir, "nonExistentFile"), "contained \"someDir\" that did not exist"),
                new HasDescription("contains \"someDir\" that exists")));
    }

    @Test
    void testFileWithDelegate() throws IOException
    {
        File file = new File(tempDir, "somefile");
        file.createNewFile();
        File dir = new File(tempDir, "someDir");
        dir.mkdir();
        assertThat(new ContainsFile("somefile", new Is<>(new AFile())),
            new AllOf<>(
                new Passes<>(tempDir, new DescribesAs(new MatchesPattern("contained \"somefile\" was file </.*>"))),
                new Fails<>(file, "contained \"somefile\" was not a file"),
                new Fails<>(dir, "contained \"somefile\" was not a file"),
                new Fails<>(new File(tempDir, "nonExistentFile"), "contained \"somefile\" was not a file"),
                new HasDescription("contains \"somefile\" is a file")));
    }

    @Test
    void testDirWithDelegate() throws IOException
    {
        File file = new File(tempDir, "somefile");
        file.createNewFile();
        File dir = new File(tempDir, "someDir");
        dir.mkdir();
        assertThat(new ContainsFile("someDir", new Is<>(new ADirectory())),
            new AllOf<>(
                new Passes<>(tempDir,  new DescribesAs(new MatchesPattern("contained \"someDir\" was directory </.*>"))),
                new Fails<>(file, "contained \"someDir\" was not a directory"),
                new Fails<>(dir, "contained \"someDir\" was not a directory"),
                new Fails<>(new File(tempDir, "nonExistentFile"), "contained \"someDir\" was not a directory"),
                new HasDescription("contains \"someDir\" is a directory")));
    }
}
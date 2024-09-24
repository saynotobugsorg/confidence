package org.saynotobugs.confidence.junit5.engine.procedure;

import org.dmfs.jems2.FragileProcedure;
import org.saynotobugs.confidence.junit5.engine.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * Initialization procedure for {@link File} directory {@link Resource}s
 * that creates a file inside that directory.
 */
public final class WithFile implements FragileProcedure<File, IOException>
{
    private final String mName;
    private final byte[] mData;

    public WithFile(String name)
    {
        this(name, new byte[0]);
    }

    public WithFile(String name, String data)
    {
        this(name, data, StandardCharsets.UTF_8);
    }

    public WithFile(String name, String data, Charset charset)
    {
        this(name, data.getBytes(charset));
    }

    public WithFile(String name, byte[] data)
    {
        mName = name;
        mData = data;
    }

    @Override
    public void process(File file) throws IOException
    {
        Files.write(new File(file, mName).toPath(), mData);
    }
}

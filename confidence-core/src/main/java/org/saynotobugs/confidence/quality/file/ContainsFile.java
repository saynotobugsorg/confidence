package org.saynotobugs.confidence.quality.file;


import org.dmfs.srcless.annotations.staticfactory.StaticFactories;
import org.saynotobugs.confidence.Quality;
import org.saynotobugs.confidence.description.Spaced;
import org.saynotobugs.confidence.description.Text;
import org.saynotobugs.confidence.description.Value;
import org.saynotobugs.confidence.quality.composite.Has;
import org.saynotobugs.confidence.quality.composite.QualityComposition;
import org.saynotobugs.confidence.quality.grammar.That;

import java.io.File;

@StaticFactories(
    value = "File",
    packageName = "org.saynotobugs.confidence.core.quality")
public final class ContainsFile extends QualityComposition<File>
{
    public ContainsFile(String child)
    {
        this(child, new That<>(new Exists()));
    }

    public ContainsFile(String child, Quality<? super File> delegate)
    {
        super(new Has<>(
            new Spaced(new Text("contains"), new Value(child)),
            new Spaced(new Text("contained"), new Value(child)),
            file -> new File(file, child),
            delegate));
    }
}

package org.Vrglab.Reflections.vfs;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class UnionUrls implements Vfs.Dir {
    private final URL url;
    private final File file;

    public UnionUrls(URL url) {
        this.url = url;
        file = new File(url.getFile().split("%")[0]);
    }

    @Override
    public String getPath() {
        return url.getPath().split("%")[0];
    }

    @Override
    public Iterable<Vfs.File> getFiles() {
        try {
            return new JarInputDir(file.toURI().toURL()).getFiles();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

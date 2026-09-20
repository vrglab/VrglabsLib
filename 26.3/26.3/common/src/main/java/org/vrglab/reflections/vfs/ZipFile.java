package org.vrglab.reflections.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/** an implementation of {@link org.vrglab.reflections.vfs.Vfs.File} for {@link ZipEntry} */
public class ZipFile implements Vfs.File {
    private final ZipDir _root;
    private final ZipEntry _entry;

    public ZipFile(final ZipDir root, ZipEntry entry) {
        this._root = root;
        this._entry = entry;
    }

    public String getName() {
        String name = _entry.getName();
        return name.substring(name.lastIndexOf("/") + 1);
    }

    public String getRelativePath() {
        return _entry.getName();
    }

    public InputStream openInputStream() throws IOException {
        return _root.jarFile.getInputStream(_entry);
    }

    @Override
    public String toString() {
        return _root.getPath() + "!" + java.io.File.separatorChar + _entry.toString();
    }
}

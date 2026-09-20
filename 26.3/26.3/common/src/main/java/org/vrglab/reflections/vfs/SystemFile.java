package org.vrglab.reflections.vfs;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/** an implementation of {@link org.vrglab.reflections.vfs.Vfs.File} for a directory {@link java.io.File} */
public class SystemFile implements Vfs.File {
    private final SystemDir _root;
    private final java.io.File _file;

    public SystemFile(final SystemDir root, java.io.File file) {
        this._root = root;
        this._file = file;
    }

    public String getName() {
        return _file.getName();
    }

    public String getRelativePath() {
        String filepath = _file.getPath().replace("\\", "/");
        if (filepath.startsWith(_root.getPath())) {
            return filepath.substring(_root.getPath().length() + 1);
        }

        return null; //should not get here
    }

    public InputStream openInputStream() {
        try {
            return new FileInputStream(_file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return _file.toString();
    }
}

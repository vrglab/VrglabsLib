package org.vrglab.reflections.vfs;

import org.vrglab.reflections.ReflectionsException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;

/*
 * An implementation of {@link org.Vrglab.Reflections.vfs.Vfs.Dir} for directory {@link java.io.File}.
 */
public class SystemDir implements Vfs.Dir {
    private final File _file;

    public SystemDir(File file) {
        if (file != null && (!file.isDirectory() || !file.canRead())) {
            throw new RuntimeException("cannot use dir " + file);
        }
        this._file = file;
    }

    public String getPath() {
        return _file != null ? _file.getPath().replace("\\", "/") : "/NO-SUCH-DIRECTORY/";
    }

    public Iterable<Vfs.File> getFiles() {
        if (_file == null || !_file.exists()) {
            return Collections.emptyList();
        }
        return () -> {
            try {
                return Files.walk(_file.toPath()).
                        filter(Files::isRegularFile).
                        map(path -> (Vfs.File) new SystemFile(SystemDir.this, path.toFile())).
                        iterator();
            } catch (IOException e) {
                throw new ReflectionsException("could not get files for " + _file, e);
            }
        };
    }
}

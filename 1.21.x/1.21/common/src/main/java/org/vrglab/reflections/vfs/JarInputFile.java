package org.vrglab.reflections.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

/**
*
*/
public class JarInputFile implements Vfs.File {
    private final ZipEntry _entry;
    private final JarInputDir _jarInputDir;
    private final long _fromIndex;
    private final long _endIndex;

    public JarInputFile(ZipEntry entry, JarInputDir jarInputDir, long cursor, long nextCursor) {
        this._entry = entry;
        this._jarInputDir = jarInputDir;
        _fromIndex = cursor;
        _endIndex = nextCursor;
    }

    public String getName() {
        String name = _entry.getName();
        return name.substring(name.lastIndexOf("/") + 1);
    }

    public String getRelativePath() {
        return _entry.getName();
    }

    public InputStream openInputStream() {
        return new InputStream() {
            @Override
            public int read() throws IOException {
                if (_jarInputDir.cursor >= _fromIndex && _jarInputDir.cursor <= _endIndex) {
                    int read = _jarInputDir.jarInputStream.read();
                    _jarInputDir.cursor++;
                    return read;
                } else {
                    return -1;
                }
            }
        };
    }
}

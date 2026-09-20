package org.vrglab.reflections.vfs;

import org.jboss.vfs.VirtualFile;

import java.io.IOException;
import java.io.InputStream;

public class JbossFile implements Vfs.File {

    private final JbossDir _root;
    private final VirtualFile _virtualFile;

    public JbossFile(final JbossDir root, VirtualFile virtualFile) {
        this._root = root;
        this._virtualFile = virtualFile;
    }

    @Override
    public String getName() {
        return _virtualFile.getName();
    }

    @Override
    public String getRelativePath() {
        String filepath  = _virtualFile.getPathName();
        if (filepath.startsWith(_root.getPath())) {
            return filepath.substring(_root.getPath().length() + 1);
        }

        return null;
    }

    @Override
    public InputStream openInputStream() throws IOException {
        return _virtualFile.openStream();
    }
}

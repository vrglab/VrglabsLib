package org.vrglab.reflections.vfs;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class UnionUrls implements Vfs.Dir {
    private final URL url;
    private final File file;

    public UnionUrls(URL url) {
        URL _newURL = null;
        try {
            String scheme = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();
            String path = url.getPath();

            path = path.replaceAll("([\\\\\\\\/]%[^\\\\\\\\/!]+![/\\\\\\\\]?)$", "");
            path = path.replace("%27", "'").replace("%20", " ");

            _newURL = new URL(scheme, host, port, path);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert path to URL: " + url, e);
        }



        this.url = _newURL;
        file = new File(_newURL.getFile());

    }

    @Override
    public String getPath() {
        return url.getPath();
    }

    @Override
    public Iterable<Vfs.File> getFiles() {
        try {
            if (file.isFile()) {
                return new JarInputDir(file.toURI().toURL()).getFiles();
            }

            if (file.isDirectory()) {
                return new SystemDir(file).getFiles();
            }

            return null;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}

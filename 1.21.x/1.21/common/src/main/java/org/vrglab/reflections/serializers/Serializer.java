package org.vrglab.reflections.serializers;

import org.vrglab.reflections.Reflections;

import java.io.File;
import java.io.InputStream;

/** de/serialization for {@link Reflections} instance metadata
 * <p>see {@link XmlSerializer}, {@link JsonSerializer}, {@link JavaCodeSerializer} */
public interface Serializer {
    /** reads the input stream into a new Reflections instance, populating it's store */
    Reflections read(InputStream inputStream);

    /** saves a Reflections instance into the given filename */
    File save(Reflections reflections, String filename);

    static File prepareFile(String filename) {
        File file = new File(filename);
        File parent = file.getAbsoluteFile().getParentFile();
        if (!parent.exists()) {
            //noinspection ResultOfMethodCallIgnored
            parent.mkdirs();
        }
        return file;
    }
}
